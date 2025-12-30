package com.example.appsmovie.DetailTicket2

import android.content.ContentValues
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.example.appsmovie.R
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appsmovie.databinding.DetailticketsBinding

class detailTicket2 : AppCompatActivity() {

    private lateinit var binding: DetailticketsBinding
    private val viewModel: DetailTicket2VM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = DetailticketsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookingId = intent.getIntExtra("BOOKING_ID", -1)
        if (bookingId != -1) {
            Log.d("DetailTicketDebug", "Booking ID: $bookingId")
            viewModel.getBookingById(bookingId).observe(this) { booking ->
                if (booking != null) {
                    binding.movietitle.text = booking.movieTitle
                    binding.ticket.text = "${booking.adultTickets} Adult, ${booking.childTickets} Child"
                    binding.sessions.text = booking.session
                    binding.seatnumber.text = booking.seatIds.joinToString(", ")
                    binding.buffets.text = booking.buffetItems
                    binding.theater.text = booking.theater

                    Glide.with(this)
                        .load(booking.moviePosterUrl)
                        .placeholder(R.drawable.item)
                        .into(binding.ivDetails)
                }
            }
        }
        binding.ivBacks.setOnClickListener {
            finish()
        }

        binding.toPDF.setOnClickListener {
            exportViewToPdf(binding.ticketContainer, "MyTicket_${bookingId}.pdf")
        }
    }

    private fun exportViewToPdf(view: android.view.View, fileName: String) {

        // ukuran
        val pageHeight = 2450
        val pageWidth = 1220

        val pdfDocument = PdfDocument()
        // nge buat satu halaman yang udah di tentukan ukurannya
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        // buat halaman
        val page = pdfDocument.startPage(pageInfo)
        // dapet canvas sebagai area untuk pdf nya
        val canvas = page.canvas

            // mastiin kalo lebar sama seperti halaman pdf
            val measureWidth = android.view.View.MeasureSpec.makeMeasureSpec(pageWidth, android.view.View.MeasureSpec.EXACTLY)
            // biarin tinggi mastiin suseai kebutuhannya sendiri
            val measureHeight = android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED)
            // view udah dapet ukuran
            view.measure(measureWidth, measureHeight)
            // nata posisi view halaman, di mulai dari pojok kiri atas sampai selebar dan setinggi yang sudah di tetntuin
            view.layout(0, 0, pageWidth, pageHeight)

        // nge gambar canvas
        view.draw(canvas)
        pdfDocument.finishPage(page)

        val contentValues = ContentValues().apply {
            // nentuin nama file yang ditampilin di file manager
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            // nentuin tipe file
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            // khusus android 10 atau lebih
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Memberi tahu sistem untuk menyimpan file ini di folder Downloads publik.
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
        }

        val resolver = applicationContext.contentResolver
        // minta sistem buat bikin file kosong di folder download
        // sistem akan mambuat URL untuk file yang baru dibuat
        val url = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (url != null) {
            try {
                resolver.openOutputStream(url)?.use { outputStream ->
                    pdfDocument.writeTo(outputStream)
                    Toast.makeText(this, "Tiket berhasil diunduh", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Gagal mengunduh tiket", Toast.LENGTH_SHORT).show()
            }
        }
        pdfDocument.close()
    }
}

