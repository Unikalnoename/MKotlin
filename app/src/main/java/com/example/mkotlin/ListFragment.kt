package com.example.mkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        classbinding = FragmentListBinding.inflate(inflater, container, false)
        classbinding.textView2.setOnClickListener {toMain()}
        classbinding.button.setOnClickListener {openSettings(it)}
        pref = this.activity?.getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        anim(classbinding.textView2, R.anim.alpha)
        anim(classbinding.list, R.anim.appearance)
        anim(classbinding.button, R.anim.quast_land_anim)
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
        return classbinding.root
    }

    private fun openSettings(v: View) {
        BottomFragment().show(childFragmentManager, "tag")
        anim(v, R.anim.press)
        vibration(v)
    }

    private fun toMain() {
        val tab = requireActivity().findViewById<View>(R.id.tabLayout) as TabLayout
        tab.getTabAt(0)?.select()
    }

    private fun anim(v: View, res: Int) {anim(v,res, this.activity)}

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        adapt()
    }

    private fun adapt() {
        val index = classbinding.list.firstVisiblePosition
        val view = classbinding.list.getChildAt(0)
        val top = if (view == null) 0 else view.top - classbinding.list.paddingTop
        val mainArray = arrayOf(resources.getString(R.string.Kyiv), resources.getString(R.string.Odessa), resources.getString(R.string.Dnipro), resources.getString(R.string.Lviv), resources.getString(R.string.Sumy), resources.getString(R.string.Rovno), resources.getString(R.string.Lutsk))
        val adapter = this.activity?.let {ArrAdapter(it, mainArray)}
        classbinding.list.adapter = adapter
        classbinding.list.setSelectionFromTop(index, top)
    }

    override fun onPause() {
        super.onPause()
        DrctrsStuff.appearance = false
        DrctrsStuff.needanimation = false
        adapt()
    }
}