package com.example.mkotlin

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.example.mkotlin.constants.DrctrsStuff
import com.example.mkotlin.constants.anim

class ArrAdapter(private val context: Context, private val values: Array<String>) :
    ArrayAdapter<String?>(context, R.layout.list_item, values) {
    private var pref: SharedPreferences? = null
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.list_item, parent, false)
        val textView = rowView.findViewById<View>(R.id.item) as TextView
        val imageView = rowView.findViewById<View>(R.id.image) as ImageView
        textView.text = values[position]
        val string = values[position]
        pref = this.context.getSharedPreferences("MEMORY", Context.MODE_PRIVATE)

        if (DrctrsStuff.needanimation && string == DrctrsStuff.city_before) {
            textView.x = 150F
            //здесь значение в которое оно увеличивается из 1.0
            textView.scaleY = 1.15F
            textView.scaleX = 1.15F
            //разница в scaleX и scaleY в файлах из-за того что в press_to_right ведь увеличение идёт с 0.8 а не 1.0
            //желаемый размер после увелечения делить на 0.8
            //в to_left 1.0 поделить на желаемый размер
            anim(textView, R.anim.to_left)
            if (pref?.getBoolean("switch_anim", true) == false) {
                textView.x = 0F
                textView.scaleY = 1F
                textView.scaleX = 1F
            }
            imageView.visibility = View.VISIBLE
            anim(imageView, R.anim.minus_alpha_to_right)
            if (pref?.getBoolean("switch_anim", true) == true) {
                val colorFrom = getColor(context, R.color.red)
                val colorTo = getColor(context, R.color.teal_700)
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                colorAnimation.duration = 450
                colorAnimation.addUpdateListener {animator -> textView.setTextColor(animator.animatedValue as Int)}
                colorAnimation.start()
            }
        }

        if (string == pref?.getString("city_now", context.getString(R.string.Kyiv))) {
            imageView.visibility = View.VISIBLE
            if (DrctrsStuff.needanimation && (pref?.getBoolean("switch_anim", true) == true)) {
                val colorFrom = getColor(context, R.color.teal_700)
                val colorTo = getColor(context, R.color.red)
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                colorAnimation.duration = 450
                colorAnimation.addUpdateListener {animator -> textView.setTextColor(animator.animatedValue as Int)}
                colorAnimation.start()
                anim(imageView, R.anim.appearance_slow)
                anim(textView, R.anim.press_to_right)
            }
            else {
                textView.x = 150F
                textView.scaleY = 1.15F
                textView.scaleX = 1.15F
                textView.setTextColor(Color.RED)
            }
        }
        else imageView.visibility = View.INVISIBLE

        if (DrctrsStuff.appearance) {
            var so:Long = 0
            val anim = AnimationUtils.loadAnimation(this.context, R.anim.appearance_fast)
            for (element in values) {
                if (string == element) {
                    anim.startOffset = so
                    anim(textView, anim)
                }
                so += 90
            }

            if (string == pref?.getString("city_now", context.getString(R.string.Kyiv)))
                anim(imageView, R.anim.alpha_scale)
        }

        return rowView
    }

    private fun anim(v: View, res: Int) {anim(v, res, this.context)}
}