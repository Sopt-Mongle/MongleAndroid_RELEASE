package com.example.mongleandroid_release.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mongleandroid_release.fragment.SearchCuratorFragment
import com.example.mongleandroid_release.fragment.SearchSentenceFragment
import com.example.mongleandroid_release.fragment.SearchThemeFragment

class SearchTabAdapter(fm:FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> SearchThemeFragment()
            1 -> SearchSentenceFragment()
            else -> SearchCuratorFragment()
        }
    }

    override fun getCount() = 3

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}