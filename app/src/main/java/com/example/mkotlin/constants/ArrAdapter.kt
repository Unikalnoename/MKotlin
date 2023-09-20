package com.example.mkotlin.constants

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
import androidx.core.content.res.ResourcesCompat
import com.example.mkotlin.R

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

        if (string == pref?.getString("city_now", context.getString(R.string.Kyiv))) {
            textView.typeface = ResourcesCompat.getFont(context, R.font.serif)
            imageView.visibility = View.VISIBLE
            textView.setTextColor(Color.RED)
            if (DrctrsStuff.needanimation) {
                anim(imageView, R.anim.appearance_slow)
                anim(textView, R.anim.press)
            }
        }
        else
            imageView.visibility = View.INVISIBLE

        if (DrctrsStuff.appearance) {
            var so:Long = 0
            val anim = AnimationUtils.loadAnimation(this.context, R.anim.appearance_fast)
            for (element in values) {
                if (string == element) {
                    anim.startOffset = so
                    textView.startAnimation(anim)
                }
                so += 75
            }

            if (string == pref?.getString("city_now", context.getString(R.string.Kyiv)))
                anim(imageView, R.anim.alpha)
        }

        return rowView
    }

    private fun anim(v: View, res: Int) {
        anim(v,res, this.context)
    }
}