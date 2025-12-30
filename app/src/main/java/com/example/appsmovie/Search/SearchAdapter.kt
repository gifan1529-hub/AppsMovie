package com.example.appsmovie.Search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsmovie.Api.MovieResult
import com.example.appsmovie.R

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var movies: List<MovieResult> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(movie: MovieResult)
    }
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

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
                    listener?.onItemClick(movies[position])
                }
            }
        }

        fun bind(movie: MovieResult) {
            tvJudul.text = movie.titleText
            tvSub.text = movie.originalTitle
            tvYear.text = movie.releaseYear.toString()
            tvPlot.text = movie.plot

            Glide.with(itemView.context)
                .load(movie.primaryImage?.url)
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

    fun setData(newMovies: List<MovieResult>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }
}
