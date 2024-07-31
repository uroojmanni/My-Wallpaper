package com.example.wallpaper_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaper_app.Adapter.Api_adapter
import com.example.wallpaper_app.Model.Model_imge
import com.example.wallpaper_app.Preference_DataStore.MyDataStore
import com.example.wallpaper_app.R
import com.example.wallpaper_app.databinding.FragmentFavouriteFragmentBinding
import kotlinx.coroutines.launch

class Favourite_fragment : Fragment() {

    private var _binding: FragmentFavouriteFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var myDataStore: MyDataStore
    private lateinit var adapter: Api_adapter
    private var favoriteList: MutableList<Model_imge> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDataStore = MyDataStore.getInstance(requireContext())

        setupRecyclerView()
        retrieveFavorites()
    }

    private fun setupRecyclerView() {
        adapter = Api_adapter(favoriteList, { photo ->
            val bundle = Bundle().apply {
                putSerializable("data_fetch", photo)
            }
            findNavController().navigate(R.id.action_favourite_fragment_to_setWallpaper_fragment2, bundle)
        },
            { photo ->
                lifecycleScope.launch {
                    favoriteList.remove(photo) // Remove from the list
                    myDataStore.removeFavorite(photo) // Remove from datastore

                    adapter.updateFavoriteStatus(photo, false) // Update favorite status
                }
            },
            myDataStore)

        binding.favouriteRecycle.layoutManager = GridLayoutManager(context, 3)
        binding.favouriteRecycle.adapter = adapter
    }

    private fun retrieveFavorites() {
        lifecycleScope.launch {
            myDataStore.getFavoritesFlow().collect { favorites ->
                favoriteList.clear()
                favoriteList.addAll(favorites.reversed())

                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
