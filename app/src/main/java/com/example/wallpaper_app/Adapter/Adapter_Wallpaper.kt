package com.example.wallpaper_app.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaper_app.Model.Model_Wallpaper
import com.example.wallpaper_app.R
import com.example.wallpaper_app.databinding.LayoutWallpaperBinding

class Adapter_Wallpaper(
    private val wallpaperList: List<Model_Wallpaper>,
    private val navController: NavController // Use NavController for navigation
) : RecyclerView.Adapter<Adapter_Wallpaper.WallpaperViewHolder>() {

    class WallpaperViewHolder(val binding: LayoutWallpaperBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val binding = LayoutWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WallpaperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        val currentItem = wallpaperList[position]

        // Bind the title from the Model_Wallpaper to the TextView
        holder.binding.textView.text = currentItem.title


        holder.binding.constantlayout.setOnClickListener {
            val bundle = Bundle().apply {
                putString("data", currentItem.title)
            }
            navController.navigate(R.id.action_catogory_fragment_to_preview_Fragment2, bundle)
        }
    }

    override fun getItemCount() = wallpaperList.size
}
