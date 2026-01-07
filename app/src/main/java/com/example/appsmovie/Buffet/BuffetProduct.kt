package com.example.appsmovie.Buffet

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appsmovie.BookingManager
import com.example.appsmovie.R
import com.example.core.UseCase_Repository.Tickets.BuffetItem
import com.example.appsmovie.Ticket.BookingTicketVM
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.ReadOnlyProperty
@AndroidEntryPoint
class BuffetProduct : AppCompatActivity() {

    private val viewModel: BookingTicketVM = BookingManager.viewModel

    private lateinit var buffetAdapter: BuffetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.buffet)

        val btnBack = findViewById<ImageView>(R.id.bek)
        btnBack.setOnClickListener {
            finish()
        }

        buffetAdapter = BuffetAdapter(
            addItem = { item -> viewModel.addBuffetItem(item) },
            removeItem = { item -> viewModel.removeBuffetItem(item) }
        )

        val recyclerView = findViewById<RecyclerView>(R.id.rv_buffet_items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = buffetAdapter

        observeViewModel()

        val btnAddToCart = findViewById<Button>(R.id.btn_add_cart)
        btnAddToCart.setOnClickListener {
            viewModel.confirmBuffetSelection()

            Toast.makeText(this, "Buffet items added!", Toast.LENGTH_SHORT).show()

            finish()
        }
    }

    private fun observeViewModel() {
         viewModel.buffetMenuList.observe(this) { items ->
            items?.let {
                buffetAdapter.setData(it)
            }
        }

        viewModel.bookingData.observe(this) { data ->
            val tvTotal = findViewById<TextView>(R.id.total)
            tvTotal.text = "$ ${String.format("%.2f", data.buffetSubtotal)}"
        }
    }
}
