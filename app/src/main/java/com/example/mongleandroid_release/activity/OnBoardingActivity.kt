package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.viewpager.widget.ViewPager
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.OnBoardingViewPagerAdapter
import com.example.mongleandroid_release.fragment.OnBoardingStep1Fragment
import com.example.mongleandroid_release.fragment.OnBoardingStep2Fragment
import com.example.mongleandroid_release.fragment.OnBoardingStep3Fragment
import com.example.mongleandroid_release.fragment.OnBoardingStep4Fragment
import com.example.mongleandroid_release.network.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.onboarding_step2.*

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)



        // viewPagerAdapter 연결
        onboarding_vp.adapter = OnBoardingViewPagerAdapter(supportFragmentManager)
        onboarding_vp.offscreenPageLimit = 0

        dots_indicator.setViewPager(onboarding_vp)

        onboarding_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        OnBoardingStep1Fragment()

                    }
                    1 -> {
                        OnBoardingStep2Fragment()

                    }
                    2 -> OnBoardingStep3Fragment()
                    else -> OnBoardingStep4Fragment()
                }


            }

        })
    }
}