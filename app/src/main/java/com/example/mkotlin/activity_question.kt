@file:Suppress("UNUSED_PARAMETER")

package com.example.mkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationUtils
import com.example.mkotlin.databinding.ActivityQuestionBinding

class activity_question : AppCompatActivity(){
    private lateinit var classbinding:ActivityQuestionBinding
    private lateinit var mediaPlayer: MediaPlayer
    private var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        classbinding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        pref = getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        classbinding.butYes.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance))
        classbinding.butNo.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance))
        classbinding.textView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.alpha))
    }

    fun back (v: View){
        classbinding.butNo.startAnimation(AnimationUtils.loadAnimation(applicationContext ,R.anim.press))
        vibro(v)
        var without_bombing = getIntent().getBooleanExtra("without_bombing", true);
        val act = Intent(this, MainActivity::class.java)
        if (without_bombing){
            act.putExtra("is_Denied",false)
        }
        else{
            act.putExtra("is_Denied", true)
        }
        startActivity(act)
        finishAffinity()
    }

    fun press (v: View){
        classbinding.textView.setText(R.string.sure)
        classbinding.btnAcp.visibility = View.VISIBLE
    }

    fun yes(v: View){
        vibro(v)
        classbinding.butYes.visibility = View.GONE
        classbinding.editText.visibility = View.VISIBLE
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        val act = Intent(this, activity_question::class.java)
        startActivity(act)
        finishAffinity()
        @Suppress("DEPRECATION")
        super.onBackPressed()
    }

    fun accpet(v: View){
        classbinding.btnAcp.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.press))
        vibro(v)
        when(classbinding.editText.text.toString()){
            "" -> classbinding.editText.hint = "${resources.getString(R.string.why)}"
            drctrs_stuff.drctr_ua -> classbinding.textView.text = "${resources.getString(R.string.cond)}${drctrs_stuff.drctr_ua_cn}"
            drctrs_stuff.drctr_ru -> classbinding.textView.text = "${resources.getString(R.string.cond)}${drctrs_stuff.drctr_ru_cn}"
            drctrs_stuff.drctr_us -> classbinding.textView.text = "${resources.getString(R.string.cond)}${drctrs_stuff.drctr_us_cn}"
            drctrs_stuff.nuke -> {
                classbinding.editText.visibility = View.GONE
                classbinding.nukeText.visibility = View.VISIBLE
                if (classbinding.nukeText.text.toString() == drctrs_stuff.nuke_code) {
                    classbinding.textView.textSize = 50f
                    classbinding.btnAcp.setText(R.string.x)
                    classbinding.textView.setText(R.string.gena)
                    classbinding.butNo.visibility = View.GONE
                }
                else{
                    classbinding.textView.textSize = 25f
                    classbinding.textView.setText(R.string.ercode)
                }
                if(classbinding.nukeText.text.toString() == ""){
                    classbinding.textView.setText(R.string.q)
                }
            }
            else -> check()
        }
    }

    fun vibro(x: View){
        x.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
    }

    fun check(){
        if (drctrs_stuff.listOfShame.contains(classbinding.editText.text.toString())){
            mediaPlayer = MediaPlayer.create(this, R.raw.hell_no)
            mediaPlayer.start()
            classbinding.btnAcp.textSize = 50f
            classbinding.butNo.textSize = 50f
            classbinding.textView.setText(R.string.faq)
            classbinding.btnAcp.setText(R.string.piz)
            classbinding.butNo.setText(R.string.clo)
            pref?.edit()?.clear()?.apply()
        }
        else{
            drctrs_stuff.isclick = false
            finishAffinity()
        }
    }
}