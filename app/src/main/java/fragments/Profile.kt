package fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64.decode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import models.userInfo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder
import android.util.Base64
import android.widget.Button
import android.widget.Toast
import androidx.core.net.toUri
import com.example.genshinhelper.*
import com.squareup.picasso.Picasso
import server.RealPathUtil
import java.io.UnsupportedEncodingException
import java.lang.System.out
import java.nio.charset.StandardCharsets

import java.util.*
import kotlin.system.exitProcess


class Profile : Fragment() {

    private val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view : View = inflater.inflate(R.layout.fragment_profile, container, false)
        var userId = 0
        val loginPage = LoginPage()
        val loginText : TextView = view.findViewById(R.id.login)
        val passwdText : TextView = view.findViewById(R.id.email)
        val logout : Button = view.findViewById(R.id.logout)
        val editProfile : Button = view.findViewById(R.id.change_profile)
        val profileImage : ImageView = view.findViewById(R.id.profileImage)
        val login = requireActivity().intent.getStringExtra("login")!!
        val pass = requireActivity().intent.getStringExtra("pass")!!
        val getUsers : Call<List<User>> = api.getUsers()

        getUsers.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful){
                    val allUsers : List<User>? = response.body()
                    allUsers?.forEach {
                        if(it.loginUser == loginText.text && it.passwordUser == passwdText.text){
                            val path = it.imageUser
                            userId = it.idUser
                            val bitmap = BitmapFactory.decodeFile(path)
                            profileImage.setImageBitmap(bitmap)

                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(view.context, "Ошибка со стороны клиента", Toast.LENGTH_SHORT).show()
            }
        })
        loginText.text = login
        passwdText.text = pass

        logout.setOnClickListener {
            loginPage.clearData()
            val intent = Intent(view.context, LoadingScreen::class.java)
            startActivity(intent)
            activity?.finish()
        }
        editProfile.setOnClickListener {
            val intent = Intent(view.context, ChangeProfileData::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }
        return view
    }
}