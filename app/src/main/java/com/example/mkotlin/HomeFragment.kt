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
import com.example.mkotlin.constants.toast
import com.example.mkotlin.constants.vibration
import com.example.mkotlin.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {
    private lateinit var classbinding: FragmentHomeBinding
    private var pref:SharedPreferences? = null
    private var count:Long = 0
    private var click = true
    private var showed = false
    private var isenebled = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        classbinding = FragmentHomeBinding.inflate(inflater, container, false)
        classbinding.text2.setOnClickListener {clicksAndAllThat(it)}
        classbinding.Text.setOnClickListener {lol()}
        classbinding.videoView.setOnClickListener {lol()}
        classbinding.boom.setOnClickListener {lolBoom()}
        classbinding.btn.setOnClickListener {click(it)}
        classbinding.btn2?.setOnClickListener{click(it)}
        pref = this.activity?.getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        if (isLand(resources)) {
            classbinding.btn2?.let{anim(it, R.anim.btn_right_anim)}
            anim(classbinding.btn, R.anim.btn_left_anim)
        }
        else
            anim(classbinding.btn, R.anim.appearance)
        var so = 0L
        for (id in listOf(classbinding.Text, classbinding.text2)) {
            val ani = AnimationUtils.loadAnimation(context, R.anim.alpha_scale)
            ani.startOffset = so
            anim(id, ani)
            so += 300
        }

        return classbinding.root
    }

    fun anim(v: View, res: Int) {anim(v, res, this.activity)}

    @SuppressLint("SetTextI18n")
    fun clicksAndAllThat(v: View) {
        if (!isenebled) {
            val anim = AnimationUtils.loadAnimation(this.activity, R.anim.record)
            anim.repeatCount = 3
            anim.duration = 100
            anim(v, anim)
        }
        else {
            anim(v, R.anim.press)
            if (!click) {
                click = true
                classbinding.text2.text = "${resources.getString(R.string.rec)} ${pref?.getLong("count_max", 0)!!}"
            }
            else {
                click = false
                classbinding.text2.text = "${resources.getString(R.string.shell)} $count"
            }
        }
    }

    private fun lol() {
        sound(R.raw.nosehonk)
        val tab = requireActivity().findViewById<View>(R.id.tabLayout) as TabLayout
        tab.getTabAt(1)?.select()
    }

    private fun lolBoom() {
        sound(R.raw.explosion)
        vibration(context, 1500)
        for (id in listOf(classbinding.text2, classbinding.boom, classbinding.btn2, classbinding.btn))
            id?.let{anim(it, R.anim.tremble)}
    }

    @SuppressLint("SetTextI18n")
    fun click (v: View) {
        DrctrsStuff.city_was = pref?.getString("city_now", resources.getString(R.string.Kyiv)).toString()
        anim(v, R.anim.press)
        vibration(context, 100)
        classbinding.text2.textSize = 35.5715f
        isenebled = true
        click = false
        if (DrctrsStuff.is_nuke)
            count += 100
        classbinding.text2.text = "${resources.getString(R.string.shell)} ${++count}"
        if (pref?.getLong("count_max", 0)!! < count) {
            pref?.edit()?.putLong("count_max", count)?.apply()
            if (!showed && count > 1) {
                sound(R.raw.omg)
                toast(context, resources.getString(R.string.newrec))
                showed = true
                anim(classbinding.text2, R.anim.record)
            }
        }
        if (!DrctrsStuff.is_nuke) {
            when (count) {
                1L -> {
                    if (!DrctrsStuff.isclick)
                        toast(context, resources.getString(R.string.toast))
                    DrctrsStuff.isclick = true
                    classbinding.Text.isClickable = true
                    classbinding.Text.setText(R.string.app_name)
                    classbinding.Text.rotationX = 45f
                    classbinding.Text.textSize = 50f
                }

                in 2..49 -> classbinding.Text.setText(R.string.app_name)

                50L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.setText(R.string.good)
                }

                in 51..99 -> classbinding.Text.setText(R.string.good)

                100L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.setText(R.string.mgood)
                }

                in 101..149 -> classbinding.Text.setText(R.string.mgood)

                150L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.setText(R.string.rez)
                }

                in 151..199 -> classbinding.Text.setText(R.string.rez)

                200L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.setText(R.string.hlop)
                }

                in 201..249 -> classbinding.Text.setText(R.string.hlop)

                250L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.setText(R.string.ab)
                }

                in 251..299 -> classbinding.Text.setText(R.string.ab)

                in 300..349 -> image(R.drawable.boom)

                350L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.boom.visibility = View.GONE
                    classbinding.Text.rotationX = 0f
                    classbinding.Text.textSize = 35f
                    classbinding.Text.visibility = View.VISIBLE
                    classbinding.Text.text = "${resources.getString(R.string.l1)}\n    ${resources.getString(R.string.l2)}"
                }

                in 351..399 -> classbinding.Text.text = "${resources.getString(R.string.l1)}\n    ${resources.getString(R.string.l2)}"

                400L -> {
                    anim(classbinding.Text, R.anim.change)
                    rocketsForReal()
                }

                in 401..403 -> rocketsForReal()

                404L -> uaMoment()

                405L -> {
                    anim(classbinding.Text, R.anim.change)
                    rocketsForReal()
                }

                in 406..449 -> rocketsForReal()

                450L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.rotationX = 45f
                    classbinding.Text.textSize = 50f
                    classbinding.Text.setText(R.string.n)
                }

                in 451..499 -> classbinding.Text.setText(R.string.n)

                in 500..549 -> image(R.drawable.bob)

                550L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.boom.visibility = View.GONE
                    classbinding.Text.rotationX = 0f
                    classbinding.Text.setText(R.string.x)
                    classbinding.Text.visibility = View.VISIBLE
                }

                in 551..599 -> classbinding.Text.setText(R.string.x)

                600L -> {
                    classbinding.videoView.visibility = View.VISIBLE
                    classbinding.Text.visibility = View.GONE
                    video(R.raw.zflix)
                }

                650L -> video(R.raw.pd)

                666L -> {
                    classbinding.videoView.visibility = View.GONE
                    classbinding.Text.visibility = View.VISIBLE
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.rotationX = 0f
                    classbinding.Text.setText(R.string.hell)
                    classbinding.Text.textSize = 40f
                }

                667L -> {
                    classbinding.Text.visibility = View.GONE
                    classbinding.videoView.visibility = View.VISIBLE
                    video(R.raw.pd)
                }

                700L -> {
                    classbinding.Text.visibility = View.VISIBLE
                    classbinding.videoView.visibility = View.GONE
                    anim(classbinding.Text, R.anim.change)
                    putinPhone2()
                }

                777L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.setText(R.string.m)
                    classbinding.Text.rotationX = 45f
                    classbinding.Text.textSize = 50f
                }

                778L -> {
                    anim(classbinding.Text, R.anim.change)
                    putinPhone2()
                }

                1000L -> {
                    anim(classbinding.Text, R.anim.change)
                    sound(R.raw.kidcheer)
                    classbinding.Text.setText(R.string.str)
                }

                1001L -> {
                    anim(classbinding.Text, R.anim.change)
                    putinPhone2()
                }

                1488L -> uaMoment()

                1489L -> {
                    anim(classbinding.Text, R.anim.change)
                    putinPhone2()
                }

                10000L -> {
                    anim(classbinding.Text, R.anim.change)
                    sound(R.raw.kidcheer)
                    classbinding.Text.rotationX = 0f
                    classbinding.Text.textSize = 40f
                    classbinding.Text.setText(R.string.ps)
                }

                10001L -> {
                    anim(classbinding.Text, R.anim.change)
                    putinPhone2()
                }

                100000L -> {
                    anim(classbinding.Text, R.anim.change)
                    sound(R.raw.gigatheme)
                    classbinding.Text.setText(R.string.nocomm)
                }

                100001L -> {
                    anim(classbinding.Text, R.anim.change)
                    putinPhone2()
                }

                else -> putinPhone2()
            }
        }
        else {
            DrctrsStuff.isclick = false
            classbinding.Text.rotationX = 45f
            classbinding.Text.textSize = 50f
            when(count) {
                101L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.setText(R.string.mgood)
                }

                202L -> {
                    anim(classbinding.Text, R.anim.change)
                    classbinding.Text.setText(R.string.hlop)
                }

                303L -> image(R.drawable.boom)

                404L -> {
                    classbinding.boom.visibility = View.GONE
                    classbinding.Text.visibility = View.VISIBLE
                    uaMoment()
                }

                505L -> image(R.drawable.bob)

                606L -> {
                    classbinding.boom.visibility = View.GONE
                    classbinding.videoView.visibility = View.VISIBLE
                    classbinding.Text.visibility = View.GONE
                    video(R.raw.zflix)
                }

                707L -> {
                    classbinding.Text.visibility = View.VISIBLE
                    classbinding.videoView.visibility = View.GONE
                    anim(classbinding.Text, R.anim.change)
                    putinPhone2()
                }

                1010L -> {
                    anim(classbinding.Text, R.anim.change)
                    sound(R.raw.kidcheer)
                    classbinding.Text.setText(R.string.str)
                }

                1111L -> {
                    anim(classbinding.Text, R.anim.change)
                    putinPhone2()
                }

                10100L -> {
                    anim(classbinding.Text, R.anim.change)
                    sound(R.raw.kidcheer)
                    classbinding.Text.rotationX = 0f
                    classbinding.Text.textSize = 40f
                    classbinding.Text.setText(R.string.ps)
                }

                10201L -> {
                    anim(classbinding.Text, R.anim.change)
                    putinPhone2()
                }
            }
        }
    }

    private fun uaMoment() {
        anim(classbinding.Text, R.anim.change)
        sound(R.raw.nosehonk)
        classbinding.Text.setText(R.string.ua)
    }

    private fun putinPhone2() {
        classbinding.Text.setText(R.string.pp2)
        classbinding.Text.rotationX = 45f
        classbinding.Text.textSize = 50f
    }

    private fun video(res: Int) {
        classbinding.videoView.setVideoPath("android.resource://${this.activity?.packageName}/$res")
        classbinding.videoView.start()
        classbinding.videoView.setOnPreparedListener {mp -> mp!!.isLooping = true}
    }

    @SuppressLint("SetTextI18n")
    private fun rocketsForReal() {
        if (isLand(resources))
            classbinding.Text.text = "${resources.getString(R.string.rr1)} ${resources.getString(R.string.rr2)} ${resources.getString(R.string.rr3)}"
        else
            classbinding.Text.text = "${resources.getString(R.string.rr1)}\n ${resources.getString(R.string.rr2)}\n ${resources.getString(R.string.rr3)}"
    }

    private fun image(res: Int) {
        classbinding.Text.visibility = View.INVISIBLE
        classbinding.boom.setImageResource(res)
        classbinding.boom.visibility = View.VISIBLE
    }

    private fun sound(res: Int) {sound(res, this.activity) }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        when (pref?.getString("city_now", resources.getString(R.string.Kyiv))!!.length) {
            in 8..10 -> {
                classbinding.btn.textSize = 30f
                classbinding.btn2?.textSize = 30f
            }
            in 11..Int.MAX_VALUE -> {
                classbinding.btn.textSize = 25f
                classbinding.btn2?.textSize = 25f
            }
            else -> {
                classbinding.btn.textSize = 40f
                classbinding.btn2?.textSize = 40f
            }
        }

        for (id in listOf(classbinding.btn, classbinding.btn2))
            id?.text = "${resources.getString(R.string.bomb)} ${pref?.getString("city_now", resources.getString(R.string.Kyiv))}"
        if (DrctrsStuff.isclick) {
            showed = false
            classbinding.boom.visibility = View.GONE
            classbinding.Text.visibility = View.VISIBLE
            isenebled = false
            count = 0
            classbinding.Text.textSize = 35f
            classbinding.Text.rotationX = 0f
            classbinding.Text.text = "${resources.getString(R.string.msg)} ${DrctrsStuff.city_was} ${resources.getString(R.string.msg2)}"
            classbinding.text2.textSize = 25f
            classbinding.text2.text = "${resources.getString(R.string.msg3)} ${resources.getString(R.string.msg4)}"
        }
    }
}