package com.example.genshinhelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.genshinhelper.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import fragments.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_menu)
        replaceFragment(MainPage())
        bottomNav.selectedItemId = R.id.main

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.chars -> replaceFragment(CharsFragment())
                R.id.map -> replaceFragment(MapFragment())
                R.id.main -> replaceFragment(MainPage())
                R.id.profile -> replaceFragment(Profile())
                R.id.artef -> replaceFragment(GameItems())
            }
            true
        }
    }
        private fun replaceFragment(fragment: Fragment) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, fragment)
            fragmentTransaction.commit()
        }
    }



