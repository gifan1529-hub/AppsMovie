package com.example.appsmovie.Home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsmovie.Api.MovieResult
import com.example.appsmovie.R
// ini udah ga kepake
class Adapter(private val layoutType: LayoutType) : RecyclerView.Adapter<Adapter.MovieViewHolder>() {

    private var movies: List<MovieResult> = emptyList()

   interface OnItemClickListener {
        fun onItemClick(movie: MovieResult)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    enum class LayoutType {
        HIGHLIGHT,
        POSTER,
        Search
    }

    inner class MovieViewHolder(itemView: View, layoutType: LayoutType) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView?

        init {
            imageView = if (layoutType == LayoutType.HIGHLIGHT) {
                itemView.findViewById(R.id.iv_highlight)
            } else {
                itemView.findViewById(R.id.iv_poster)
            }

            if (imageView == null) {
                Log.e("Adapter", "ImageView tidak ditemukan di layout untuk tipe: $layoutType.")
            }

             itemView.setOnClickListener {
                val position = adapterPosition
                 if (position != RecyclerView.NO_POSITION) {
                     listener?.onItemClick(movies[position])
                }
            }
        }

        fun bind(movie: MovieResult) {
            val imageUrl = movie.primaryImage?.url
            val title = movie.titleText
            Log.d("Adapter", "Data untuk film: '$title', Mencoba memuat URL: $imageUrl")

            val isValidImageUrl = imageUrl != null && imageUrl.startsWith("https://")

            if (isValidImageUrl && imageView != null) {
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.item)
                    .error(R.drawable.item)
                    .into(imageView)
            } else {
                Log.w("Adapter", "URL tidak valid atau null, menggunakan placeholder. URL: $imageUrl")
                imageView?.setImageResource(R.drawable.item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutId = when (layoutType) {
            LayoutType.HIGHLIGHT -> R.layout.cardsamping
            LayoutType.POSTER -> R.layout.cardbawah
            LayoutType.Search -> R.layout.isisearch
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        Log.d("Adapter", "ViewHolder dibuat untuk tipe: $layoutType")
        return MovieViewHolder(view, layoutType)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun setData(newMovies: List<MovieResult>) {
        this.movies = newMovies
        notifyDataSetChanged()
        Log.d("Adapter", "Adapter.setData() dipanggil dengan ${newMovies.size} item.")
    }
}