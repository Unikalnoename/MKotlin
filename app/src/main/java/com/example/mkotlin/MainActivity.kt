package com.example.mkotlin

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.mkotlin.constants.DrctrsStuff
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
        anim(classbinding.tabLayout, R.anim.alpha_up)
        classbinding.viewPager.adapter = TabPageAdapter(this, classbinding.tabLayout.tabCount)

        classbinding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {classbinding.tabLayout.selectTab(classbinding.tabLayout.getTabAt(position))}
        })

        for (i in 1 until classbinding.tabLayout.tabCount) classbinding.tabLayout.getTabAt(i)?.icon?.setColorFilter(getColor(R.color.teal_700), PorterDuff.Mode.SRC_IN)

        if (DrctrsStuff.current_item != 0) {
            val ani = AnimationUtils.loadAnimation(this, R.anim.alpha_up)
            ani.startOffset = 400
            anim(classbinding.tabLayout, ani)
        }
        else {
            var so = 0L
            for (i in 0 until classbinding.tabLayout.tabCount) {
                val ani = AnimationUtils.loadAnimation(this, R.anim.alpha_up)
                ani.startOffset = so
                classbinding.tabLayout.getTabAt(i)?.let {anim(it.view, ani)}
                so += 275
            }
        }

        for (i in 0 until classbinding.tabLayout.tabCount) {
            classbinding.tabLayout.getTabAt(i)?.view?.y = -5F
            classbinding.tabLayout.getTabAt(i)?.view?.setOnClickListener {vibration(it)}
        }

        classbinding.tabLayout.getTabAt(0)?.let {anim(it.view, R.anim.decrease)}
        if (pref?.getBoolean("switch_anim", true) == false) {
            classbinding.tabLayout.getTabAt(0)?.view?.scaleX = 1.6F
            classbinding.tabLayout.getTabAt(0)?.view?.scaleY = 1.6F
        }

        classbinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(tab: TabLayout.Tab) {
                classbinding.viewPager.currentItem = tab.position
                DrctrsStuff.current_item = classbinding.viewPager.currentItem
                if (classbinding.tabLayout.selectedTabPosition == classbinding.tabLayout.tabCount - 1) {
                    if (DrctrsStuff.yes_pressed) findViewById<Button>(R.id.btn_yes).visibility = View.VISIBLE
                    DrctrsStuff.yes_pressed = false
                }
                val colorFrom = ContextCompat.getColor(applicationContext, R.color.teal_700)
                val colorTo = ContextCompat.getColor(applicationContext, R.color.red)
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                if (pref?.getBoolean("switch_anim", true) == true) {
                    classbinding.tabLayout.tabIndicatorAnimationMode = TabLayout.INDICATOR_ANIMATION_MODE_ELASTIC
                    colorAnimation.duration = 300
                }
                else {
                    classbinding.tabLayout.tabIndicatorAnimationMode = TabLayout.INDICATOR_ANIMATION_MODE_FADE
                    colorAnimation.duration = 0
                }
                colorAnimation.addUpdateListener {animator -> tab.icon?.setColorFilter(animator.animatedValue as Int, PorterDuff.Mode.SRC_IN)}
                colorAnimation.start()
                tab.view.scaleX = 1F
                tab.view.scaleY = 1F
                val ani = AnimationUtils.loadAnimation(applicationContext, R.anim.decrease)
                if (pref?.getBoolean("switch_anim", true) == false)
                    ani.duration = 0
                tab.view.startAnimation(ani)
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
                colorAnimation.addUpdateListener {animator -> if (tab != null) tab.icon?.setColorFilter(animator.animatedValue as Int, PorterDuff.Mode.SRC_IN)}
                colorAnimation.start()
                if (tab != null) {
                    tab.view.scaleX = 1.6F
                    tab.view.scaleY = 1.6F
                    val ani = AnimationUtils.loadAnimation(applicationContext, R.anim.increase)
                    if (pref?.getBoolean("switch_anim", true) == false)
                        ani.duration = 0
                    tab.view.startAnimation(ani)
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (classbinding.tabLayout.selectedTabPosition == classbinding.tabLayout.tabCount - 1)
            anim(findViewById<TextView>(R.id.textView), R.anim.change)
        else
            classbinding.tabLayout.getTabAt(classbinding.tabLayout.tabCount - 1)?.select()
    }

    private fun anim(v: View, res: Int) {anim(v, res, this)}
}