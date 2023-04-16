package com.example.genshinhelper

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit


class TimerResign : AppCompatActivity() {

    private companion object{
        private const val CHANNEL_ID2 = "channel02"
        private const val resignCapacity = 160
    }
  private lateinit var  sharedPreferences : SharedPreferences

    private lateinit var timer : CountDownTimer
    private var currentTime = 0L
    private lateinit var time : TextView
    private lateinit var  resign : EditText
    private lateinit var btnStart : Button
    var currentResign : Int = 0
    private lateinit var pendingIntent : PendingIntent
    private lateinit var alarmManager : AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_resign)
        supportActionBar!!.hide()
        createNotificationChannel()
sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        pendingIntent =  PendingIntent.getBroadcast(this,0,intent,
        FLAG_IMMUTABLE)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
         resign = findViewById(R.id.resign)
        btnStart = findViewById(R.id.btnStart)
        val btnStop : Button = findViewById(R.id.btnStop)
        time = findViewById(R.id.time)

        if (sharedPreferences.getLong("time",0L) != 0L){
            startTimer(sharedPreferences.getLong("time",0L))
        }

        btnStart.setOnClickListener {
               if(resign.text.toString().toInt() <= resignCapacity){
                currentResign = resign.text.toString().toInt()
                Toast.makeText(this, "${(resignCapacity - currentResign) * 8}", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ReminderBroadcast::class.java)



                var currentTime = System.currentTimeMillis()

                val timeInMillis = (resignCapacity - currentResign) * 60000 * 8

               startTimer(timeInMillis.toLong())
            }
            else {
                Toast.makeText(this, "Максимальное значение смолы 160", Toast.LENGTH_LONG).show()
            }
        }
        btnStop.setOnClickListener {
            timer.cancel()
            time.text = "00:00:00"
        }
    }

        private fun hmsTimeFormatter(milliSeconds: Long): String? {
            return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
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

    private fun startTimer(timeStart : Long){
                timer = object : CountDownTimer(timeStart,1000){
                    override fun onTick(millisUntilFinished: Long) {
                        time.text = hmsTimeFormatter(millisUntilFinished)
                        currentTime = millisUntilFinished
                    }

                    override fun onFinish() {
                        time.text = ""
                    }

                }.start()
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, currentTime + timeStart, pendingIntent)
            }
    override fun onStop() {
        super.onStop()
        val sharedPreferences : SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val sharedPreferencesEditor : SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putLong("time",currentTime)
        sharedPreferencesEditor.apply()

    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences : SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val sharedPreferencesEditor : SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putLong("time",currentTime)
        sharedPreferencesEditor.apply()
    }
    override fun onStart() {
        super.onStart()
        val sharedPreferences : SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        currentTime = sharedPreferences.getLong("time",0)
        time.text = hmsTimeFormatter(currentTime)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putLong("time",currentTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        time.text = hmsTimeFormatter(savedInstanceState.getLong("time"))

        timer = object : CountDownTimer(savedInstanceState.getLong("time"),1000){
            override fun onTick(millisUntilFinished: Long) {
                time.text = hmsTimeFormatter(millisUntilFinished)
                currentTime = millisUntilFinished
            }

            override fun onFinish() {
                time.text = ""
            }

        }.start()

    }
}