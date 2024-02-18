package com.example.mkotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Switch
import com.example.mkotlin.constants.DrctrsStuff
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
        DrctrsStuff.is_bottom_open = true
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
        anim(classbinding.textView, R.anim.alpha_scale)
        if (isLand(resources)) {
            var so = 0L
            for (id in arrayOf(classbinding.switchAnim,classbinding.switchNotification,
                classbinding.switchSound, classbinding.switchVibro)) {
                id.setOnClickListener{pressed(it)}
                val anim = AnimationUtils.loadAnimation(this.activity ,R.anim.appearance_fast)
                anim.startOffset = so
                anim(id, anim)
                so += 100
            }
            for (id in arrayOf(classbinding.imageView, classbinding.buttonSupport)) {
                val ani = AnimationUtils.loadAnimation(this.activity, R.anim.alpha_up)
                ani.startOffset = so
                anim(id, ani)
                so += 300
            }
        }
        else {
            var so = 250L
            for (id in arrayOf(classbinding.imageView, classbinding.switchSound, classbinding.switchVibro,
                classbinding.switchAnim, classbinding.switchNotification, classbinding.buttonSupport)) {
                if (id is Switch) id.setOnClickListener{pressed(it)}
                val anim = AnimationUtils.loadAnimation(this.activity ,R.anim.alpha_up)
                anim.startOffset = so
                anim(id, anim)
                so += 100
            }
        }
        return classbinding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.peekHeight = if (isLand(resources)) 1 else 0
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {}

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset < 0.01)
                        close()
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

    override fun onStop() {
        super.onStop()
        DrctrsStuff.is_bottom_open = false
    }

    private fun close() {this.dismiss()}

    private fun anim(v: View, res: Int) {anim(v, res, this.activity)}
}