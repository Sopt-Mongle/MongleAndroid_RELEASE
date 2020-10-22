package com.example.mongleandroid_release.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class LibraryTabAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LibraryThemaFragment()
            1 -> LibrarySentenceFragment()
            else -> LibraryCuratorFragment()
        }
    }

    override fun getCount() = 3
}