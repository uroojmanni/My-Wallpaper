package com.example.wallpaper_app.Share_Preference

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.wallpaper_app.Model.Model_imge
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class My_preferences {

    private val PREF_NAME = "FavoritePhotos"
    private val KEY_FAVORITES = "favorites"

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun addFavorite(context: Context, photo: Model_imge) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()

        // Get existing favorites
        val favorites = getFavorites(context).toMutableList()

        // Check if photo already exists
        if (!favorites.contains(photo)) {
            // Add new favorite
            favorites.add(photo)

            // Convert to JSON and save
            val json = Gson().toJson(favorites)
            editor.putString(KEY_FAVORITES, json)
            editor.apply()
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Wallpaper already added to favorites", Toast.LENGTH_SHORT).show()
        }
    }

   // is favourite

    fun isfavorite(context: Context, photo: Model_imge): Boolean {
        return getFavorites(context).contains(photo)
    }


    // remove from favourite
    fun removeFavorite(context: Context, photo: Model_imge) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()

        // Get existing favorites
        val favorites = getFavorites(context).toMutableList()

        // Remove photo if it exists
        val wasRemoved = favorites.remove(photo)
        if (wasRemoved) {
            // Convert to JSON and save
            val json = Gson().toJson(favorites)
            editor.putString(KEY_FAVORITES, json)
            editor.apply()
            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Remove Wallpaper ", Toast.LENGTH_SHORT).show()
        }
    }



    // add to favourite
    fun getFavorites(context: Context): List<Model_imge> {
        val sharedPreferences = getSharedPreferences(context)
        val json = sharedPreferences.getString(KEY_FAVORITES, null)
        val type = object : TypeToken<List<Model_imge>>() {}.type

        return if (json != null) {
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}
