package com.example.appsmovie.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsmovie.Api.MovieResult
import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.databinding.CardbawahBinding
import com.example.appsmovie.databinding.CardsampingBinding

class AdapterOffline(private val layoutType: LayoutType) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val movies = mutableListOf<RoomApi>()
    private var itemClickListener: OnItemClickListener? = null

    enum class LayoutType {
        HIGHLIGHT,
        POSTER,
        Search
    }

    interface OnItemClickListener {
        fun onItemClick(movie: RoomApi)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    fun setData(newMovies: List<RoomApi>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return layoutType.ordinal
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LayoutType.HIGHLIGHT.ordinal -> {
                val binding = CardsampingBinding.inflate(inflater, parent, false)
                HighlightViewHolder(binding)
            }
            else -> {
                val binding = CardbawahBinding.inflate(inflater, parent, false)
                PosterViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val movie = movies[position]
        when (holder) {
            is HighlightViewHolder -> holder.bind(movie, itemClickListener)
            is PosterViewHolder -> holder.bind(movie, itemClickListener)
        }
    }

    override fun getItemCount(): Int = movies.size

    class HighlightViewHolder(private val binding: CardsampingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: RoomApi, clickListener: OnItemClickListener?) {
            Glide.with(itemView.context)
                .load(movie.posterUrl)
                .into(binding.ivHighlight)
            itemView.setOnClickListener {
                clickListener?.onItemClick(movie)

            }
        }
    }

    class PosterViewHolder(private val binding: CardbawahBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: RoomApi, clickListener: OnItemClickListener?) {
            Glide.with(itemView.context)
                .load(movie.posterUrl)
                .into(binding.ivPoster)
            itemView.setOnClickListener {
                clickListener?.onItemClick(movie)
            }
        }
    }
}