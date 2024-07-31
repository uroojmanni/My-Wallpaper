package com.example.wallpaper_app.Fragments

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.wallpaper_app.Model.Model_imge
import com.example.wallpaper_app.Preference_DataStore.MyDataStore
import com.example.wallpaper_app.databinding.FragmentSetWallpaperFragmentBinding

class SetWallpaper_fragment : Fragment() {

    private var _binding: FragmentSetWallpaperFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var myDataStore: MyDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetWallpaperFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize MyDataStore
        myDataStore = MyDataStore.getInstance(requireContext())

        // Handle back arrow click
        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        val bundle = arguments?.getSerializable("data_fetch") as? Model_imge
        val imageUrl = bundle?.urls?.regular

        binding.setimage.let {
            Glide.with(context ?: return)
                .load(imageUrl)
                .into(binding.setimage)
        }

        // Handle Set Wallpaper button click
        binding.set.setOnClickListener {
            imageUrl?.let { url ->
                setWallpaper(url)
            }
        }

        // Load the image
        loadImage(imageUrl)

    }

// load image
    private fun loadImage(imageUrl: String?) {
        binding.progressBar.visibility = View.VISIBLE // Show progress bar
    Glide.with(this)
        .load(imageUrl)
        .into(binding.setimage)

        Glide.with(this)
            .load(imageUrl)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    binding.progressBar.visibility = View.GONE // Hide progress bar
                    binding.setimage.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle the case when the load is cleared
                }
            })

            }

    // Set wallpaper
    private fun setWallpaper(imageUrl: String) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val wallpaperManager = WallpaperManager.getInstance(context)
                    try {
                        wallpaperManager.setBitmap(resource)
                        Toast.makeText(context, "Wallpaper set successfully!", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
