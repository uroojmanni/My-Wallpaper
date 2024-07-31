package com.example.wallpaper_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaper_app.Adapter.Api_adapter
import com.example.wallpaper_app.Interface.UnsplashResponse
import com.example.wallpaper_app.Model.Model_imge
import com.example.wallpaper_app.Object.Api_Instance
import com.example.wallpaper_app.Preference_DataStore.MyDataStore
import com.example.wallpaper_app.R
import com.example.wallpaper_app.databinding.FragmentPreviewBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Preview_Fragment : Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Api_adapter
    private lateinit var myDataStore: MyDataStore
    private var photosList: MutableList<Model_imge> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDataStore = MyDataStore.getInstance(requireContext())

        // Back navigation
        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        // Receive and display data
        val query = arguments?.getString("data")
        binding.textView.text = query

        // Set up RecyclerView
        setupRecyclerView()

        // Fetch photos
        fetchPhotos(query)
    }

    private fun setupRecyclerView() {
        adapter = Api_adapter(
            imageList = photosList,
            onItemClick = { image ->
                val bundle = Bundle().apply {
                    putSerializable("data_fetch", image)
                }
                findNavController().navigate(R.id.action_preview_Fragment2_to_setWallpaper_fragment2, bundle)
            },
            onHeartClick = { image ->
                lifecycleScope.launch {
                    // Refresh the adapter to update heart icons
                    adapter.notifyItemChanged(photosList.indexOf(image))
                }
            },
            myDataStore
        )
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter
    }

    private fun fetchPhotos(query: String?) {
        if (query.isNullOrEmpty()) return
        val totalPages = 7
        val currentPage = 1
        fetchPage(query, currentPage, totalPages)
    }

    private fun fetchPage(query: String, page: Int, totalPages: Int) {
        if (page > totalPages) return


        //api request
        Api_Instance.api.searchPhotos(
            clientId = Api_Instance.API_KEY,
            query = query,
            page = page,
            perPage = 20
        ).enqueue(object : Callback<UnsplashResponse> {
            override fun onResponse(
                call: Call<UnsplashResponse>,
                response: Response<UnsplashResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { photos ->
                        photosList.addAll(photos)
                        adapter.notifyItemRangeInserted(20,photos.size)

                        // Fetch next page recursively
                        fetchPage(query, page + 1, totalPages)

                    }
                } else {
                    context?.let { ctx ->
                        Toast.makeText(ctx, "Failed to load photos", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<UnsplashResponse>, t: Throwable) {
                context?.let { ctx ->
                    Toast.makeText(ctx, "Failed to load photos", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
