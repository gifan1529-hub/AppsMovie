package com.example.appsmovie

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyMovieApp: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels(){
        // ngecek versi android
        // harus android 8
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val downloadChannel = NotificationChannel(
                "TICKET_DOWNLOAD_CHANNEL",
                "Unduhan Tiket",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifikasi saat tiket berhasil diunduh"
            }

            val paymentChannel = NotificationChannel(
                "PYAMENT_SUCCES_CHANNEL",
                "Konfirmasi Pembayaran",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifikasi saat pembayaran berhasil"
            }

            val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(downloadChannel)
            notificationManager.createNotificationChannel(paymentChannel)
        }
    }


}