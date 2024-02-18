package com.example.mkotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.mkotlin.constants.DrctrsStuff
import com.example.mkotlin.constants.anim
import com.example.mkotlin.constants.toast
import com.example.mkotlin.constants.vibration
import com.example.mkotlin.databinding.FragmentListBinding
import com.google.android.material.tabs.TabLayout

class ListFragment : Fragment() {
    private lateinit var classbinding: FragmentListBinding
    private var pref: SharedPreferences? = null
    private var newArr = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        classbinding = FragmentListBinding.inflate(inflater, container, false)
        classbinding.textView2.setOnClickListener {toMain()}
        classbinding.button.setOnClickListener {openSettings(it)}
        pref = this.activity?.getSharedPreferences("MEMORY", Context.MODE_PRIVATE)

        var so = 0L
        for (view in arrayOf(classbinding.list, classbinding.button, classbinding.spinner)) {
            val ani = AnimationUtils.loadAnimation(context, R.anim.appearance)
            ani.startOffset = so
            anim(view, ani)
            so += 150
        }

        anim(classbinding.textView2, R.anim.alpha_scale)
        DrctrsStuff.appearance = true
        DrctrsStuff.city_before = pref?.getString("city_now", context?.getString(R.string.Kyiv)).toString()

        classbinding.list.onItemClickListener = AdapterView.OnItemClickListener {parent, itemClicked, position, id ->
            if (classbinding.list.getItemAtPosition(position).toString() != pref?.getString("city_now", resources.getString(R.string.Kyiv))) {
                toast(context, resources.getString(R.string.apl))
                vibration(classbinding.list)
                DrctrsStuff.city_before = pref?.getString("city_now", context?.getString(R.string.Kyiv)).toString()
                pref?.edit()?.putString("city_now", classbinding.list.getItemAtPosition(position).toString())?.apply()
                DrctrsStuff.needanimation = true
            }
            else
                DrctrsStuff.needanimation = false
            DrctrsStuff.appearance = false
            adapt()
        }

        classbinding.list.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {}

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                DrctrsStuff.appearance = false
                DrctrsStuff.needanimation = false
            }
        })
        
        updateSpinner()

        classbinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                pref?.edit()?.putString("spinner_now", classbinding.spinner.getItemAtPosition(position).toString())?.apply()
                DrctrsStuff.needanimation = false
                adapt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        return classbinding.root
    }

    private fun updateSpinner() {
        val arr = mutableListOf(resources.getString(R.string.by_population), resources.getString(R.string.alphabetically), resources.getString(R.string.accidentally))

        newArr.add(0, pref?.getString("spinner_now", resources.getString(R.string.by_population)).toString())

        for (i in arr) {
            if (i != pref?.getString("spinner_now", resources.getString(R.string.by_population)).toString() && !newArr.contains(i))
                newArr.add(i)
        }

        newArr = newArr.distinctBy {it.length}.toMutableList()

        val adapter: ArrayAdapter<String>? = this.activity?.let {ArrayAdapter<String>(it, R.layout.spinner_item, newArr)}
        adapter?.setDropDownViewResource(R.layout.spinner_dropdown_item)
        classbinding.spinner.adapter = adapter
    }

    private fun openSettings(v: View) {
        if (!DrctrsStuff.is_bottom_open) {
            BottomFragment().show(childFragmentManager, "tag")
            anim(v, R.anim.press)
            vibration(v)
        }
    }

    private fun toMain() {
        val tab = requireActivity().findViewById<View>(R.id.tabLayout) as TabLayout
        tab.getTabAt(0)?.select()
    }

    private fun anim(v: View, res: Int) {anim(v,res, this.activity)}

    private fun adapt() {
        val stockArray = arrayOf(resources.getString(R.string.Kyiv), resources.getString(R.string.Kharkov), resources.getString(R.string.Odessa), resources.getString(R.string.Dnipro), resources.getString(R.string.Zaporozhye), resources.getString(R.string.Lviv), resources.getString(R.string.Krivoy_Rog),
            resources.getString(R.string.Nikolaev), resources.getString(R.string.Vinnitsa), resources.getString(R.string.Poltava), resources.getString(R.string.Chernihiv), resources.getString(R.string.Sumy), resources.getString(R.string.Zhytomyr), resources.getString(R.string.Khmelnitsky),
            resources.getString(R.string.Rovno), resources.getString(R.string.Kremenchug), resources.getString(R.string.Ternopil), resources.getString(R.string.Lutsk), resources.getString(R.string.Kramatorsk), resources.getString(R.string.Slavyansk))

        val mainArray: Array<String> = when (pref?.getString("spinner_now", resources.getString(R.string.by_population))) {
            getString(R.string.by_population) -> {
                DrctrsStuff.can_spinner_refresh = true
                stockArray
            }

            getString(R.string.accidentally) -> {
                if (DrctrsStuff.can_spinner_refresh) {
                    DrctrsStuff.can_spinner_refresh = false
                    val arr: Array<String> = stockArray
                    arr.shuffle()
                    for (i in arr)
                        pref?.edit()?.putString("city" + arr.indexOf(i), i)?.apply()
                    arr
                }
                else {
                    val arr = mutableListOf<String>()
                    for (i in stockArray.indices)
                        arr.add(pref?.getString("city$i", stockArray[i])!!)
                    arr.toTypedArray()
                }
            }

            getString(R.string.alphabetically) -> {
                DrctrsStuff.can_spinner_refresh = true
                stockArray.sortedArray()
            }

            else -> {
                DrctrsStuff.can_spinner_refresh = true
                stockArray
            }
        }

        val index = classbinding.list.firstVisiblePosition
        val view = classbinding.list.getChildAt(0)
        val top = if (view == null) 0 else view.top - classbinding.list.paddingTop
        val adapter = this.activity?.let {ArrAdapter(it, mainArray)}
        classbinding.list.adapter = adapter
        classbinding.list.setSelectionFromTop(index, top)
    }

    override fun onResume() {
        super.onResume()
        updateSpinner()
    }

    override fun onPause() {
        super.onPause()
        DrctrsStuff.appearance = false
        DrctrsStuff.needanimation = false
        adapt()
    }
}