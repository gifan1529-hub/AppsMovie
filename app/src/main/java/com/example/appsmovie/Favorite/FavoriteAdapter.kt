package com.example.appsmovie.Favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.data.Movie
import com.example.appsmovie.databinding.ItemfavoriteBinding
import kotlin.text.format

class FavoriteAdapter (
    private var movies: MutableList<Movie>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    private var itemClickListener: OnItemClickListener? = null

   interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    inner class FavoriteViewHolder(val binding: ItemfavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(movies[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemfavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(movie.posterUrl)
                .placeholder(com.example.appsmovie.R.drawable.item)
                .error(com.example.appsmovie.R.drawable.item)
                .into(ivFavorite)
        }

        movie.rating?.let { rating ->
            holder.binding.ratingfav.text = String.format("%.1f", rating)
            holder.binding.ratingfav.visibility = View.VISIBLE
        } ?: run {
            holder.binding.ratingfav.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = movies.size

    fun setData(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }
}
