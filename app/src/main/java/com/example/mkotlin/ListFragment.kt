package com.example.mkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        return classbinding.root
    }

    private fun toMain() {
        val tab = requireActivity().findViewById<View>(R.id.tabLayout) as TabLayout
        tab.getTabAt(0)?.select()
    }

    private fun anim(v: View, res: Int){
        anim(v,res, this.activity)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        val arrayAdapter: ArrayAdapter<*>
        val mainArray = arrayOf(resources.getString(R.string.Kyiv), resources.getString(R.string.Odessa), resources.getString(R.string.Dnipro), resources.getString(R.string.Lviv), resources.getString(R.string.Sumy), resources.getString(R.string.Rovno), resources.getString(R.string.Lutsk))

        arrayAdapter = this.activity?.let {ArrayAdapter(it, R.layout.list_item, mainArray)}!!

        classbinding.list.onItemClickListener = AdapterView.OnItemClickListener {parent, itemClicked, position, id ->
            Toast.makeText(this.activity, resources.getString(R.string.apl), Toast.LENGTH_SHORT).show()
            pref?.edit()?.putString("city_now", (itemClicked as TextView).text.toString())?.apply()
            anim(itemClicked, R.anim.press)
            vibration(classbinding.list)
        }
        classbinding.list.adapter = arrayAdapter
    }
}