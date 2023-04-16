package com.example.genshinhelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.genshinhelper.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import fragments.*
import java.util.function.ToLongBiFunction

class MainActivity : AppCompatActivity() {

    var a = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        val tooltips = Tooltips(this)
        a = 0
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_menu)
        replaceFragment(MainPage())
        bottomNav.selectedItemId = R.id.main

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.chars -> {
                    replaceFragment(CharsFragment())
                    tooltips.charsTooltip.showAlignTop(bottomNav)
                }
                R.id.map -> {
                    replaceFragment(MapFragment())
                    tooltips.mapTooltip.showAlignTop(bottomNav)
                }
                R.id.main -> {
                    replaceFragment(MainPage())
                    tooltips.mainTooltip.showAlignTop(bottomNav)
                }
                R.id.profile -> {
                    replaceFragment(Profile())
                    tooltips.profileTooltip.showAlignTop(bottomNav)
                }
                R.id.artef -> {
                    replaceFragment(GameItems())
                    tooltips.weaponsTooltip.showAlignTop(bottomNav)
                }
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



