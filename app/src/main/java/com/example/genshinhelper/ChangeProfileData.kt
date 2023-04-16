package com.example.genshinhelper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView
import models.userInfo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RealPathUtil
import server.RequestBuilder

class ChangeProfileData : AppCompatActivity() {
    private var path : String = ""
    private lateinit var imageUser : CircleImageView
    private val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile_data)
        supportActionBar?.hide()
        val loginPage = LoginPage()
        imageUser  = findViewById(R.id.image)
        val loginChange : TextView = findViewById(R.id.loginChange)
        val emailChange : TextView = findViewById(R.id.emailChange)
        val upload : Button = findViewById(R.id.upload_photo)
        val changeProfile : Button = findViewById(R.id.edit)
        val passwordChange : TextView = findViewById(R.id.passwordTXTChange)
        val id = intent.getIntExtra("id",1)
        val getUserById : Call<User> = api.getUserById(id)


        getUserById.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    val user = response.body()
                    imageUser.setImageURI(user?.imageUser?.toUri())
                    path = user?.imageUser.toString()
                    loginChange.text = user?.loginUser
                    emailChange.text = user?.emailUser
                    passwordChange.text = user?.passwordUser
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
            }
        })
        upload.setOnClickListener {
            val intent  = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,10)
        }
        changeProfile.setOnClickListener {

            if (loginChange.text.toString() == "" ||
                passwordChange.text.toString() == "" ||
                emailChange.text.toString() == ""
            ) {

                Snackbar.make(
                    View(this@ChangeProfileData),
                    "Заполните все поля",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val postData: Call<User> = api.changeProfileInfo(
                    id,
                    User(
                        id,
                        loginChange.text.toString(),
                        path,
                        passwordChange.text.toString(),
                        emailChange.text.toString()
                    )
                )

                postData.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@ChangeProfileData,
                                "Успешное изменение, войдите заново",
                                Toast.LENGTH_LONG
                            ).show()
                            loginPage.clearData()
                            val intent = Intent(this@ChangeProfileData, LoadingScreen::class.java)
                            startActivity(intent)
                            finish()
                        }
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
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            path = RealPathUtil.getRealPathFromURI(applicationContext, uri!!)!!
            imageUser.setImageURI(uri)
        }
    }
}