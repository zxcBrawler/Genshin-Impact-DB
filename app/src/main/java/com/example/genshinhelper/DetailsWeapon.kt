package com.example.genshinhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.genshinhelper.R
import com.squareup.picasso.Picasso
import models.Weapons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder

class DetailsWeapon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_weapon)
        supportActionBar?.hide()
        val id = intent.getIntExtra("id", -1)
        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        val getWeaponById : Call<Weapons> = api.getWeaponsById(id)
        val imageWeapon : ImageView = findViewById(R.id.fullImageWeapon)
        val fullNameWeapon : TextView = findViewById(R.id.full_nameWeapon)
        val typeWeapon : TextView = findViewById(R.id.weaponType)
        val stars : ImageView = findViewById(R.id.stars)
        getWeaponById.enqueue(object : Callback<Weapons>{
            override fun onResponse(call: Call<Weapons>, response: Response<Weapons>) {
                if (response.isSuccessful){
                    val weapon = response.body()
                    Picasso.get().load(weapon!!.imageWeapon).fit().into(imageWeapon)
                    fullNameWeapon.text = weapon.nameWeapon
                    typeWeapon.text = weapon.typeWeapon.nameTypeWeapon
                    when (weapon.rarityWeapon) {
                        5 -> {
                            stars.setImageResource(R.drawable.star5)
                        }
                        4 -> {
                            stars.setImageResource(R.drawable.star4)
                        }
                        3 -> {
                            stars.setImageResource(R.drawable.star3)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Weapons>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}