package com.example.genshinhelper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import okhttp3.internal.notify

class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(context!!, "channel02")
            .setSmallIcon(R.drawable.alhaitam)
            .setContentTitle("Ваша смола полная")
            .setContentText("ddd")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(200, builder.build())
    }
}