package com.example.genshinhelper

import adapters.MyViewPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.genshinhelper.R
import com.google.android.material.tabs.TabLayout

class Details : AppCompatActivity() {

lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        bundle = intent.extras!!

        val tabLayout : TabLayout = findViewById(R.id.tab_layout)
        val viewPager2 : ViewPager2 = findViewById(R.id.view_pager2)
        val myViewPagerAdapter : MyViewPagerAdapter = MyViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = myViewPagerAdapter
        supportActionBar!!.hide()
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)!!.select()
            }
        })


    }
    fun getIdChar() : Int {
        return bundle.getInt("id")
    }
}