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
import kotlinx.coroutines.*


class OnBoardingStep2Fragment : Fragment() {
    private val act by lazy { context as OnBoardingActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.onboarding_step2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // vp 이동

        // btn 이동
        onboarding_step2_next.setOnClickListener {
            animCoroutines()
        }

        onboarding_step2_skip.setOnClickListener {
            val intent = Intent(it.context , LoginActivity::class.java)
            it.context.startActivity(intent)
            act.finish()
        }

    }

    override fun onResume() {
        super.onResume()
        onboarding_step2_next.visibility = View.GONE
        onboarding_step2_skip.visibility = View.GONE
        onboarding_2_img_mongle.setImageResource(R.drawable.onboarding_2_img_mongle)
        onboarding_step2_img_list.visibility = View.VISIBLE
        GlobalScope.launch {
//            this@OnBoardingStep2Fragment.activity?.runOnUiThread(java.lang.Runnable {
//                onboarding_step2_next.visibility = View.GONE
//                onboarding_step2_skip.visibility = View.GONE
//                onboarding_2_img_mongle.setImageResource(R.drawable.onboarding_2_img_mongle)
//                onboarding_step2_img_list.visibility = View.VISIBLE
//            })
            onboarding_step2_img_list.startAnimation(AnimationUtils.loadAnimation(context,R.anim.mongle_list_in))
            delay(1000L)
            this@OnBoardingStep2Fragment.activity?.runOnUiThread(java.lang.Runnable {
                onboarding_step2_next.visibility = View.VISIBLE
                onboarding_step2_skip.visibility = View.VISIBLE
            })
            onboarding_step2_next.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_fade_in))
            onboarding_step2_skip.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_fade_in))

        }

    }



    private fun animCoroutines() {
        onboarding_2_img_mongle.setImageResource(R.drawable.onboarding_3_img_mongle)
        onboarding_2_img_mongle.startAnimation(AnimationUtils.loadAnimation(context,R.anim.mongle_out))
        GlobalScope.launch {
            delay(900L)
            this@OnBoardingStep2Fragment.activity?.runOnUiThread(java.lang.Runnable {
                act.onboarding_vp.currentItem =2
            }

            )

        }




    }


}