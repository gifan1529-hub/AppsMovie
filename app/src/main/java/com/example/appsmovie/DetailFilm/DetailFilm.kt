package com.example.appsmovie.DetailFilm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import com.bumptech.glide.Glide
import com.example.appsmovie.Api.MovieResult
import com.example.appsmovie.R
import com.example.appsmovie.Ticket.BookingTicket
// hilt dagger
// clean arsi
class DetailFilm : AppCompatActivity() {
    private var currentMovieResult: MovieResult? = null
    private lateinit var ivFavoriteToggle: ImageView
    private lateinit var progressbar: ProgressBar
    private val viewModel: DetailFilmVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailfilm)

        progressbar = findViewById(R.id.progressBal)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val tvYear = findViewById<TextView>(R.id.tv_year)
        val tvGenre = findViewById<TextView>(R.id.tv_genre)
        val tvPlot = findViewById<TextView>(R.id.tv_plot)
        val ivPoster = findViewById<ImageView>(R.id.iv_poster)
        val ivBackdrop = findViewById<ImageView>(R.id.iv_backdrop)
        val back = findViewById<ImageView>(R.id.btn_back)
        ivFavoriteToggle = findViewById(R.id.favorite)

        ivFavoriteToggle.isEnabled = true
        ivFavoriteToggle.imageAlpha = 100

        back.setOnClickListener {
            finish()
        }

        val tempTitle = intent.getStringExtra("MOVIE_TITLE")
        val tempPosterUrl = intent.getStringExtra("MOVIE_POSTER_URL")
        var movieId: String? = intent.getStringExtra("MOVIE_ID")


        Log.e("DetailFilm", "Movie ID: $movieId, Title: $tempTitle, Poster URL: $tempPosterUrl")


        if (movieId == null) {
            val movieIdInt = intent.getStringExtra("MOVIE_ID")?.toIntOrNull()
            if (movieIdInt != -1) {
                movieId = movieIdInt.toString()
            }
        }

        if (movieId != null) {
            viewModel.fetchMovieDetails(movieId)
        } else {
            Toast.makeText(this, "Movie ID tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }

        ivFavoriteToggle.setOnClickListener {
            currentMovieResult?.let { movieData ->
                viewModel.toggleFavorite(movieData)
            }
        }
        val buy = findViewById<Button>(R.id.btn_buy_ticket)
        buy.setOnClickListener {
            val title = currentMovieResult?.titleText ?: intent.getStringExtra("MOVIE_TITLE")
            val posterUrl = currentMovieResult?.primaryImage?.url ?: intent.getStringExtra("MOVIE_POSTER_URL")
            val genre = tvGenre.text.toString()
            val year = tvYear.text.toString()
            val id = movieId

            Log.e("DetailFilm", "onClickListener: Movie ID: $movieId, Title: $title, Poster URL: $tempPosterUrl")

            val intent = Intent(this, BookingTicket::class.java).apply {
                // masalahnya karenya di depan putExtra ada intent.
                // jadinya si intent ini ngisi ke intent yang lama bukan ke intent yang di booking ticket
                putExtra("MOVIE_ID", movieId)
                putExtra("MOVIE_TITLE", tempTitle)
                putExtra("MOVIE_POSTER_URL", tempPosterUrl)
                putExtra("MOVIE_GENRE", tvGenre.text.toString())
                putExtra("MOVIE_YEAR", tvYear.text.toString())
            }
            startActivity(intent)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movieDetails.observe(this) { details ->
            details?.let {
                currentMovieResult = it

                findViewById<TextView>(R.id.tv_title).text = it.titleText
                findViewById<TextView>(R.id.tv_year).text = it.releaseYear?.toString()
                findViewById<TextView>(R.id.tv_plot).text = it.plot
                findViewById<TextView>(R.id.tv_genre).text = it.genres?.joinToString(", ") { genre -> genre }

                val ratingbar = findViewById<RatingBar>(R.id.rating_bar)
                val ratingValue = it.rating?.aggregateRating?.toFloat() ?: 0.0f
                ratingbar.rating = ratingValue
                findViewById<TextView>(R.id.reting).text = String.format("%.1f/10", ratingbar.rating)

                Glide.with(this).load(it.primaryImage?.url).into(findViewById(R.id.iv_poster))
                Glide.with(this).load(it.primaryImage?.url).into(findViewById(R.id.iv_backdrop))

                ivFavoriteToggle.isEnabled = true
                ivFavoriteToggle.imageAlpha = 255
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

       viewModel.isFavorite.observe(this) { isFavorite ->
            if (isFavorite) {
                ivFavoriteToggle.setImageResource(R.drawable.star)
            } else {
                ivFavoriteToggle.setImageResource(R.drawable.bintang)
            }
        }
    }
}
