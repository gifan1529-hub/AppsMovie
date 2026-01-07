package com.example.appsmovie.DetailFilm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appsmovie.R
import com.example.appsmovie.Ticket.BookingTicket
// 1. Import ViewBinding yang sudah digenerate otomatis
import com.example.appsmovie.databinding.DetailfilmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFilm : AppCompatActivity() {

    private lateinit var binding: DetailfilmBinding
    private val viewModel: DetailFilmVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = DetailfilmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.favorite.setOnClickListener {
            viewModel.toggleFavorite()
        }

        val movieId = intent.getStringExtra("MOVIE_ID")

        if (movieId != null && movieId.isNotEmpty()) {
            viewModel.fetchMovieDetails(movieId)
        } else {
            Toast.makeText(this, "Movie ID tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnBuyTicket.setOnClickListener {
            viewModel.movieDetails.value?.let { movie ->
                val intent = Intent(this, BookingTicket::class.java).apply {
                    putExtra("MOVIE_ID", movie.id)
                    putExtra("MOVIE_TITLE", movie.title)
                    putExtra("MOVIE_POSTER_URL", movie.posterUrl)
                    }
                startActivity(intent)
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBal.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isFavorite.observe(this) { isFavorite ->
            if (isFavorite) {
                binding.favorite.setImageResource(R.drawable.star)
            } else {
                binding.favorite.setImageResource(R.drawable.bintang)
            }
        }

        viewModel.movieDetails.observe(this) { movie ->
            movie?.let {
                binding.tvTitle.text = it.title
                binding.tvYear.text = it.releaseYear?.toString()
                binding.tvPlot.text = it.plot
                binding.tvGenre.text = it.genre
                val ratingValue = (it.rating?.toFloat() ?: 0f)
                binding.ratingBar.rating = ratingValue
                binding.reting.text = String.format("%.1f/10", it.rating)

                val posterUrl = (it.posterUrl)
                Glide.with(this).load(posterUrl).into(binding.ivPoster)
                Glide.with(this).load(posterUrl).into(binding.ivBackdrop)

                binding.favorite.isEnabled = true
                binding.favorite.imageAlpha = 255
            }
        }
    }
}
