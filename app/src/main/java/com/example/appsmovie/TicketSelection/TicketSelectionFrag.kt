package com.example.appsmovie.TicketSelection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.appsmovie.BookingManager
import com.example.appsmovie.Buffet.BuffetProduct
import com.example.appsmovie.R
import com.example.appsmovie.Ticket.BookingTicketVM
import com.example.appsmovie.databinding.FragmentTicketSelectionBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.getValue

class TicketSelectionFrag : Fragment() {
    private var _binding: FragmentTicketSelectionBinding? = null
    private val binding get() = _binding!!

    private val bookingViewModel: BookingTicketVM by lazy { BookingManager.viewModel }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ngambil data dari detailfilm
        val movieTitle = requireActivity().intent.getStringExtra("MOVIE_TITLE")
        val moviePosterUrl = requireActivity().intent.getStringExtra("MOVIE_POSTER_URL")
        val movieGenre = requireActivity().intent.getStringExtra("MOVIE_GENRE")
        Log.e("TicketSelectionFrag", "Intent di fragment Movie Title: $movieTitle, Poster URL: $moviePosterUrl, Genre: $movieGenre")

        binding.tvTicketGenre.text = movieGenre
        binding.tvTicketTitle.text = movieTitle
        Glide.with(this)
            .load(moviePosterUrl)
            .placeholder(R.drawable.item)
            .into(binding.ivTicketPoster)

        binding.btnChooseTheater.setOnClickListener {
            showTheaterSelectionDialog(it as Button)
        }

        binding.btnSelectSession.setOnClickListener {
            showSessionSelectionDialog(it as Button)
        }

        binding.btnBuffet.setOnClickListener {
            val intent = Intent(requireContext(), BuffetProduct::class.java)
            startActivity(intent)
        }
    }

    private fun showTheaterSelectionDialog(ButtonToUpdate: Button){
        val Theater = arrayOf("Cinema XXI - Plaza Senayan", "CGV - Grand Indonesia", "Cinépolis - Pejaten Village")
        // munculin alert dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pilih Theater")
            .setItems(Theater) { _, which ->
                val selectedTheater = Theater[which]
                ButtonToUpdate.text = selectedTheater
                bookingViewModel.setTheater(selectedTheater)
            }
            .show()
    }

    private fun showSessionSelectionDialog(ButtonToUpdate: Button){
        val sessions = arrayOf("12:30 PM", "03:00 PM", "05:30 PM", "08:00 PM")
        // munculin alert dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pilih Session")
            .setItems(sessions) { _, which ->
                val selectedSession = sessions[which]
                ButtonToUpdate.text = selectedSession
                bookingViewModel.setSession(selectedSession)
            }
            .show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}