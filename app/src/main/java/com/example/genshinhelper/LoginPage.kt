package com.example.genshinhelper

import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import models.userInfo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates
import kotlin.random.Random



class LoginPage : AppCompatActivity() {
    private lateinit var sharedPreferences : SharedPreferences
    private var randomNumber by Delegates.notNull<Int>()

    private companion object{
        private const val CHANNEL_ID = "channel01"
    }

    private val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
    private lateinit var loginText : EditText
    private lateinit var passwdText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        supportActionBar!!.hide()
        sharedPreferences = this.getSharedPreferences("pref",0)
        val regButton : Button = findViewById(R.id.regButton)
        val loginButton : Button = findViewById(R.id.logInButton)
        loginText  = findViewById(R.id.login)
        passwdText = findViewById(R.id.password)

       loginButton.setOnClickListener {
                login()
        }

        regButton.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }
private fun login() {
    val randomGenerator = Random(System.currentTimeMillis())
     randomNumber = randomGenerator.nextInt(1000, 9999)
    val getUsers : Call<List<User>> = api.getUsers()
    getUsers.enqueue(object : Callback<List<User>>{
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
            if (response.isSuccessful){
                val allUsers : List<User>? = response.body()
                allUsers?.forEach{
                    if(it.loginUser == loginText.text.toString() && it.passwordUser == passwdText.text.toString()){
                        storeData(User(it.idUser,loginText.text.toString(),it.imageUser, passwdText.text.toString(),it.emailUser))
                        loggedIn(true)
                      val dialogBinding = layoutInflater.inflate(R.layout.dialog_input, null)
                        val myDialog  = Dialog(this@LoginPage)
                        myDialog.setContentView(dialogBinding)
                        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        myDialog.show()

                        showNotification()

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("login", loginText.text.toString())
                        intent.putExtra("pass", passwdText.text.toString())


                        val submitButton = dialogBinding.findViewById<Button>(R.id.inputButton)
                        val inputField = dialogBinding.findViewById<EditText>(R.id.inputCode)
                        submitButton.setOnClickListener {
                            if (inputField.text.toString() == randomNumber.toString()){
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
            }
            else {
                Toast.makeText(applicationContext,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<List<User>>, t: Throwable) {
            Toast.makeText(applicationContext,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
        }

    })

}
    private fun showNotification() {
        createNotificationChannel()
        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
        notificationBuilder.setSmallIcon(R.drawable.alhaitam)
        notificationBuilder.setContentTitle("Код подтверждения")
        notificationBuilder.setContentText("Ваш код подтверждения - $randomNumber")
        notificationBuilder.priority = NotificationCompat.PRIORITY_DEFAULT
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(notificationId, notificationBuilder.build())
    }

    private fun createNotificationChannel(){
    val name : CharSequence = "MyNotification"
        val description = "My notification description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
        notificationChannel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onStart() {
        super.onStart()
        if (auth()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("login", sharedPreferences.getString("login", ""))
            intent.putExtra("pass", sharedPreferences.getString("pass", ""))
            startActivity(intent)
        }
    }

    private fun auth() : Boolean{
        return getUserLoggedIn()
    }

    fun storeData(user : User){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("login",user.loginUser)
        editor.putString("pass",user.passwordUser)
        editor.apply()
    }

    fun clearData() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    fun loggedIn(logIn : Boolean){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("loggedIn", logIn)
        editor.apply()
    }

    private fun getUserLoggedIn() : Boolean{
        return sharedPreferences.getBoolean("loggedIn", false)
    }

}
