@file:Suppress("UNUSED_PARAMETER")

package com.example.mkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mkotlin.databinding.ActivityQuestionBinding

class activity_question : AppCompatActivity(){
    private lateinit var classbinding:ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        classbinding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
    }

    fun back (v: View){
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

    fun check(){
        if (drctrs_stuff.listOfShame.contains(classbinding.editText.text.toString())){
            classbinding.textView.setText(R.string.faq)
            classbinding.btnAcp.setText(R.string.piz)
            classbinding.butNo.setText(R.string.clo)
        }
        else{
            drctrs_stuff.isclick = false
            finishAffinity()
        }
    }
}