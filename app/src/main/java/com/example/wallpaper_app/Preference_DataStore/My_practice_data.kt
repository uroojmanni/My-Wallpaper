package com.example.wallpaper_app.Preference_DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class My_practice_data  (val context: Context) {


     private  val Context.dataStore:DataStore<Preferences> by preferencesDataStore("settings")
    //  make keys

    val NAME = stringPreferencesKey("NAME")
    val AGE = stringPreferencesKey("66")


    suspend fun savedata(name:String, age:String)
    {
    context.dataStore.edit {

        it[NAME]=name
        it[AGE]=age
    }

    }
    suspend fun getdata()=context.dataStore.data.map {

        it[NAME]?:""
        it[AGE]?:""
    }


}