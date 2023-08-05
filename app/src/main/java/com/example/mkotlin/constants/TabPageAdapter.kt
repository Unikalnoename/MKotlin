package com.example.mkotlin.constants

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mkotlin.ExitFragment
import com.example.mkotlin.HomeFragment
import com.example.mkotlin.ListFragment

class TabPageAdapter(activity:FragmentActivity, private val tabCount: Int) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> HomeFragment()
            1 -> ListFragment()
            2 -> ExitFragment()
            else -> HomeFragment()
        }
    }
}