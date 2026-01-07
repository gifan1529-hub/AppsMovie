package com.example.appsmovie.DetailTicket

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsmovie.R
import com.example.appsmovie.databinding.FragmentTicketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketFragment : Fragment() {
    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailTicketVM by viewModels()
    private lateinit var ticketAdapter: DetailTicketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences("MyAppSession", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("key_email", null)

        setupRecyclerView()

        if (userEmail != null) {
            observeTicketData(userEmail)
        } else {

        }
    }

    private fun setupRecyclerView() {
        ticketAdapter = DetailTicketAdapter(emptyList())
        binding.rvdetails.apply {
            adapter = ticketAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeTicketData(email: String){
        viewModel.getTicketsByUser(email).observe(viewLifecycleOwner) { ticketList ->
            if (ticketList.isEmpty()){
                binding.tvEmptyTicket.visibility = View.VISIBLE
                binding.rvdetails.visibility = View.GONE
            } else {
                binding.tvEmptyTicket.visibility = View.GONE
                binding.rvdetails.visibility = View.VISIBLE
                ticketAdapter = DetailTicketAdapter(ticketList)
                binding.rvdetails.adapter = ticketAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}