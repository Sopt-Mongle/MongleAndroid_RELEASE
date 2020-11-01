package com.example.mongleandroid_release.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT  ) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> MainViewPager1Fragment()
            1 -> MainViewPager2Fragment()
            else -> MainViewPager3Fragment()
        }
    }

    override fun getCount()= 3
}