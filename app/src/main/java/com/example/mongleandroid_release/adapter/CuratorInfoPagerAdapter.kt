package com.example.mongleandroid_release.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mongleandroid_release.fragment.CuratorInfoSentenceFragment
import com.example.mongleandroid_release.fragment.CuratorInfoThemaFragment

class CuratorInfoPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position : Int) : Fragment {
        return when(position){
            0 -> CuratorInfoThemaFragment()
            else -> CuratorInfoSentenceFragment()
        }
    }

    override fun getCount() = 2
}