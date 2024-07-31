package com.example.wallpaper_app.Interface

import com.example.wallpaper_app.Model.Model_imge
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api_Interface {

    @GET("search/photos")// end points
    fun searchPhotos(
        @Query("client_id") clientId: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<UnsplashResponse>
}

data class UnsplashResponse(
    val results: List<Model_imge>
)

