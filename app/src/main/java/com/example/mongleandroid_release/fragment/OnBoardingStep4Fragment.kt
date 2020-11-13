package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.LoginActivity
import com.example.mongleandroid_release.activity.MainActivity
import com.example.mongleandroid_release.activity.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.onboarding_step2.*
import kotlinx.android.synthetic.main.onboarding_step3.*
import kotlinx.android.synthetic.main.onboarding_step4.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class OnBoardingStep4Fragment : Fragment() {
    private val act by lazy { context as OnBoardingActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.onboarding_step4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // btn 이동
        onboarding_step4_login.setOnClickListener {
            val intent = Intent(it.context , LoginActivity::class.java)
            it.context.startActivity(intent)
            act.finish()
        }

        onboarding_step4_main.setOnClickListener {
            val intent = Intent(it.context , MainActivity::class.java)
            it.context.startActivity(intent)
            act.finish()
        }
    }

    override fun onStart() {
        super.onStart()
        onboarding_step4_main.visibility = View.GONE
        onboarding_step4_login.visibility = View.GONE
        onboarding_4_img_mongle.visibility = View.VISIBLE
    }


    override fun onResume() {
        super.onResume()
        onboarding_step4_main.visibility = View.GONE
        onboarding_step4_login.visibility = View.GONE
        onboarding_4_img_mongle.visibility = View.VISIBLE
        GlobalScope.launch {
//            this@OnBoardingStep4Fragment.activity?.runOnUiThread(java.lang.Runnable {
//                onboarding_step4_main.visibility = View.GONE
//                onboarding_step4_login.visibility = View.GONE
//                onboarding_4_img_mongle.visibility = View.VISIBLE
//            })
            onboarding_4_img_mongle.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mongle_down))
            delay(900L)
            this@OnBoardingStep4Fragment.activity?.runOnUiThread(java.lang.Runnable {
                onboarding_step4_login.visibility = View.VISIBLE
            })
            onboarding_step4_login.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mongle_list_in))

            delay(800L)
            this@OnBoardingStep4Fragment.activity?.runOnUiThread(java.lang.Runnable {
                onboarding_step4_main.visibility = View.VISIBLE
            })
            onboarding_step4_main.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mongle_list_in))
        }

    }


}