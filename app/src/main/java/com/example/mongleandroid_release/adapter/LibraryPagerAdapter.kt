package com.example.mongleandroid_release.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mongleandroid_release.util.FragmentTab

class LibraryPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragments: ArrayList<FragmentTab> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LibraryThemaFragment()
            1 -> LibrarySentenceFragment()
            else -> LibraryCuratorFragment()
        }
    }

    override fun getCount() = 3

    fun addItems(fragment: FragmentTab) {
        fragments.add(fragment)
    }
}