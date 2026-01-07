package com.example.appsmovie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsmovie.Ticket.BookingTicketVM
import com.example.appsmovie.databinding.FragmentPayementResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
@AndroidEntryPoint
class PayementResultFragment : Fragment() {
    private val bookingViewModel: BookingTicketVM = BookingManager.viewModel
    private var _binding: FragmentPayementResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPayementResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnViewTicket.setOnClickListener {
            bookingViewModel.resetBookingData() //
            val intent = Intent(requireContext(), HomeMain::class.java).apply {
                putExtra("navigateTo", 2)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

        binding.btnBackToHome.setOnClickListener {
            bookingViewModel.resetBookingData()
            val intent = Intent(requireContext(), HomeMain::class.java).apply {
                putExtra("navigateTo", 0)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}