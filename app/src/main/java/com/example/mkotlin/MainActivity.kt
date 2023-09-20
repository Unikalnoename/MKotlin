package com.example.mkotlin

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mkotlin.constants.TabPageAdapter
import com.example.mkotlin.constants.anim
import com.example.mkotlin.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(){
    private lateinit var classbinding:ActivityMainBinding

    @Suppress("DEPRECATION")
    override fun onCreate(name: Bundle?) {
        super.onCreate(name)
        classbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        anim(classbinding.tabLayout, R.anim.quast_land_anim, this)
        classbinding.viewPager.adapter = TabPageAdapter(this, classbinding.tabLayout.tabCount)

        classbinding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                classbinding.tabLayout.selectTab(classbinding.tabLayout.getTabAt(position))
            }
        })

        classbinding.tabLayout.getTabAt(1)?.icon?.setColorFilter(Color.parseColor("#FF018786"), PorterDuff.Mode.SRC_IN)
        classbinding.tabLayout.getTabAt(2)?.icon?.setColorFilter(Color.parseColor("#FF018786"), PorterDuff.Mode.SRC_IN)

        classbinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                classbinding.viewPager.currentItem = tab.position
                tab.icon?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab != null)
                    tab.icon?.setColorFilter(Color.parseColor("#FF018786"), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (classbinding.tabLayout.selectedTabPosition == 2)
            anim(findViewById<TextView>(R.id.textView), R.anim.change, this)
        else
            classbinding.tabLayout.getTabAt(2)?.select()
    }
}