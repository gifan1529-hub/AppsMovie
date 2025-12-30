package com.example.appsmovie.Seat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appsmovie.BookingManager
import com.example.appsmovie.Ticket.BookingTicketVM
import com.example.appsmovie.databinding.FragmentPilihSeatBinding
import kotlin.getValue

class PilihSeatFragment : Fragment() {

    private var _binding: FragmentPilihSeatBinding? = null
    private val binding get() = _binding!!

    private val bookingViewModel: BookingTicketVM = BookingManager.viewModel


    private lateinit var seatAdapter: SeatAdapter
    private var seatList = mutableListOf<Seat>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPilihSeatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = requireActivity().intent
        bookingViewModel.setInitialMovieData(
            intent.getStringExtra("MOVIE_ID"),
            intent.getStringExtra("MOVIE_TITLE"),
            intent.getStringExtra("MOVIE_POSTER_URL"),
            intent.getStringExtra("MOVIE_GENRE")
        )
        setupSeatRecyclerView()
        setupTicketCounters()
        observeViewModel()
        loadSeatTaken()
    }

    private fun loadSeatTaken(){
        val bookingData = bookingViewModel.bookingData.value
        val movieId = bookingData?.movieId
        val theater = bookingData?.theater
        val session = bookingData?.session

        if (movieId != null && theater != null && session != null) {
            bookingViewModel.fetchTakenSeats(movieId, theater, session)
        } else {
            loadSeatLayout(emptySet())
        }
    }
    private fun loadSeatLayout(takenSeats: Set<String>) {
        seatList.clear()
        val rows = 'A'..'H' // baris A - H
        val cols = 1..8    // kolom 1 - 8
        for (row in rows) {
            for (col in cols) {
                val seatId = "$row$col"
                val status = if (takenSeats.contains(seatId)) {
                    SeatStatus.TAKEN
                } else {
                    SeatStatus.AVAILABLE
                }
                seatList.add(Seat(id = seatId, status = status))
            }
        }
        seatAdapter.setData(seatList, bookingViewModel.bookingData.value?.selectedSeats ?: emptySet())
    }

    private fun setupSeatRecyclerView() {
       seatAdapter = SeatAdapter { seat ->
           bookingViewModel.onSeatSelected(seat)
        }

        binding.rvSeats.apply {
            adapter = seatAdapter
             layoutManager = GridLayoutManager(requireContext(), 8)
        }
    }

    private fun setupTicketCounters() {
        binding.btnAddAdult.setOnClickListener { bookingViewModel.addAdultTicket() }
        binding.btnMinAdult.setOnClickListener { bookingViewModel.removeAdultTicket() }
        binding.btnAddChild.setOnClickListener { bookingViewModel.addChildTicket() }
        binding.btnMinChild.setOnClickListener { bookingViewModel.removeChildTicket() }
    }

    private fun observeViewModel() {
       bookingViewModel.bookingData.observe(viewLifecycleOwner) { data ->
            binding.tvAdultCount.text = data.adultTickets.toString()
            binding.tvChildCount.text = data.childTickets.toString()
            binding.namafilm.text = data.movieTitle
            binding.session.text = data.session
            binding.theater.text = data.theater
            binding.buffet.text = data.selectedBuffet ?: "None"
            binding.quantity.text = "${data.adultTickets} Adult, ${data.childTickets} Child"
            binding.seat.text = data.selectedSeats.joinToString(", ").ifEmpty { "Not selected" }
            binding.totalamount.text = "$ ${String.format("%.2f", data.totalPrice)}"

           seatAdapter.setData(seatList, data.selectedSeats)
        }
        bookingViewModel.takenSeats.observe(viewLifecycleOwner) { takenSeats ->
            loadSeatLayout(takenSeats)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
