package com.example.mkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mkotlin.constants.TabPageAdapter
import com.example.mkotlin.constants.anim
import com.example.mkotlin.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(){
    private lateinit var classbinding:ActivityMainBinding

    override fun onCreate(name: Bundle?) {
        super.onCreate(name)
        classbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        anim(classbinding.tabLayout, R.anim.quast_land_anim, this)
        setUpTabBar()
    }

    private fun setUpTabBar() {
        classbinding.viewPager.adapter = TabPageAdapter(this, classbinding.tabLayout.tabCount)

        classbinding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                classbinding.tabLayout.selectTab(classbinding.tabLayout.getTabAt(position))
            }
        })

        classbinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                classbinding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (classbinding.tabLayout.selectedTabPosition != 2) {
            classbinding.tabLayout.getTabAt(2)?.select()
        }
    }
}