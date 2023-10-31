package com.example.mkotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.example.mkotlin.constants.anim
import com.example.mkotlin.constants.isLand
import com.example.mkotlin.constants.toast
import com.example.mkotlin.constants.vibration
import com.example.mkotlin.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomFragment : BottomSheetDialogFragment() {
    private var pref: SharedPreferences? = null
    private lateinit var classbinding: BottomSheetLayoutBinding

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        classbinding = BottomSheetLayoutBinding.bind(inflater.inflate(R.layout.bottom_sheet_layout, container))
        pref = this.context?.getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        classbinding.switchSound.isChecked = pref!!.getBoolean("switch_sound", true)
        classbinding.switchVibro.isChecked = pref!!.getBoolean("switch_vibro", true)
        classbinding.switchAnim.isChecked = pref!!.getBoolean("switch_anim", true)
        classbinding.switchNotification.isChecked = pref!!.getBoolean("switch_notification", true)
        classbinding.imageView.setOnClickListener {close()}
        classbinding.textView.setOnClickListener {close()}
        classbinding.buttonSupport.setOnClickListener {
            toast(context, resources.getString(R.string.minus_money))
            vibration(context, 666)
            anim(it, R.anim.press)
        }
        anim(classbinding.buttonSupport, R.anim.quast_land_anim)
        anim(classbinding.textView, R.anim.alpha)
        anim(classbinding.imageView, R.anim.btn_right_anim)
        var so:Long = 0
        for (id in arrayOf(classbinding.switchSound, classbinding.switchVibro, classbinding.switchAnim, classbinding.switchNotification)) {
            id.setOnClickListener{pressed(it)}
            val anim = AnimationUtils.loadAnimation(this.activity ,R.anim.appearance)
            anim.startOffset = so
            anim(id, anim)
            so += 90
        }
        return classbinding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.peekHeight = -1
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {}

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (isLand(resources)) {if (slideOffset < 0.1) close()}
                    else {if (slideOffset > 0.1) close()}
                }
            })
        }
    }

    private fun pressed(v: View) {
        when (v) {
            classbinding.switchSound -> {
                if (classbinding.switchSound.isChecked)
                    pref?.edit()?.putBoolean("switch_sound", true)?.apply()
                else
                    pref?.edit()?.putBoolean("switch_sound", false)?.apply()
            }

            classbinding.switchVibro -> {
                if (classbinding.switchVibro.isChecked)
                    pref?.edit()?.putBoolean("switch_vibro", true)?.apply()
                else
                    pref?.edit()?.putBoolean("switch_vibro", false)?.apply()
            }

            classbinding.switchAnim -> {
                if (classbinding.switchAnim.isChecked)
                    pref?.edit()?.putBoolean("switch_anim", true)?.apply()
                else
                    pref?.edit()?.putBoolean("switch_anim", false)?.apply()
            }

            classbinding.switchNotification -> {
                if (classbinding.switchNotification.isChecked)
                    pref?.edit()?.putBoolean("switch_notification", true)?.apply()
                else
                    pref?.edit()?.putBoolean("switch_notification", false)?.apply()
            }
        }
        vibration(v)
    }

    private fun close() {this.dismiss()}

    private fun anim(v: View, res: Int) {anim(v, res, this.activity)}
}