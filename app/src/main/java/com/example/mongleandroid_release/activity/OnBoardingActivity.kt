package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.OnBoardingViewPagerAdapter
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        // viewPagerAdapter 연결
        onboarding_vp.adapter = OnBoardingViewPagerAdapter(supportFragmentManager)
        onboarding_vp.offscreenPageLimit = 3

        dots_indicator.setViewPager(onboarding_vp)
    }
}