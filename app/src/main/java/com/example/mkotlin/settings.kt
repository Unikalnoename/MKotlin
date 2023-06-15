@file:Suppress("UNUSED_PARAMETER")

package com.example.mkotlin

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mkotlin.databinding.ActivitySettingsBinding

class settings : AppCompatActivity() {
    private lateinit var classbinding: ActivitySettingsBinding
    var pref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classbinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(classbinding.root)
        pref = getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
    }

    fun toKiev(v: View){
        pref?.edit()?.putString("city_now", resources.getString(R.string.Kyiv))?.apply()
        toast()
    }

    fun toLviv(v: View){
        pref?.edit()?.putString("city_now", resources.getString(R.string.Lviv))?.apply()
        toast()
    }

    fun toSumy(v: View){
        pref?.edit()?.putString("city_now", resources.getString(R.string.Sumy))?.apply()
        toast()
    }

    fun toRovno(v: View){
        pref?.edit()?.putString("city_now", resources.getString(R.string.Rovno))?.apply()
        toast()
    }

    fun toLutsk(v: View){
        pref?.edit()?.putString("city_now", resources.getString(R.string.Lutsk))?.apply()
        toast()
    }

    fun toDnipro(v: View){
        pref?.edit()?.putString("city_now", resources.getString(R.string.Dnipro))?.apply()
        toast()
    }

    fun toast(){
        Toast.makeText(applicationContext, resources.getString(R.string.apl), Toast.LENGTH_SHORT).show()
    }
}