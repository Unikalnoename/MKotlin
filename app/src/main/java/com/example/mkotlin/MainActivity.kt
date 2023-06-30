@file:Suppress("UNUSED_PARAMETER")

package com.example.mkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var classbinding:ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    private var pref:SharedPreferences? = null
    private var count:Long = 0
    private var clck = true
    private var denied = false
    private var isshowed = false

    override fun onCreate(name: Bundle?){
        super.onCreate(name)
        classbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        pref = getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        denied = getIntent().getBooleanExtra("is_Denied", false);
        if (drctrs_stuff.isclick){
            denied = true
        }
        classbinding.text2.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.alpha))
        classbinding.Text.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.alpha))
        classbinding.btn2?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance))
        classbinding.btn.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance))
    }

    fun clicks_and_all_that(v: View){
        if (!clck){
            clck = true
            classbinding.text2.text = "${resources.getString(R.string.rec)} ${pref?.getLong("count_max", 0)!!}"
        }
        else{
            clck = false
            classbinding.text2.text = "${resources.getString(R.string.shell)} ${count}"
        }
    }

    fun lol (v: View){
        mediaPlayer = MediaPlayer.create(this, R.raw.nosehonk)
        mediaPlayer.start()
        val act = Intent(this, settings::class.java)
        startActivity(act)
    }

    fun lol_boom (v: View){
        mediaPlayer = MediaPlayer.create(this, R.raw.explosion)
        mediaPlayer.start()
        classbinding.text2.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.tremble))
        classbinding.boom.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.tremble))
        classbinding.btn2?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.tremble))
        classbinding.btn.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.tremble))
    }

    @Suppress("DEPRECATION")
    fun click (v: View){
        when (v.id){
            classbinding.btn.id -> classbinding.btn.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.press))
            classbinding.btn2?.id -> classbinding.btn2?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.press))
        }
        val vibro = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibro.vibrate(100)
        classbinding.text2.textSize = 35.5715f;
        classbinding.text2.isEnabled = true;
        clck = false
        classbinding.text2.text = "${resources.getString(R.string.shell)} ${++count}"
        if (pref?.getLong("count_max", 0)!! < count){
            pref?.edit()?.putLong("count_max", count)?.apply()
            if (!isshowed && count > 1){
                mediaPlayer = MediaPlayer.create(this, R.raw.omg)
                mediaPlayer.start()
                Toast.makeText(applicationContext, resources.getString(R.string.newrec), Toast.LENGTH_SHORT).show()
                isshowed = true
                classbinding.text2.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.record))
            }
        }
        when(count){
            1L -> {
                if (!drctrs_stuff.isclick){
                    Toast.makeText(applicationContext, resources.getString(R.string.toast), Toast.LENGTH_SHORT).show()
                }
                drctrs_stuff.isclick = true
                classbinding.Text.isClickable = true
                classbinding.Text.setText(R.string.app_name)
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }
            in 2..49 -> classbinding.Text.setText(R.string.app_name)

            50L -> {
                anim()
                classbinding.Text.setText(R.string.good)
            }
            in 51..99 -> classbinding.Text.setText(R.string.good)

            100L -> {
                anim()
                classbinding.Text.setText(R.string.mgood)
            }
            in 101..149 -> classbinding.Text.setText(R.string.mgood)

            150L -> {
                anim()
                classbinding.Text.setText(R.string.rez)
            }
            in 151..199 -> classbinding.Text.setText(R.string.rez)

            200L -> {
                anim()
                classbinding.Text.setText(R.string.hlop)
            }
            in 201..249 -> classbinding.Text.setText(R.string.hlop)

            250L -> {
                anim()
                classbinding.Text.setText(R.string.ab)
            }
            in 251..299 -> classbinding.Text.setText(R.string.ab)

            in 300..349 -> {
                classbinding.Text.visibility = View.INVISIBLE
                classbinding.boom.setImageResource(R.drawable.boom)
                classbinding.boom.visibility = View.VISIBLE
            }

            350L -> {
                anim()
                toTheLastUkranian()
            }
            in 351..399 -> toTheLastUkranian()

            400L -> {
                anim()
                rocketsForReal()
            }
            in 401..449 -> rocketsForReal()

            450L -> {
                anim()
                nuke666()
            }
            in 451..499 -> nuke666()

            in 500..549 -> {
                classbinding.Text.visibility = View.INVISIBLE
                classbinding.boom.setImageResource(R.drawable.bob)
                classbinding.boom.visibility = View.VISIBLE
            }

            550L -> {
                anim()
                X()
            }
            in 551..599 -> X()

            600L -> {
                anim()
                putinPhone2()
            }

            666L -> {
                anim()
                classbinding.Text.rotationX = 0f
                classbinding.Text.setText(R.string.hell)
                classbinding.Text.textSize = 40f
            }

            777L -> {
                anim()
                classbinding.Text.setText(R.string.m)
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }

            1000L -> {
                anim()
                mediaPlayer = MediaPlayer.create(this, R.raw.kidcheer)
                mediaPlayer.start()
                classbinding.Text.setText(R.string.str)
            }

            1488L -> {
                anim()
                mediaPlayer = MediaPlayer.create(this, R.raw.nosehonk)
                mediaPlayer.start()
                classbinding.Text.setText(R.string.ua)
            }

            10000L -> {
                anim()
                mediaPlayer = MediaPlayer.create(this, R.raw.kidcheer)
                mediaPlayer.start()
                classbinding.Text.rotationX = 0f
                classbinding.Text.textSize = 40f
                classbinding.Text.setText(R.string.ps)
            }
            else -> putinPhone2()
        }
    }

    fun toTheLastUkranian(){
        classbinding.boom.visibility = View.GONE
        classbinding.Text.rotationX = 0f
        classbinding.Text.textSize = 35f
        classbinding.Text.text = "${resources.getString(R.string.l1)}\n    ${resources.getString(R.string.l2)}"
        classbinding.Text.visibility = View.VISIBLE
    }

    fun rocketsForReal(){
        if (isLand()){
            classbinding.Text.text = "${resources.getString(R.string.rr1)} ${resources.getString(R.string.rr2)} ${resources.getString(R.string.rr3)}"
        }
        else{
            classbinding.Text.text = "${resources.getString(R.string.rr1)}\n ${resources.getString(R.string.rr2)}\n ${resources.getString(R.string.rr3)}"
        }
    }

    fun nuke666(){
        classbinding.Text.rotationX = 45f
        classbinding.Text.textSize = 50f
        classbinding.Text.setText(R.string.n)
    }

    fun X(){
        classbinding.boom.visibility = View.GONE
        classbinding.Text.rotationX = 0f
        classbinding.Text.setText(R.string.x)
        classbinding.Text.visibility = View.VISIBLE
    }

    fun putinPhone2(){
        classbinding.Text.setText(R.string.pp2)
        classbinding.Text.rotationX = 45f
        classbinding.Text.textSize = 50f
    }

    fun anim(){
        classbinding.Text.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.change))
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        val act = Intent(this, activity_question::class.java)
        if (!drctrs_stuff.isclick){
            act.putExtra("without_bombing", true)
        }
        else{
            act.putExtra("without_bombing", false)
        }
        startActivity(act)
        finishAffinity()
        @Suppress("DEPRECATION")
        super.onBackPressed()
    }

    fun isLand(): Boolean{
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            return true
        }
        else{
            return false
        }
    }

    override fun onStart(){
        super.onStart()
        classbinding.btn.text = "${resources.getString(R.string.bomb)} ${pref?.getString("city_now", resources.getString(R.string.Kyiv))}"
        classbinding.btn2?.text  = "${resources.getString(R.string.bomb)} ${pref?.getString("city_now", resources.getString(R.string.Kyiv))}"
        if (count > 0 || denied){
            isshowed = false
            classbinding.boom.visibility = View.GONE
            classbinding.Text.visibility = View.VISIBLE
            classbinding.text2.isEnabled = false
            count = 0
            classbinding.Text.textSize = 25f
            classbinding.Text.rotationX = 0f
            classbinding.Text.text = "${resources.getString(R.string.msg)} ${pref?.getString("city_now", resources.getString(R.string.Kyiv))}\n${resources.getString(R.string.msg2)}"
            classbinding.text2.textSize = 25f
            classbinding.text2.text = "${resources.getString(R.string.msg3)}\n${resources.getString(R.string.msg4)}"
        }
    }
}