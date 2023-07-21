package com.example.mkotlin

import android.annotation.SuppressLint
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
import com.example.mkotlin.constants.DrctrsStuff
import com.example.mkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var classbinding:ActivityMainBinding
    private var pref:SharedPreferences? = null
    private var count:Long = 0
    private var click = true
    private var denied = false
    private var showed = false

    override fun onCreate(name: Bundle?){
        super.onCreate(name)
        classbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        pref = getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        denied = intent.getBooleanExtra("is_Denied", false)
        if (DrctrsStuff.isclick)
            denied = true
        if (isLand()){
            classbinding.btn2?.let{anim(it, R.anim.btn_right_anim)}
            anim(classbinding.btn, R.anim.btn_left_anim)
        }
        else
            anim(classbinding.btn, R.anim.appearance)
        for (id in listOf(classbinding.text2, classbinding.Text))
            anim(id, R.anim.alpha)
    }

    @SuppressLint("SetTextI18n")
    fun clicksAndAllThat(v: View){
        anim(v, R.anim.press)
        if (!click){
            click = true
            classbinding.text2.text = "${resources.getString(R.string.rec)} ${pref?.getLong("count_max", 0)!!}"
        }
        else{
            click = false
            classbinding.text2.text = "${resources.getString(R.string.shell)} $count"
        }
    }

    fun lol (v: View){
        anim(v, R.anim.press)
        sound(R.raw.nosehonk)
        startActivity(Intent(this, Settings::class.java))
        finishAffinity()
    }

    fun lolBoom (@Suppress("UNUSED_PARAMETER") v: View){
        sound(R.raw.explosion)
        for (id in listOf(classbinding.text2, classbinding.boom, classbinding.btn2, classbinding.btn))
            id?.let{anim(it, R.anim.tremble)}
    }

    @SuppressLint("SetTextI18n")
    @Suppress("DEPRECATION")
    fun click (v: View){
        DrctrsStuff.city_was = pref?.getString("city_now", resources.getString(R.string.Kyiv)).toString()
        anim(v, R.anim.press)
        val vibration = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibration.vibrate(100)
        classbinding.text2.textSize = 35.5715f
        classbinding.text2.isEnabled = true
        click = false
        classbinding.text2.text = "${resources.getString(R.string.shell)} ${++count}"
        if (pref?.getLong("count_max", 0)!! < count){
            pref?.edit()?.putLong("count_max", count)?.apply()
            if (!showed && count > 1){
                sound(R.raw.omg)
                Toast.makeText(applicationContext, resources.getString(R.string.newrec), Toast.LENGTH_SHORT).show()
                showed = true
                anim(classbinding.text2, R.anim.record)
            }
        }
        when(count){
            1L -> {
                if (!DrctrsStuff.isclick)
                    Toast.makeText(applicationContext, resources.getString(R.string.toast), Toast.LENGTH_SHORT).show()
                DrctrsStuff.isclick = true
                classbinding.Text.isClickable = true
                classbinding.Text.setText(R.string.app_name)
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }
            in 2..49 -> classbinding.Text.setText(R.string.app_name)

            50L -> {
                anim(classbinding.Text, R.anim.change)
                classbinding.Text.setText(R.string.good)
            }
            in 51..99 -> classbinding.Text.setText(R.string.good)

            100L -> {
                anim(classbinding.Text, R.anim.change)
                classbinding.Text.setText(R.string.mgood)
            }
            in 101..149 -> classbinding.Text.setText(R.string.mgood)

            150L -> {
                anim(classbinding.Text, R.anim.change)
                classbinding.Text.setText(R.string.rez)
            }
            in 151..199 -> classbinding.Text.setText(R.string.rez)

            200L -> {
                anim(classbinding.Text, R.anim.change)
                classbinding.Text.setText(R.string.hlop)
            }
            in 201..249 -> classbinding.Text.setText(R.string.hlop)

            250L -> {
                anim(classbinding.Text, R.anim.change)
                classbinding.Text.setText(R.string.ab)
            }
            in 251..299 -> classbinding.Text.setText(R.string.ab)

            in 300..349 -> image(R.drawable.boom)

            350L -> {
                anim(classbinding.Text, R.anim.change)
                classbinding.boom.visibility = View.GONE
                classbinding.Text.rotationX = 0f
                classbinding.Text.textSize = 35f
                classbinding.Text.visibility = View.VISIBLE
                classbinding.Text.text = "${resources.getString(R.string.l1)}\n    ${resources.getString(R.string.l2)}"
            }
            in 351..399 -> classbinding.Text.text = "${resources.getString(R.string.l1)}\n    ${resources.getString(R.string.l2)}"

            400L -> {
                anim(classbinding.Text, R.anim.change)
                rocketsForReal()
            }
            in 401..449 -> rocketsForReal()

            450L -> {
                anim(classbinding.Text, R.anim.change)
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
                classbinding.Text.setText(R.string.n)
            }
            in 451..499 -> classbinding.Text.setText(R.string.n)

            in 500..549 -> image(R.drawable.bob)

            550L -> {
                anim(classbinding.Text, R.anim.change)
                classbinding.boom.visibility = View.GONE
                classbinding.Text.rotationX = 0f
                classbinding.Text.setText(R.string.x)
                classbinding.Text.visibility = View.VISIBLE
            }
            in 551..599 -> classbinding.Text.setText(R.string.x)

            600L -> {
                classbinding.videoView.visibility = View.VISIBLE
                classbinding.Text.visibility = View.GONE
                video(R.raw.zflix)
            }

            650L -> {
                video(R.raw.pd)
            }

            666L -> {
                classbinding.videoView.visibility = View.GONE
                classbinding.Text.visibility = View.VISIBLE
                anim(classbinding.Text, R.anim.change)
                classbinding.Text.rotationX = 0f
                classbinding.Text.setText(R.string.hell)
                classbinding.Text.textSize = 40f
            }

            667L -> {
                classbinding.Text.visibility = View.GONE
                classbinding.videoView.visibility = View.VISIBLE
                video(R.raw.pd)
            }

            700L -> {
                classbinding.Text.visibility = View.VISIBLE
                classbinding.videoView.visibility = View.GONE
                anim(classbinding.Text, R.anim.change)
                putinPhone2()
            }

            777L -> {
                anim(classbinding.Text, R.anim.change)
                classbinding.Text.setText(R.string.m)
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }

            1000L -> {
                anim(classbinding.Text, R.anim.change)
                sound(R.raw.kidcheer)
                classbinding.Text.setText(R.string.str)
            }

            1488L -> {
                anim(classbinding.Text, R.anim.change)
                sound(R.raw.nosehonk)
                classbinding.Text.setText(R.string.ua)
            }

            10000L -> {
                anim(classbinding.Text, R.anim.change)
                sound(R.raw.kidcheer)
                classbinding.Text.rotationX = 0f
                classbinding.Text.textSize = 40f
                classbinding.Text.setText(R.string.ps)
            }

            100000L -> {
                anim(classbinding.Text, R.anim.change)
                sound(R.raw.gigatheme)
                classbinding.Text.setText(R.string.nocomm)
            }
            else -> putinPhone2()
        }
    }

    private fun video(res: Int){
        classbinding.videoView.setVideoPath("android.resource://$packageName/$res")
        classbinding.videoView.start()
        classbinding.videoView.setOnPreparedListener {mp -> mp!!.isLooping = true}
    }

    private fun image(res: Int){
        classbinding.Text.visibility = View.INVISIBLE
        classbinding.boom.setImageResource(res)
        classbinding.boom.visibility = View.VISIBLE
    }

    fun anim(x: View, res: Int){
        x.startAnimation(AnimationUtils.loadAnimation(applicationContext, res))
    }

    private fun sound(res: Int){
        MediaPlayer.create(this, res).start()
    }

    @SuppressLint("SetTextI18n")
    private fun rocketsForReal(){
        if (isLand())
            classbinding.Text.text = "${resources.getString(R.string.rr1)} ${resources.getString(R.string.rr2)} ${resources.getString(R.string.rr3)}"
        else
            classbinding.Text.text = "${resources.getString(R.string.rr1)}\n ${resources.getString(R.string.rr2)}\n ${resources.getString(R.string.rr3)}"
    }

    private fun putinPhone2(){
        classbinding.Text.setText(R.string.pp2)
        classbinding.Text.rotationX = 45f
        classbinding.Text.textSize = 50f
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        val act = Intent(this, Question::class.java)
        if (!DrctrsStuff.isclick)
            act.putExtra("without_bombing", true)
        else
            act.putExtra("without_bombing", false)
        startActivity(act)
        finishAffinity()
        @Suppress("DEPRECATION")
        super.onBackPressed()
    }

    private fun isLand(): Boolean{
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    @SuppressLint("SetTextI18n")
    override fun onStart(){
        super.onStart()
        for (id in listOf(classbinding.btn, classbinding.btn2))
            id?.text = "${resources.getString(R.string.bomb)} ${pref?.getString("city_now", resources.getString(R.string.Kyiv))}"
        if (count > 0 || denied){
            showed = false
            classbinding.boom.visibility = View.GONE
            classbinding.Text.visibility = View.VISIBLE
            classbinding.text2.isEnabled = false
            count = 0
            classbinding.Text.textSize = 25f
            classbinding.Text.rotationX = 0f
            classbinding.Text.text = "${resources.getString(R.string.msg)} ${DrctrsStuff.city_was}\n${resources.getString(R.string.msg2)}"
            classbinding.text2.textSize = 25f
            classbinding.text2.text = "${resources.getString(R.string.msg3)}\n${resources.getString(R.string.msg4)}"
        }
    }
}