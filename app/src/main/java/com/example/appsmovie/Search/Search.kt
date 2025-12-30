package com.example.appsmovie.Search

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.appsmovie.R
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
// Hapus: import com.example.appsmovie.Home.Adapter
import com.example.appsmovie.Api.MovieResult
import androidx.core.widget.doOnTextChanged
import com.example.appsmovie.DetailFilm.DetailFilm

class Search : AppCompatActivity() {
    private val viewModel: SearchVM by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val rvSearchResults = findViewById<RecyclerView>(R.id.rv_search_results)
        searchAdapter = SearchAdapter()
        rvSearchResults.layoutManager = LinearLayoutManager(this)
        rvSearchResults.adapter = searchAdapter

        val etJudul = findViewById<EditText>(R.id.etjudul)
        etJudul.doOnTextChanged { text, _, _, _ ->
            val query = text.toString()
            viewModel.searchMovies(query)
        }

        findViewById<ImageView>(R.id.bak).setOnClickListener {
            finish()
        }

        searchAdapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(movie: MovieResult) {
                val intent = Intent(this@Search, DetailFilm::class.java).apply {
                    putExtra("MOVIE_ID", movie.id)
                    putExtra("MOVIE_TITLE", movie.titleText)
                    putExtra("MOVIE_POSTER_URL", movie.primaryImage?.url)
                }
                startActivity(intent)
            }
        })
        observeViewModel()
    }
    private fun observeViewModel(){
        viewModel.searchResults.observe(this) { results ->
            searchAdapter.setData(results)
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
        }
    }
}
