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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mkotlin.constants.ArrAdapter
import com.example.mkotlin.constants.DrctrsStuff
import com.example.mkotlin.constants.anim
import com.example.mkotlin.constants.vibration
import com.example.mkotlin.databinding.FragmentListBinding
import com.google.android.material.tabs.TabLayout

class ListFragment : Fragment() {
    private lateinit var classbinding: FragmentListBinding
    private var pref: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        classbinding = FragmentListBinding.inflate(inflater, container, false)
        classbinding.textView2.setOnClickListener {toMain()}
        pref = this.activity?.getSharedPreferences("MEMORY", Context.MODE_PRIVATE)
        anim(classbinding.textView2, R.anim.alpha)
        anim(classbinding.list, R.anim.appearance)
        DrctrsStuff.appearance = true

        classbinding.list.onItemClickListener = AdapterView.OnItemClickListener {parent, itemClicked, position, id ->
            if (classbinding.list.getItemAtPosition(position).toString() != pref?.getString("city_now", resources.getString(R.string.Kyiv))) {
                Toast.makeText(this.activity, resources.getString(R.string.apl), Toast.LENGTH_SHORT).show()
                vibration(classbinding.list)
                pref?.edit()?.putString("city_now", classbinding.list.getItemAtPosition(position).toString())?.apply()
                DrctrsStuff.needanimation = true
            }
            else
                DrctrsStuff.needanimation = false
            DrctrsStuff.appearance = false
            adapt()
        }

        classbinding.list.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                // TODO Auto-generated method stub
            }
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {DrctrsStuff.appearance = false}
        })

        return classbinding.root
    }

    private fun toMain() {
        val tab = requireActivity().findViewById<View>(R.id.tabLayout) as TabLayout
        tab.getTabAt(0)?.select()
    }

    private fun anim(v: View, res: Int) {
        anim(v,res, this.activity)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        DrctrsStuff.needanimation = false
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
    }
}