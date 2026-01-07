package com.example.appsmovie.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData // <<-- PENTING: IMPORT INI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsmovie.ApiOffline.RoomApi // <<-- 1. GANTI TIPE DATA UTAMA
import com.example.appsmovie.DetailFilm.DetailFilm
import com.example.appsmovie.DetailUser.DetailUser
import com.example.appsmovie.CleanArchitecture.Data.MovieRepositoryVM // <<-- 2. GANTI VIEWMODEL
import com.example.appsmovie.RoomDatabase.AppDatabase // <<-- IMPORT DATABASE
import com.example.appsmovie.Search.Search
import com.example.appsmovie.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val movieViewModel: MovieRepositoryVM by viewModels()
    private lateinit var highlightsAdapter: AdapterOffline
    private lateinit var moviesAdapter: AdapterOffline
    private lateinit var tvSeriesAdapter: AdapterOffline
    private lateinit var tvMiniSeriesAdapter: AdapterOffline
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        observeViewModel()

        binding.searchi.setOnClickListener {
            val intent = Intent(requireActivity(), Search::class.java)
            startActivity(intent)
        }

        binding.poto.setOnClickListener {
            val intent = Intent(requireActivity(), DetailUser::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerViews() {
        // Highlights
        binding.rvHighlights.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        highlightsAdapter = AdapterOffline(AdapterOffline.LayoutType.HIGHLIGHT)
        binding.rvHighlights.adapter = highlightsAdapter

        // Movies
        binding.rvNewmovie.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        moviesAdapter = AdapterOffline(AdapterOffline.LayoutType.POSTER)
        binding.rvNewmovie.adapter = moviesAdapter

        // TV Series
        binding.rvComing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tvSeriesAdapter = AdapterOffline(AdapterOffline.LayoutType.POSTER)
        binding.rvComing.adapter = tvSeriesAdapter

        // TV Mini Series
        binding.rvMiniseries.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tvMiniSeriesAdapter = AdapterOffline(AdapterOffline.LayoutType.POSTER)
        binding.rvMiniseries.adapter = tvMiniSeriesAdapter

        val clickListener = object : AdapterOffline.OnItemClickListener {
            override fun onItemClick(movie: RoomApi) {
                val intent = Intent(requireActivity(), DetailFilm::class.java)
                intent.putExtra("MOVIE_ID", movie.id)
                intent.putExtra("MOVIE_TITLE", movie.title)
                intent.putExtra("MOVIE_GENRE",movie.genre)
                intent.putExtra("MOVIE_POSTER_URL", movie.posterUrl)
                startActivity(intent)
            }
        }
        highlightsAdapter.setOnItemClickListener(clickListener)
        moviesAdapter.setOnItemClickListener(clickListener)
        tvSeriesAdapter.setOnItemClickListener(clickListener)
        tvMiniSeriesAdapter.setOnItemClickListener(clickListener)
    }

    private fun observeViewModel() {
        movieViewModel.movies.asLiveData().observe(viewLifecycleOwner, Observer { movies ->
            binding.progressBar.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE

            if (movies.isNotEmpty()) {
                highlightsAdapter.setData(movies.take(5)) // Ambil 5 film pertama untuk highlights
                moviesAdapter.setData(movies.filter { it.type == "movie" })
                tvSeriesAdapter.setData(movies.filter { it.type == "tvSeries" })
                tvMiniSeriesAdapter.setData(movies.filter { it.type == "tvMiniSeries" })
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
