package com.example.mkotlin

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.mkotlin.constants.TabPageAdapter
import com.example.mkotlin.constants.anim
import com.example.mkotlin.constants.vibration
import com.example.mkotlin.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(){
    private lateinit var classbinding:ActivityMainBinding
    private var pref: SharedPreferences? = null

    @Suppress("DEPRECATION")
    override fun onCreate(name: Bundle?) {
        super.onCreate(name)
        classbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        pref = this.getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        anim(classbinding.tabLayout, R.anim.quast_land_anim, this)
        classbinding.viewPager.adapter = TabPageAdapter(this, classbinding.tabLayout.tabCount)

        classbinding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {classbinding.tabLayout.selectTab(classbinding.tabLayout.getTabAt(position))}
        })

        classbinding.tabLayout.getTabAt(1)?.icon?.setColorFilter(getColor(R.color.teal_700), PorterDuff.Mode.SRC_IN)
        classbinding.tabLayout.getTabAt(2)?.icon?.setColorFilter(getColor(R.color.teal_700), PorterDuff.Mode.SRC_IN)
        for (i in 0..2) classbinding.tabLayout.getTabAt(i)?.view?.setOnClickListener {vibration(it)}

        classbinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(tab: TabLayout.Tab) {
                classbinding.viewPager.currentItem = tab.position
                if (pref?.getBoolean("switch_anim", true) == true)
                    classbinding.tabLayout.tabIndicatorAnimationMode = TabLayout.INDICATOR_ANIMATION_MODE_ELASTIC
                else
                    classbinding.tabLayout.tabIndicatorAnimationMode = TabLayout.INDICATOR_ANIMATION_MODE_FADE
                val colorFrom = ContextCompat.getColor(applicationContext, R.color.teal_700)
                val colorTo = ContextCompat.getColor(applicationContext, R.color.red)
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                if (pref?.getBoolean("switch_anim", true) == true)
                    colorAnimation.duration = 300
                else
                    colorAnimation.duration = 0
                colorAnimation.addUpdateListener {animator -> tab.icon?.setColorFilter(animator.animatedValue as Int, PorterDuff.Mode.SRC_IN)}
                colorAnimation.start()
            }

            @SuppressLint("ResourceAsColor")
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val colorFrom = ContextCompat.getColor(applicationContext, R.color.red)
                val colorTo = ContextCompat.getColor(applicationContext, R.color.teal_700)
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                if (pref?.getBoolean("switch_anim", true) == true)
                    colorAnimation.duration = 300
                else
                    colorAnimation.duration = 0
                colorAnimation.addUpdateListener {animator ->
                    if (tab != null)
                        tab.icon?.setColorFilter(animator.animatedValue as Int, PorterDuff.Mode.SRC_IN)
                }
                colorAnimation.start()
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (classbinding.tabLayout.selectedTabPosition == 2)
            anim(findViewById<TextView>(R.id.textView), R.anim.change, this)
        else
            classbinding.tabLayout.getTabAt(2)?.select()
    }
}