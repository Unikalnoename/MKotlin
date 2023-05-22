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
        val act = Intent(this, MainActivity::class.java)
        act.putExtra("is_Denied",true)
        startActivity(act)
        finishAffinity()
    }

    fun press (v: View){
        classbinding.textView.text = "Точно?"
        classbinding.btnAcp.visibility = View.VISIBLE
    }

    fun yes(v: View){
        classbinding.butYes.visibility = View.GONE
        classbinding.editText.visibility = View.VISIBLE
    }

    fun accpet(v: View){
        when(classbinding.editText.text.toString()){
            "" -> classbinding.editText.hint = "Так почему?"
            drctrs_stuff.drctr_ua -> classbinding.textView.text = "Состояние: ${drctrs_stuff.drctr_ua_cn}"
            drctrs_stuff.drctr_ru -> classbinding.textView.text = "Состояние: ${drctrs_stuff.drctr_ru_cn}"
            drctrs_stuff.drctr_us -> classbinding.textView.text = "Состояние: ${drctrs_stuff.drctr_us_cn}"
            drctrs_stuff.nuke -> {
                classbinding.editText.visibility = View.GONE
                classbinding.nukeText.visibility = View.VISIBLE
                if (classbinding.nukeText.text.toString() == drctrs_stuff.nuke_code) {
                    classbinding.textView.textSize = 50f
                    classbinding.btnAcp.text = "X)"
                    classbinding.textView.text = "GENOCIDE"
                    classbinding.butNo.visibility = View.GONE
                }
                else {
                    classbinding.textView.textSize = 25f
                    classbinding.textView.text = "Неверный код"
                }
                if(classbinding.nukeText.text.toString() == ""){
                    classbinding.textView.text = "?"
                }
            }
            else -> check()
        }
    }

    fun check(){
        if (drctrs_stuff.listOfShame.contains(classbinding.editText.text.toString())){
            clown()
        }
        else {
            drctrs_stuff.isclick = false
            drctrs_stuff.isOpen = false
            finishAffinity()
        }
    }

    fun clown(){
        classbinding.textView.text = "Да пошёл ты нахуй"
        classbinding.btnAcp.text = "ПИЗДА!"
        classbinding.butNo.text = "Клоун"
    }
}