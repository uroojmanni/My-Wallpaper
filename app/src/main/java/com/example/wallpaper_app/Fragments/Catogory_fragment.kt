package com.example.wallpaper_app.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaper_app.Adapter.Adapter_Wallpaper
import com.example.wallpaper_app.Database_Repository.WallpaperRepository
import com.example.wallpaper_app.databinding.FragmentCatogoryFragmentBinding

class Catogory_fragment : Fragment() {

    private var _binding: FragmentCatogoryFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var wallpaperRepository: WallpaperRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatogoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //not back to splash
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner)
        {
            requireActivity().finish()
        }


        // Initialize the repository
        wallpaperRepository = WallpaperRepository()

        // Fetch data from the repository
        val wallpapers = wallpaperRepository.getWallpapers()

        // Initialize RecyclerView
        binding.mainRecycleview.layoutManager = GridLayoutManager(context, 2)

        // Set adapter with data from the repository
        val adapter = Adapter_Wallpaper(wallpapers, findNavController()) // Pass NavController
        binding.mainRecycleview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
