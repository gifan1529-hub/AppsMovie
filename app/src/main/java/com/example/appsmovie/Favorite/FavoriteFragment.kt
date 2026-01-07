package com.example.appsmovie.Favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appsmovie.DetailFilm.DetailFilm
import com.example.core.Database.MovieDatabase.Movie
import com.example.appsmovie.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.mutableListOf

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val FavVM: FavoriteVM by viewModels()

   private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListener()
        observeFavoriteMovies()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter(mutableListOf())
        binding.rvFavorite.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = favoriteAdapter
        }
    }

    private fun setupClickListener() {
        val clickListener = object : FavoriteAdapter.OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                val intent = Intent(requireActivity(), DetailFilm::class.java).apply {
                    putExtra("MOVIE_ID", movie.id)
                    putExtra("MOVIE_TITLE", movie.title)
                    putExtra("MOVIE_POSTER_URL", movie.posterUrl)
                }
                startActivity(intent)
            }
        }
        favoriteAdapter.setOnItemClickListener(clickListener)
    }

    private fun observeFavoriteMovies() {
        FavVM.favoriteMovies.observe(viewLifecycleOwner) { favoriteMovies ->
            if (favoriteMovies.isEmpty()) {
                binding.tvEmptyFavorites.visibility = View.VISIBLE
                binding.rvFavorite.visibility = View.GONE
            } else {
                binding.tvEmptyFavorites.visibility = View.GONE
                binding.rvFavorite.visibility = View.VISIBLE
                favoriteAdapter.setData(favoriteMovies)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
