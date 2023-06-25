@file:Suppress("UNUSED_PARAMETER")

package com.example.mkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
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

    fun click (v: View){
        classbinding.btn.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
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
            in 50..99 -> classbinding.Text.setText(R.string.good)
            in 100..149 -> classbinding.Text.setText(R.string.mgood)
            in 150..199 -> classbinding.Text.setText(R.string.rez)
            in 200..249 -> classbinding.Text.setText(R.string.hlop)
            in 250..299 -> classbinding.Text.setText(R.string.ab)
            in 300..349 -> {
                classbinding.Text.visibility = View.INVISIBLE
                classbinding.boom.setImageResource(R.drawable.boom)
                classbinding.boom.visibility = View.VISIBLE
            }
            in 350..399 -> {
                classbinding.boom.visibility = View.GONE
                classbinding.Text.rotationX = 0f
                classbinding.Text.textSize = 35f
                classbinding.Text.text = "${resources.getString(R.string.l1)}\n    ${resources.getString(R.string.l2)}"
                classbinding.Text.visibility = View.VISIBLE
            }
            in 400..449 -> {
                if (isLand()){
                    classbinding.Text.text = "${resources.getString(R.string.rr1)} ${resources.getString(R.string.rr2)} ${resources.getString(R.string.rr3)}"
                }
                else{
                    classbinding.Text.text = "${resources.getString(R.string.rr1)}\n ${resources.getString(R.string.rr2)}\n ${resources.getString(R.string.rr3)}"
                }
            }
            in 450..499 -> {
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
                classbinding.Text.setText(R.string.n)
            }
            in 500..549 -> {
                classbinding.Text.visibility = View.INVISIBLE
                classbinding.boom.setImageResource(R.drawable.bob)
                classbinding.boom.visibility = View.VISIBLE
            }
            in 550..599 -> {
                classbinding.boom.visibility = View.GONE
                classbinding.Text.rotationX = 0f
                classbinding.Text.setText(R.string.x)
                classbinding.Text.visibility = View.VISIBLE
            }
            666L -> {
                classbinding.Text.rotationX = 0f
                classbinding.Text.setText(R.string.hell)
                classbinding.Text.textSize = 40f
            }
            777L -> {
                classbinding.Text.setText(R.string.m)
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }
            1000L -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.kidcheer)
                mediaPlayer.start()
                classbinding.Text.setText(R.string.str)
            }
            1488L -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.nosehonk)
                mediaPlayer.start()
                classbinding.Text.setText(R.string.ua)
            }
            10000L -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.kidcheer)
                mediaPlayer.start()
                classbinding.Text.rotationX = 0f
                classbinding.Text.textSize = 40f
                classbinding.Text.setText(R.string.ps)
            }
            else -> {
                classbinding.Text.setText(R.string.pp2)
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }
        }
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