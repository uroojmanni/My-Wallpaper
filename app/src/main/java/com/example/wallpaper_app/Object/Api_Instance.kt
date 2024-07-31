package com.example.wallpaper_app.Object

import com.example.wallpaper_app.Interface.Api_Interface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api_Instance {

    //end points
    private const val BASE_URL = "https://api.unsplash.com/"


    const val API_KEY = "c5eqQRdxrKEnnh_ccHuKXZtxs2EGlRbRFkjrNvfz5X8"

    //  Retrofit API interface
    val api: Api_Interface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api_Interface::class.java) // Create an instance of the Api_Interface to make API calls
    }
}
