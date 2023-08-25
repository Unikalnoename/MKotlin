package com.example.mkotlin.constants

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentActivity

@Suppress("DEPRECATION")
fun vibro(context: Context, time: Long) {
    val vibration = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibration.vibrate(time)
}

fun vibration(v: View) {
    v.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
}

fun isLand(resources: Resources): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

fun sound(res: Int, context: FragmentActivity?) {
    MediaPlayer.create(context, res).start()
}

fun anim(v: View, res: Int, context: FragmentActivity?) {
    v.startAnimation(AnimationUtils.loadAnimation(context, res))
}