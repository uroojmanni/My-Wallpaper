package com.example.wallpaper_app.Preference_DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class Practice_datastore(val context: Context) {

    //initialize  preference datastore
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("pref")



    //  define a  keys// make keys

    val USERNAME = stringPreferencesKey("User_Name")
    val AGE = stringPreferencesKey("Age")




     // store  data in datastore

    suspend fun storeUserData(name: String, age: String) {
        context.dataStore.edit {
            it[USERNAME] = name
            it[AGE] = age

        }


        // delete data from datastore

        suspend fun deletedata(name: String,age: String)
        {
            context.dataStore.edit {preferences->
                preferences[USERNAME]=""
                preferences[AGE]= ""


            }
        }
    }

    // DISPLAY DATA NAME AND AGE
   // get data from datastore
    fun displayData()=context.dataStore.data.map {
        it[USERNAME]?:""
        it[AGE] ?: ""
    }


}