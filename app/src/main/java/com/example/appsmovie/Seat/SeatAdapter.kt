 package com.example.appsmovie.Seat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.appsmovie.R

data class Seat(val id: String, var status: SeatStatus)
enum class SeatStatus { AVAILABLE, TAKEN, SELECTED }

class SeatAdapter(
    private val onSeatClick: (Seat) -> Unit
) : RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {
    private var seats: List<Seat> = emptyList()
    private var selectedSeatIds: Set<String> = emptySet()

    fun setData(newSeats: List<Seat>, newSelectedSeatIds: Set<String>) {
        this.seats = newSeats
        this.selectedSeatIds = newSelectedSeatIds
        notifyDataSetChanged()
    }


    inner class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val seatView: ImageView = itemView.findViewById(R.id.seat_view)

        fun bind(seat: Seat) {
            val currentStatus = when {
                selectedSeatIds.contains(seat.id) -> SeatStatus.SELECTED
                seat.status == SeatStatus.TAKEN -> SeatStatus.TAKEN
                else -> SeatStatus.AVAILABLE
            }

            // ngubah tmampilah kursi nya
            when (currentStatus) {
                SeatStatus.AVAILABLE -> seatView.setImageResource(R.drawable.belompilih)
                SeatStatus.TAKEN -> seatView.setImageResource(R.drawable.udahdipilih)
                SeatStatus.SELECTED -> seatView.setImageResource(R.drawable.udahpilih)
            }

            // Kursi yang udah diambil ga bisa diklik
            if (seat.status != SeatStatus.TAKEN) {
                itemView.setOnClickListener { onSeatClick(seat) }
            } else {
                itemView.setOnClickListener(null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemseat, parent, false)
        return SeatViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.bind(seats[position])
    }

    override fun getItemCount(): Int = seats.size
}
