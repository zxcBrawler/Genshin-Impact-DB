package com.example.genshinhelper

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import models.userInfo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RealPathUtil
import server.RequestBuilder



class RegisterPage : AppCompatActivity() {

    private val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
    private lateinit var image : ImageView


    private var path : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val login : TextView = findViewById(R.id.login)
        val email : TextView = findViewById(R.id.email)
        val password : TextView = findViewById(R.id.passwordTXT)
        val confirmPassword : TextView = findViewById(R.id.confirmPasswordTXT)
        supportActionBar!!.hide()
        val choosePhoto: Button = findViewById(R.id.upload_photo)
        val register: Button = findViewById(R.id.regbtn)
         image = findViewById(R.id.image)

        register.setOnClickListener {
            val getAllUsers: Call<List<User>> = api.getUsers()
            getAllUsers.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    val listUsers: List<User>? = response.body()
                    listUsers?.forEach {
                        if (it.loginUser == login.text || it.passwordUser == password.text || it.emailUser == email.text) {
                            Toast.makeText(
                                applicationContext,
                                "Данный пользователь уже существует",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            val reg: Call<User> = api.register(
                                User(
                                    0,
                                    login.text.toString(),
                                    path,
                                    password.text.toString(),
                                    email.text.toString()
                                )
                            )

                            if (password.text != confirmPassword.text) {
                                reg.enqueue(object : Callback<User> {
                                    override fun onResponse(
                                        call: Call<User>,
                                        response: Response<User>
                                    ) {
                                        Toast.makeText(applicationContext, "Успешная регистрация", Toast.LENGTH_LONG)
                                            .show()
                                        val intent = Intent(applicationContext, LoginPage::class.java)
                                        startActivity(intent)
                                        finish()
                                    }

                                    override fun onFailure(call: Call<User>, t: Throwable) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Ошибка со стороны клиента",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                })
                            }
                            else {
                                Toast.makeText(
                                    applicationContext,
                                    "Пароли не совпадают",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Ошибка со стороны клиента",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }

        choosePhoto.setOnClickListener {
              val intent : Intent = Intent()
              intent.type = "image/*"
              intent.action = Intent.ACTION_GET_CONTENT
              startActivityForResult(intent,10)
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            path = RealPathUtil.getRealPath(applicationContext, uri)!!
            image.setImageURI(uri)
        }
    }
}