package com.example.wallpaper_app.Adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.wallpaper_app.Model.Model_imge
import com.example.wallpaper_app.Preference_DataStore.MyDataStore
import com.example.wallpaper_app.R
import com.example.wallpaper_app.databinding.WallLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Api_adapter(
    private var imageList: MutableList<Model_imge>,
    private val onItemClick: (Model_imge) -> Unit,
    private val onHeartClick: (Model_imge) -> Unit,
    private val myDataStore: MyDataStore
) : RecyclerView.Adapter<Api_adapter.ImageViewHolder>() {

    private lateinit var context: Context
    private val favoriteStatusMap = mutableMapOf<Model_imge, Boolean>()

    inner class ImageViewHolder(val binding: WallLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Model_imge) {
            binding.progressBar.visibility = View.VISIBLE
            Glide.with(binding.root)
                .load(image.urls?.thumb)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,

                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.imageviewApi)

            // Initialize favorite status and update UI
            CoroutineScope(Dispatchers.Main).launch {
                    val favorite = myDataStore.isFavorite(image)
//                }
                updateHeartIcon(favorite)
            }

            binding.heartIcon.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val currentlyFavorite = myDataStore.isFavorite(image)
                    if (currentlyFavorite) {
                        myDataStore.removeFavorite(image)
                        favoriteStatusMap[image] = false
                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                    } else {
                        myDataStore.addFavorite(image)
                        favoriteStatusMap[image] = true
                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                    }
                    // Notify only the specific item that has been clicked
                    notifyItemChanged(adapterPosition)
                    onHeartClick(image)
                }
            }

            binding.root.setOnClickListener {
                onItemClick(image)
            }
        }

        private fun updateHeartIcon(isFavorite: Boolean) {
            binding.heartIcon.setImageResource(
                if (isFavorite) R.drawable.big_heart else R.drawable.ic_heart2
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        context = parent.context
        val binding = WallLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageList[position]
        holder.bind(image)
    }

    override fun getItemCount() = imageList.size

    // Update favorite status and notify only the affected items
    fun updateFavoriteStatus(image: Model_imge, isFavorite: Boolean) {
        favoriteStatusMap[image] = isFavorite
        val index = imageList.indexOf(image)
        if (index != -1) {
            notifyItemChanged(index)
        }
    }
}
