package com.example.appsmovie.Search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsmovie.DetailFilm.DetailFilm
import com.example.appsmovie.databinding.SearchBinding // Import ViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Search : AppCompatActivity() {

    private lateinit var binding: SearchBinding
    private val viewModel: SearchVM by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchInput()
        observeViewModel()

        binding.bak.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter { movie ->
               val intent = Intent(this@Search, DetailFilm::class.java).apply {
                    putExtra("MOVIE_ID", movie.id)
                }
                startActivity(intent)
            }

        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(this@Search)
            adapter = searchAdapter
        }
    }

    private fun setupSearchInput() {
        binding.etjudul.doOnTextChanged { text, _, _, _ ->
            viewModel.searchMovies(text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.searchResults.observe(this) { results ->
            searchAdapter.setData(results)
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
