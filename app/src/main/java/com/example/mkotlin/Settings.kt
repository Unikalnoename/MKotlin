package com.example.mkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.mkotlin.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {
    private lateinit var classbinding: ActivitySettingsBinding
    private var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classbinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        pref = getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        anim(classbinding.textView2, R.anim.alpha)
        val buttons: List<View> = when(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            true -> listOf(classbinding.buttonS, classbinding.buttonR, classbinding.buttonLU,
                classbinding.buttonK, classbinding.buttonD, classbinding.buttonL)
            false -> listOf(classbinding.buttonK, classbinding.buttonD, classbinding.buttonL,
                classbinding.buttonS, classbinding.buttonR, classbinding.buttonLU)
        }
        var so:Long = 50
        for (id in buttons){
            val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.appearance)
            anim.startOffset = so
            id.startAnimation(anim)
            so += 50
        }
    }

    fun toMain(v: View){
        anim(v, R.anim.press)
        newAct()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        newAct()
        @Suppress("DEPRECATION")
        super.onBackPressed()
    }

    private fun newAct(){
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    fun toKiev(v: View){
        toast(v, resources.getString(R.string.Kyiv), R.anim.press)
    }

    fun toLviv(v: View){
        toast(v, resources.getString(R.string.Lviv), R.anim.press)
    }

    fun toSumy(v: View){
        toast(v, resources.getString(R.string.Sumy), R.anim.press)
    }

    fun toRovno(v: View){
        toast(v, resources.getString(R.string.Rovno), R.anim.press)
    }

    fun toLutsk(v: View){
        toast(v, resources.getString(R.string.Lutsk), R.anim.press)
    }

    fun toDnipro(v: View){
        toast(v, resources.getString(R.string.Dnipro), R.anim.press)
    }

    private fun toast(x: View, city: String, res: Int){
        pref?.edit()?.putString("city_now", city)?.apply()
        x.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
        Toast.makeText(applicationContext, resources.getString(R.string.apl), Toast.LENGTH_SHORT).show()
        anim(x, res)
    }

    private fun anim(x: View, res: Int){
        x.startAnimation(AnimationUtils.loadAnimation(applicationContext, res))
    }
}