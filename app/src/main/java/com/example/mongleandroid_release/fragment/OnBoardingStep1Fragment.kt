package com.example.mongleandroid_release.fragment

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.LoginActivity
import com.example.mongleandroid_release.activity.MainActivity
import com.example.mongleandroid_release.activity.OnBoardingActivity
import com.example.mongleandroid_release.activity.WritingSentenceActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.onboarding_step1.*
import kotlinx.android.synthetic.main.onboarding_step2.*
import kotlinx.android.synthetic.main.onboarding_step4.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingStep1Fragment : Fragment() {

    private val act by lazy { context as OnBoardingActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.onboarding_step1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // btn 다음으로 이동
        onboarding_step1_next.setOnClickListener {
            act.onboarding_vp.currentItem = 1
        }

        onboarding_step1_skip.setOnClickListener {
            val intent = Intent(it.context , LoginActivity::class.java)
            it.context.startActivity(intent)
            act.finish()
        }


    }

    override fun onStart() {
        super.onStart()
        onboarding_step1_next.visibility = View.GONE
        onboarding_step1_skip.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        onboarding_step1_next.visibility = View.GONE
        onboarding_step1_skip.visibility = View.GONE
        GlobalScope.launch {
//            this@OnBoardingStep1Fragment.activity?.runOnUiThread(java.lang.Runnable {
//                onboarding_step1_next.visibility = View.GONE
//                onboarding_step1_skip.visibility = View.GONE
//            })
            delay(100L)
            this@OnBoardingStep1Fragment.activity?.runOnUiThread(java.lang.Runnable {
                onboarding_step1_next.visibility = View.VISIBLE
            })
            onboarding_step1_next.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_fade_in))

            delay(300L)
            this@OnBoardingStep1Fragment.activity?.runOnUiThread(java.lang.Runnable {
                onboarding_step1_skip.visibility = View.VISIBLE
            })
            onboarding_step1_skip.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_fade_in))
        }

    }




}