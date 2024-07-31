package com.example.wallpaper_app.Database_Repository

import com.example.wallpaper_app.Model.Model_Wallpaper

class WallpaperRepository {

    fun getWallpapers(): List<Model_Wallpaper> {
    // Simulate data fetching from a remote source or database
    return listOf(
        Model_Wallpaper("Animal"),
        Model_Wallpaper("Fruit"),
        Model_Wallpaper("Nature"),
        Model_Wallpaper("City"),
        Model_Wallpaper("Abstract"),
        Model_Wallpaper("Space"),
        Model_Wallpaper("Cars"),
        Model_Wallpaper("Flowers"),
        Model_Wallpaper("Architecture"),
        Model_Wallpaper("Sports"),
        Model_Wallpaper("Technology"),
        Model_Wallpaper("Music"),
        Model_Wallpaper("Movies"),
        Model_Wallpaper("Food"),
        Model_Wallpaper("Travel"),
        Model_Wallpaper("Pets"),
        Model_Wallpaper("Fashion"),
        Model_Wallpaper("Education"),
        Model_Wallpaper("Gaming"),
        Model_Wallpaper("History"),
        Model_Wallpaper("Comics"),
        Model_Wallpaper("Vehicles"),
        Model_Wallpaper("Seasons"),
        Model_Wallpaper("Holidays")



    )
}
}