package com.example.appsmovie.Search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsmovie.R
import com.example.core.Database.ApiOffline.RoomApi

class SearchAdapter (
    private val onItemClick: (RoomApi) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var movies: List<RoomApi> = emptyList()

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivPoster: ImageView = itemView.findViewById(R.id.card)
        private val tvJudul: TextView = itemView.findViewById(R.id.tvjudul)
        private val tvSub: TextView = itemView.findViewById(R.id.tvsub)
        private val tvYear: TextView = itemView.findViewById(R.id.tvyear)
        private val tvPlot: TextView = itemView.findViewById(R.id.tvplot)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(movies[position])
                }
            }
        }

        fun bind(movie: RoomApi) {
            tvJudul.text = movie.title
            tvSub.text = movie.title
            tvYear.text = movie.releaseYear.toString()
            tvPlot.text = movie.plot

            val posterUrl = movie.posterUrl ?: ""
            Glide.with(itemView.context)
                .load(posterUrl)
                .placeholder(R.drawable.item)
                .error(R.drawable.item)
                .into(ivPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.isisearch, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun setData(newMovies: List<RoomApi>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }
}
