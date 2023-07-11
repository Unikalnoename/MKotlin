package com.example.mkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationUtils
import com.example.mkotlin.constants.DrctrsStuff
import com.example.mkotlin.databinding.ActivityQuestionBinding

class Question : AppCompatActivity(){
    private lateinit var classbinding:ActivityQuestionBinding
    private var pref: SharedPreferences? = null
    private var click = false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        classbinding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        pref = getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        classbinding.textView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.alpha))
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            fo(R.anim.quast_land_anim)
        else
            fo(R.anim.appearance)
    }

    fun noNo(v: View){
        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.record)
        anim.repeatCount = 3
        anim.duration = 100
        v.startAnimation(anim)
    }

    private fun fo(ani: Int){
        var so:Long = 0
        for (id in listOf(classbinding.btnNo, classbinding.btnYes)){
            val anim = AnimationUtils.loadAnimation(applicationContext, ani)
            anim.startOffset = so
            id.startAnimation(anim)
            so += 100
        }
    }

    fun back (v: View){
        anim(v, R.anim.press)
        vibration(v)
        val act = Intent(this, MainActivity::class.java)
        if (intent.getBooleanExtra("without_bombing", true))
            act.putExtra("is_Denied",false)
        else
            act.putExtra("is_Denied", true)
        startActivity(act)
        finishAffinity()
    }

    fun pressNuke (@Suppress("UNUSED_PARAMETER") v: View){
        anim(classbinding.btnAcp, R.anim.change)
    }

    fun press (@Suppress("UNUSED_PARAMETER") v: View){
        classbinding.textView.setText(R.string.sure)
        classbinding.btnAcp.visibility = View.VISIBLE
        if (!click)
            anim(classbinding.btnAcp, R.anim.appearance)
        else
            anim(classbinding.btnAcp, R.anim.change)
        click = true
    }

    fun yes(v: View){
        vibration(v)
        classbinding.btnYes.visibility = View.GONE
        classbinding.editText.visibility = View.VISIBLE
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        startActivity(Intent(this, Question::class.java))
        finishAffinity()
        @Suppress("DEPRECATION")
        super.onBackPressed()
    }

    @SuppressLint("SetTextI18n")
    fun accept(v: View){
        anim(v, R.anim.press)
        vibration(v)
        when(classbinding.editText.text.toString()){
            "" -> classbinding.editText.hint = resources.getString(R.string.why)
            DrctrsStuff.drctr_ua -> {
                sound(R.raw.kidcheer)
                classbinding.textView.text = "${resources.getString(R.string.cond)}${DrctrsStuff.drctr_ua_cn}"
            }

            DrctrsStuff.drctr_ru -> {
                sound(R.raw.gigatheme)
                classbinding.textView.text = "${resources.getString(R.string.cond)}${DrctrsStuff.drctr_ru_cn}"
            }

            DrctrsStuff.drctr_us -> {
                sound(R.raw.hellno)
                classbinding.textView.text = "${resources.getString(R.string.cond)}${DrctrsStuff.drctr_us_cn}"
            }

            DrctrsStuff.drctr_fr -> {
                sound(R.raw.ringring)
                classbinding.textView.text = "${resources.getString(R.string.cond)}${DrctrsStuff.drctr_fr_cn}"
            }

            DrctrsStuff.drctr_nk -> {
                sound(R.raw.explosion)
                classbinding.textView.text = "${resources.getString(R.string.cond)}${DrctrsStuff.drctr_nk_cn}"
            }

            DrctrsStuff.nuke -> {
                classbinding.editText.visibility = View.GONE
                classbinding.nukeText.visibility = View.VISIBLE
                if (classbinding.nukeText.text.toString() == DrctrsStuff.nuke_code) {
                    classbinding.textView.textSize = 50f
                    classbinding.btnAcp.setText(R.string.x)
                    classbinding.textView.setText(R.string.gena)
                    classbinding.btnNo.visibility = View.GONE
                }
                else{
                    classbinding.textView.textSize = 25f
                    classbinding.textView.setText(R.string.ercode)
                }
                if(classbinding.nukeText.text.toString() == "")
                    classbinding.textView.setText(R.string.q)
            }
            else -> check()
        }
    }

    private fun check(){
        if (DrctrsStuff.listOfShame.contains(classbinding.editText.text.toString())){
            sound(R.raw.hellno)
            classbinding.btnAcp.textSize = 50f
            classbinding.btnNo.textSize = 50f
            classbinding.textView.setText(R.string.faq)
            classbinding.btnAcp.setText(R.string.piz)
            classbinding.btnNo.setText(R.string.clo)
            pref?.edit()?.clear()?.apply()
        }
        else{
            DrctrsStuff.isclick = false
            finishAffinity()
        }
    }

    private fun vibration(x: View){
        x.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
    }

    private fun anim(x: View, res: Int){
        x.startAnimation(AnimationUtils.loadAnimation(applicationContext, res))
    }

    private fun sound(res: Int){
        MediaPlayer.create(this, res).start()
    }
}