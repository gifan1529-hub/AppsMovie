package com.example.appsmovie.DetailTicket

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsmovie.DetailTicket2.detailTicket2
import com.example.appsmovie.R
import com.example.appsmovie.RoomDatabase.BookingHistory
import kotlin.jvm.java

class DetailTicketAdapter (private val ticketList: List<BookingHistory>) :
    RecyclerView.Adapter<DetailTicketAdapter.TicketViewHolder>() {

    class TicketViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvTitle: TextView = view.findViewById(R.id.tv_detail_title)
        private val tvSession: TextView = view.findViewById(R.id.tv_session)
        private val tvTheater: TextView = view.findViewById(R.id.tv_theater)
        private val posterimage: ImageView = view.findViewById(R.id.iv_detail)

        fun bind(ticket: BookingHistory) {
            tvTitle.text = ticket.movieTitle
            tvSession.text = ticket.session
            tvTheater.text = ticket.theater

            Glide.with(itemView.context)
                .load(ticket.moviePosterUrl)
                .placeholder(R.drawable.item)
                .into(posterimage)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detailticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TicketViewHolder,
        position: Int
    ) {
        val ticket = ticketList[position]
        holder.bind(ticket)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, detailTicket2::class.java).apply {
                putExtra("BOOKING_ID", ticket.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }
}