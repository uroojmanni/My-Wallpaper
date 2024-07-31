package com.example.wallpaper_app.Preference_DataStore

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.wallpaper_app.Model.Model_imge
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MyDataStore private constructor(context: Context) {

    // create a DataStore instance
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favorite_photos")


    // define a keys
    private val dataStore: DataStore<Preferences> = context.dataStore
    private val KEY_FAVORITES = stringPreferencesKey("favorites")
    private val gson = Gson()

    @SuppressLint("StaticFieldLeak")
    // singlton instance
    companion object {
        @Volatile
        private var INSTANCE: MyDataStore? = null

        fun getInstance(context: Context): MyDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MyDataStore(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    private fun toJson(favorites: List<Model_imge>): String {
        return gson.toJson(favorites)
    }

    private fun fromJson(json: String): List<Model_imge> {
        val type = object : TypeToken<List<Model_imge>>() {}.type
        return gson.fromJson(json, type)
    }

    fun getFavoritesFlow(): Flow<List<Model_imge>> {
        return dataStore.data
            .map { preferences ->
                val json = preferences[KEY_FAVORITES] ?: "[]"
                fromJson(json)
            }
    }

    suspend fun addFavorite(photo: Model_imge) {
        dataStore.edit { preferences ->
            val currentFavorites = getFavoritesFlow().first()
            val updatedFavorites = currentFavorites.toMutableList()

            if (!updatedFavorites.contains(photo)) {
                updatedFavorites.add(photo)
                preferences[KEY_FAVORITES] = toJson(updatedFavorites)
            }
        }
    }

    suspend fun isFavorite(photo: Model_imge): Boolean {
        return getFavoritesFlow().first().contains(photo)
    }

    suspend fun removeFavorite(photo: Model_imge) {
        dataStore.edit { preferences ->
            val currentFavorites = getFavoritesFlow().first()
            val updatedFavorites = currentFavorites.toMutableList()

            if (updatedFavorites.remove(photo)) {
                preferences[KEY_FAVORITES] = toJson(updatedFavorites)

            }
        }
    }
}

