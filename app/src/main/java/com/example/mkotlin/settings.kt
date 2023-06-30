@file:Suppress("UNUSED_PARAMETER")

package com.example.mkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.mkotlin.databinding.ActivitySettingsBinding

class settings : AppCompatActivity() {
    private lateinit var classbinding: ActivitySettingsBinding
    private var pref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classbinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        pref = getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        classbinding.textView2.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.alpha))
        classbinding.buttonD.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance_scale))
        classbinding.buttonK.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance_scale))
        classbinding.buttonL.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance_scale))
        classbinding.buttonS.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance_scale))
        classbinding.buttonR.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance_scale))
        classbinding.buttonLU.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance_scale))
    }

    fun toMain(v: View){
        newAct()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        @Suppress("DEPRECATION")
        super.onBackPressed()
        newAct()
    }

    fun newAct(){
        val act = Intent(this, MainActivity::class.java)
        startActivity(act)
    }

    fun toKiev(v: View){
        classbinding.buttonK.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.press))
        pref?.edit()?.putString("city_now", resources.getString(R.string.Kyiv))?.apply()
        toast(v)
    }

    fun toLviv(v: View){
        classbinding.buttonL.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.press))
        pref?.edit()?.putString("city_now", resources.getString(R.string.Lviv))?.apply()
        toast(v)
    }

    fun toSumy(v: View){
        classbinding.buttonS.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.press))
        pref?.edit()?.putString("city_now", resources.getString(R.string.Sumy))?.apply()
        toast(v)
    }

    fun toRovno(v: View){
        classbinding.buttonR.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.press))
        pref?.edit()?.putString("city_now", resources.getString(R.string.Rovno))?.apply()
        toast(v)
    }

    fun toLutsk(v: View){
        classbinding.buttonLU.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.press))
        pref?.edit()?.putString("city_now", resources.getString(R.string.Lutsk))?.apply()
        toast(v)
    }

    fun toDnipro(v: View){
        classbinding.buttonD.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.press))
        pref?.edit()?.putString("city_now", resources.getString(R.string.Dnipro))?.apply()
        toast(v)
    }

    fun toast(x: View){
        x.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
        Toast.makeText(applicationContext, resources.getString(R.string.apl), Toast.LENGTH_SHORT).show()
    }
}