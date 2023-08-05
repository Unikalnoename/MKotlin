package com.example.mkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.mkotlin.constants.DrctrsStuff
import com.example.mkotlin.constants.anim
import com.example.mkotlin.constants.isLand
import com.example.mkotlin.constants.sound
import com.example.mkotlin.constants.vibration
import com.example.mkotlin.constants.vibro
import com.example.mkotlin.databinding.FragmentExitBinding
import com.google.android.material.tabs.TabLayout
import kotlin.system.exitProcess

class ExitFragment : Fragment() {
    private lateinit var classbinding: FragmentExitBinding
    private var pref: SharedPreferences? = null
    private var click = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        classbinding = FragmentExitBinding.inflate(inflater, container, false)
        classbinding.textView.setOnClickListener {noNo(it)}
        classbinding.btnAcp.setOnClickListener {accept(it)}
        classbinding.btnNo.setOnClickListener {back(it)}
        classbinding.btnYes.setOnClickListener {yes(it)}
        classbinding.editText.setOnClickListener {press(it)}
        classbinding.nukeText.setOnClickListener {pressNuke()}
        pref = this.activity?.getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        anim(classbinding.textView, R.anim.alpha)
        if (isLand(resources))
            fo(R.anim.quast_land_anim)
        else
            fo(R.anim.appearance)
        return classbinding.root
    }

    private fun pressNuke(){
        anim(classbinding.btnAcp, R.anim.change)
    }

    private fun back(v: View){
        vibration(v)
        val tab = requireActivity().findViewById<View>(R.id.tabLayout) as TabLayout
        tab.getTabAt(0)?.select()
        click = false
        classbinding.textView.text = resources.getString(R.string.Question)
        classbinding.btnAcp.text = resources.getString(R.string.YES)
        classbinding.btnNo.text = resources.getString(R.string.NO)
        classbinding.editText.text = null
        classbinding.btnYes.visibility = View.VISIBLE
        classbinding.btnAcp.visibility = View.GONE
        classbinding.nukeText.text = null
        classbinding.nukeText.visibility = View.GONE
    }

    private fun noNo(v: View){
        val anim = AnimationUtils.loadAnimation(this.activity, R.anim.record)
        anim.repeatCount = 3
        anim.duration = 100
        v.startAnimation(anim)
    }

    private fun press (@Suppress("UNUSED_PARAMETER") v: View){
        classbinding.textView.setText(R.string.sure)
        classbinding.btnAcp.visibility = View.VISIBLE
        if (!click)
            anim(classbinding.btnAcp, R.anim.appearance)
        else
            anim(classbinding.btnAcp, R.anim.change)
        click = true
    }

    private fun yes(v: View){
        vibration(v)
        classbinding.btnYes.visibility = View.GONE
        classbinding.editText.visibility = View.VISIBLE
    }

    private fun fo(ani: Int){
        var so:Long = 0
        for (id in listOf(classbinding.btnNo, classbinding.btnYes)){
            val anim = AnimationUtils.loadAnimation(this.activity, ani)
            anim.startOffset = so
            id.startAnimation(anim)
            so += 100
        }
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
                context?.let {vibro(it, 1500)}
                classbinding.textView.text = "${resources.getString(R.string.cond)}${DrctrsStuff.drctr_nk_cn}"
            }

            DrctrsStuff.nuke -> {
                classbinding.editText.visibility = View.GONE
                classbinding.nukeText.visibility = View.VISIBLE
                if (classbinding.nukeText.text.toString() == DrctrsStuff.nuke_code) {
                    classbinding.textView.textSize = 50f
                    DrctrsStuff.is_nuke = true
                    classbinding.btnAcp.setText(R.string.x)
                    classbinding.textView.setText(R.string.gena)
                    classbinding.btnNo.visibility = View.GONE
                }
                else{
                    classbinding.textView.textSize = 25f
                    classbinding.textView.setText(R.string.ercode)
                }
                if(classbinding.nukeText.text.toString() == ""){
                    classbinding.textView.setText(R.string.q)
                    classbinding.btnAcp.setText(R.string.q)
                    classbinding.btnNo.setText(R.string.q)
                }
            }
            else -> check()
        }
    }

    private fun check(){
        var indexUa = 0
        var indexGlory = 0
        var haram1 = false
        var haram2 = false
        var norm = false

        val sizeUa = DrctrsStuff.listOfUa.size -1
        val sizeGlory = DrctrsStuff.listOfGlory.size -1
        val sizeNorm = DrctrsStuff.listNorm.size -1

        for (i in 0..sizeGlory){
            if (classbinding.editText.text.contains(DrctrsStuff.listOfGlory[i], ignoreCase = true)){
                haram1 = true
                indexGlory = classbinding.editText.text.indexOf(DrctrsStuff.listOfGlory[i], ignoreCase = true)
            }
        }

        for (i in 0..sizeUa){
            if (classbinding.editText.text.contains(DrctrsStuff.listOfUa[i], ignoreCase = true)){
                haram2 = true
                indexUa = classbinding.editText.text.indexOf(DrctrsStuff.listOfUa[i], ignoreCase = true)
            }
        }

        for (i in 0..sizeNorm){
            if (classbinding.editText.text.substring(indexGlory..indexUa).contains(DrctrsStuff.listNorm[i], ignoreCase = true)){
                norm = true
            }
        }

        if (!norm){
            if (haram1 && haram2){
                sound(R.raw.hellno)
                classbinding.btnAcp.textSize = 50f
                classbinding.btnNo.textSize = 50f
                classbinding.textView.setText(R.string.faq)
                classbinding.btnAcp.setText(R.string.piz)
                classbinding.btnNo.setText(R.string.clo)
                classbinding.btnNo.isClickable = false
                DrctrsStuff.is_nuke = false
                pref?.edit()?.clear()?.apply()
            }
            else{
                exitProcess(0)
            }
        }
        else{
            exitProcess(0)
        }
    }

    private fun sound(res: Int){
        sound(res, this.activity)
    }

    private fun anim(v: View, res: Int){
        anim(v, res, this.activity)
    }
}