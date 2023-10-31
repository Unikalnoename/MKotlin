package com.example.mkotlin.constants

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

private var pref: SharedPreferences? = null
private var mToast: Toast? = null

fun anim(v: View, res: Int, context: Context?) {
    if (context != null)
        pref = context.getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
    if (pref?.getBoolean("switch_anim", true) == true)
        v.startAnimation(AnimationUtils.loadAnimation(context, res))
}

fun anim(v: View, anim: Animation) {
    if (pref?.getBoolean("switch_anim", true) == true)
        v.startAnimation(anim)
}

@Suppress("DEPRECATION")
fun vibration(context: Context?, time: Long) {
    if (pref?.getBoolean("switch_vibro", true) == true) {
        val vibration = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibration.vibrate(time)
    }
}

fun vibration(v: View) {
    if (pref?.getBoolean("switch_vibro", true) == true)
        v.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
}

fun sound(res: Int, context: FragmentActivity?) {
    if (pref?.getBoolean("switch_sound", true) == true)
        MediaPlayer.create(context, res).start()
}

fun toast(context: Context?, string: String) {
    if (pref?.getBoolean("switch_notification", true) == true) {
        mToast?.cancel()
        mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT)
        mToast?.show()
    }
}

fun isLand(resources: Resources): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE