package com.example.mongleandroid_release.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.example.mongleandroid_release.fragment.OnBoardingStep1Fragment
import com.example.mongleandroid_release.fragment.OnBoardingStep2Fragment
import com.example.mongleandroid_release.fragment.OnBoardingStep3Fragment
import com.example.mongleandroid_release.fragment.OnBoardingStep4Fragment

class OnBoardingViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm,
BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> OnBoardingStep1Fragment()
            1 -> OnBoardingStep2Fragment()
            2 -> OnBoardingStep3Fragment()
            else -> OnBoardingStep4Fragment()
        }
    }

    override fun getCount() = 4
}