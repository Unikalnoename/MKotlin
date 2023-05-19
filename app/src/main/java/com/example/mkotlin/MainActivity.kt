package com.example.mkotlin

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var classbinding:ActivityMainBinding
    private var count:Long = 0
    private var clck = true

    override fun onCreate(name: Bundle?){
        super.onCreate(name)
        classbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        if (!drctrs_stuff.isclick) {
            classbinding.Text.isClickable = false
        }
        var is_Denied = getIntent().getBooleanExtra("is_Denied", false);
        if (!is_Denied) {
            toast("Плюхи-приколюхи каждые 50 обстрелов")
        }
    }

    fun clicks_and_all_that(v: View){
        if (!clck) {
            clck = true
            classbinding.text2.text = "Z.O.V"
        }
        else {
            clck = false
            classbinding.text2.text = "Обстрелов: $count"
        }
    }

    fun lol (v: View){
        val act = Intent(this, activity_question::class.java)
        startActivity(act)
        finishAffinity()
    }

    fun click (v: View){
        classbinding.text2.textSize = 35.5715f;
        classbinding.text2.isEnabled = true;
        clck = false
        drctrs_stuff.isclick = true
        classbinding.text2.text = "Обстрелов: ${++count}"
        when(count){
            in 1..49 -> {
                classbinding.Text.isClickable = true
                classbinding.Text.text = "PutinPhone"
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }
            in 50..99 -> classbinding.Text.text = "ХОРОШ"
            in 100..149 -> classbinding.Text.text = "МЕГАХОРОШ"
            in 150..199 -> classbinding.Text.text = "Резня"
            in 200..249 -> classbinding.Text.text = "Хлопки)"
            in 250..299 -> classbinding.Text.text = "Angry birds)"
            in 300..349 -> {
                classbinding.Text.visibility = View.INVISIBLE
                classbinding.boom.setImageResource(R.drawable.boom)
                classbinding.boom.visibility = View.VISIBLE
            }
            in 350..399 -> {
                classbinding.boom.visibility = View.GONE
                classbinding.Text.rotationX = 0f
                classbinding.Text.textSize = 35f
                classbinding.Text.text = "До последнего\n     украинца"
                classbinding.Text.visibility = View.VISIBLE
            }
            in 400..449 -> {
                if (isLand()){
                    classbinding.Text.text = ("Скоро ракеты, полетят по настоящему)")
                }
                else {
                    classbinding.Text.text = "Скоро ракеты,\n полетят по\n настоящему)"
                }
            }
            in 450..499 -> {
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
                classbinding.Text.text = "nuke: 666"
            }
            in 500..549 -> {
                classbinding.Text.visibility = View.INVISIBLE
                classbinding.boom.setImageResource(R.drawable.bob)
                classbinding.boom.visibility = View.VISIBLE
            }
            in 550..599 -> {
                classbinding.boom.visibility = View.GONE
                classbinding.Text.rotationX = 0f
                classbinding.Text.text = "X)"
                classbinding.Text.visibility = View.VISIBLE
            }
            666L -> {
                classbinding.Text.rotationX = 0f
                classbinding.Text.text = "АДская поджарка"
                classbinding.Text.textSize = 40f
            }
            777L -> {
                classbinding.Text.text = "M777?"
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }
            1000L -> classbinding.Text.text = "Страйк!"
            1488L -> classbinding.Text.text = "UA moment"
            10000L -> {
                classbinding.Text.rotationX = 0f
                classbinding.Text.textSize = 40f
                classbinding.Text.text = "Свинострайк!!!"
            }
            else -> {
                classbinding.Text.text = "PutinPhone2"
                classbinding.Text.rotationX = 45f
                classbinding.Text.textSize = 50f
            }
        }
    }

    fun isLand(): Boolean {
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true
        }
        else {
            return false
        }
    }

    fun Context.toast(message: CharSequence) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart(){
        super.onStart()
        var is_Denied = getIntent().getBooleanExtra("is_Denied", false);
        if (count > 0 || is_Denied){
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