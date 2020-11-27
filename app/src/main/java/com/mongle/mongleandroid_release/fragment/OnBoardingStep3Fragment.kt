package com.mongle.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.LoginActivity
import com.mongle.mongleandroid_release.activity.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.onboarding_step1.*
import kotlinx.android.synthetic.main.onboarding_step2.*
import kotlinx.android.synthetic.main.onboarding_step3.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class OnBoardingStep3Fragment : Fragment() {
    private val act by lazy { context as OnBoardingActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.onboarding_step3, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // btn 이동
        onboarding_step3_next.setOnClickListener {
            act.onboarding_vp.currentItem = 3
        }

        onboarding_step3_skip.setOnClickListener {
            val intent = Intent(it.context , LoginActivity::class.java)
            it.context.startActivity(intent)
            act.finish()
        }
    }


    override fun onResume() {
        super.onResume()
        onboarding_3_img_mongle.visibility = View.VISIBLE
        onboarding_step3_next.visibility = View.GONE
        onboarding_step3_skip.visibility = View.GONE
        GlobalScope.launch {
//            this@OnBoardingStep3Fragment.activity?.runOnUiThread(java.lang.Runnable {
//                onboarding_3_img_mongle.visibility = View.VISIBLE
//                onboarding_step3_next.visibility = View.GONE
//                onboarding_step3_skip.visibility = View.GONE
//            })
            onboarding_3_img_mongle.startAnimation(AnimationUtils.loadAnimation(context,R.anim.mongle_in))
            delay(1000L)
            onboarding_3_img_mongle.startAnimation(AnimationUtils.loadAnimation(context,R.anim.mongle_jump_up))
            delay(500L)
            onboarding_3_img_mongle.startAnimation(AnimationUtils.loadAnimation(context,R.anim.mongle_jump_down))
            this@OnBoardingStep3Fragment.activity?.runOnUiThread(java.lang.Runnable {
                onboarding_step3_next.visibility = View.VISIBLE
                onboarding_step3_skip.visibility = View.VISIBLE
            })
            onboarding_step3_next.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_fade_in))
            onboarding_step3_skip.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_fade_in))
        }

    }
}