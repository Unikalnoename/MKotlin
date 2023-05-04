package com.example.mkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var classbinding:ActivityMainBinding
    private var count:Long = 0
    private var clck:Boolean = true

    override fun onCreate(name: Bundle?){
        super.onCreate(name)
        classbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
    }

    fun clicks_and_all_that(v: View){
        if (!clck){
            clck = true
            classbinding.text2.text = "Z.O.V"
        }
        else{
            clck = false
            classbinding.text2.text = "Обстрелов: $count"
        }
    }

    fun lol (v: View){
        val act = Intent(this, activity_question::class.java)
        finishAffinity()
        startActivity(act)
    }

    fun click (v: View){
        classbinding.text2.textSize = 35.5715f;
        classbinding.text2.isEnabled = true;
        clck = false
        classbinding.text2.text = "Обстрелов: ${++count}"
        when(count){
            in 30..60 -> classbinding.Text.text = "Харош"
            in 60..90 -> classbinding.Text.text = "Мегахарош"
            in 90..120 -> classbinding.Text.text = "Angry birds)"
            in 120..150 -> classbinding.Text.text = "Резня"
            in 150..180 -> classbinding.Text.text = "Angry birds2"
            in 180..210 -> {
                classbinding.Text.visibility = View.INVISIBLE
                classbinding.boom.visibility = View.VISIBLE
            }
            in 210..240 -> {
                classbinding.boom.visibility = View.GONE
                classbinding.Text.text = "nuke: 666"
                classbinding.Text.visibility = View.VISIBLE
            }
            in 240..270 -> {
                classbinding.Text.text = "X)"
                classbinding.Text.rotationX = 0f
            }
            else -> {
                classbinding.Text.text = "PutinPhone"
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }
        }
    }

    override fun onStart(){
        super.onStart()
        var is_Denied = getIntent().getBooleanExtra("is_Denied", false);
        if (count > 0 || is_Denied == true){
            classbinding.boom.visibility = View.GONE
            classbinding.Text.visibility = View.VISIBLE
            classbinding.text2.isEnabled = false
            count = 0
            classbinding.Text.textSize = 27f
            classbinding.Text.rotationX = 0f
            classbinding.Text.text = "Пока тебя не было, Киев\n      уже востановили!"
            classbinding.text2.textSize = 25f;
            classbinding.text2.text = "Предётся бомбить,\n           заново("
        }
    }
}