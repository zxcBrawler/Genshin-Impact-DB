package com.example.genshinhelper

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.concurrent.TimeUnit


class TimerResign : AppCompatActivity() {

    private companion object{
        private const val CHANNEL_ID2 = "channel02"
        private const val resignCapacity = 160
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_resign)
        supportActionBar!!.hide()
        createNotificationChannel()
        var currentResign : Int
        val resign : EditText = findViewById(R.id.resign)
        val button : Button = findViewById(R.id.btn)
        val text : TextView = findViewById(R.id.text)

        button.setOnClickListener {
            if(resign.text.toString().toInt() <= resignCapacity){
                currentResign = resign.text.toString().toInt()
                Toast.makeText(this, "${(resignCapacity - currentResign) * 8}", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ReminderBroadcast::class.java)
                val pendingIntent : PendingIntent = PendingIntent.getBroadcast(this,0,intent,
                    FLAG_IMMUTABLE)

                val alarmManager : AlarmManager =  getSystemService(ALARM_SERVICE) as AlarmManager

                val currentTime = System.currentTimeMillis()

                val timeInMillis = (resignCapacity - currentResign) * 60000
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, currentTime + timeInMillis, pendingIntent)
                text.text = "Ваша смола заполнится через ${fromMinutesToHHmm((resignCapacity - currentResign) * 8) } "

            }
            else {
                Toast.makeText(this, "Максимальное значение смолы 160", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun fromMinutesToHHmm(minutes: Int): String {
        val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
        val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
        return String.format("%02d:%02d", hours, remainMinutes)
    }


    private fun createNotificationChannel(){
        val name : CharSequence = "MyNotification"
        val description = "My notification description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(CHANNEL_ID2, name, importance)
        notificationChannel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}