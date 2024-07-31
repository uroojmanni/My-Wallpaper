package com.example.wallpaper_app.Model

import java.io.Serializable

data class Model_imge(
    val id: String,
    val urls: Urls,
) : Serializable

data class Urls(
    val thumb: String,
    val regular: String,
    val full: String
)
