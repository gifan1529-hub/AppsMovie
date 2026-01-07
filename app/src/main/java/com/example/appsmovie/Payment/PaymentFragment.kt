package com.example.appsmovie.Payment

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appsmovie.BookingManager
import com.example.appsmovie.R
import com.example.core.Database.BookingDatabase.BookingHistory
import com.example.appsmovie.Ticket.BookingTicketVM
import com.example.appsmovie.databinding.FragmentPaymentBinding
import java.text.NumberFormat
import java.util.Locale
import android.Manifest
import com.example.appsmovie.DetailTicket2.detailTicket2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val paymentViewModel: PaymentFragmentVM by viewModels()
    private val bookingTicketVM: BookingTicketVM = BookingManager.viewModel

    private val  requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Izin di confirm", Toast.LENGTH_SHORT).show()
                confirmPayment()
            } else {
                Toast.makeText(requireContext(), "Izin ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookingTicketVM.bookingData.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                binding.tvPaymentTotal.text = format.format(data.totalPrice)
            }
        }

        bookingTicketVM.paymentTrigger.observe(viewLifecycleOwner) { shouldPay ->
            if (shouldPay == true) {
                Log.e("PaymentFragment", "${bookingTicketVM.bookingData.value}")
                askForNotifPermission()
                bookingTicketVM.onPaymentFinished()
            }
        }
       observeViewModel()
    }

    private fun askForNotifPermission() {
        // ngecek ver android
        // kalo android 13 keatas bakal ada permission
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                confirmPayment()
            } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            confirmPayment()
        }
    }

    private fun confirmPayment(){
        paymentViewModel.onPaymentConfirmed(
            bookingData = bookingTicketVM.bookingData.value,
            selectedPaymentMethodId = binding.paymentRadioGroup.checkedRadioButtonId
        )
    }

    private fun observeViewModel() {
        paymentViewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

        paymentViewModel.navigateToResult.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate == true) {
                findNavController().navigate(R.id.action_paymentFragment_to_payementResultFragment)
                paymentViewModel.onNavigationComplete()
            }
        }

        paymentViewModel.showNotificationEvent.observe(viewLifecycleOwner) { booking ->
            booking?.let {
                showPaymentSuccessNotification(it)
                paymentViewModel.onNotificationShown()
            }
        }
    }

    private fun showPaymentSuccessNotification(booking: BookingHistory){
        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(requireContext(), detailTicket2::class.java).apply {
        putExtra("BOOKING_ID", booking.id)
            Log.d("Notif", "Booking ID: ${booking.id}")
        }
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            booking.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(requireContext(), "PYAMENT_SUCCES_CHANNEL")
            .setSmallIcon(R.drawable.centang )
            .setContentTitle("Pembayaran Berhasil!")
            .setContentText("Tiket Anda untuk ${booking.movieTitle} sudah dikonfirmasi.")
            .setPriority(NotificationCompat.PRIORITY_HIGH) // biar ada pop up
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // kalo di pencet ilang
            .build()

        notificationManager.notify(booking.id, notification)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
