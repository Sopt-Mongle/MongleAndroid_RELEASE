package com.example.mongleandroid_release.fragment

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
import com.example.mongleandroid_release.activity.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.onboarding_step1.*
import kotlinx.android.synthetic.main.onboarding_step2.*
import kotlinx.android.synthetic.main.onboarding_step3.*


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
        onboarding_3_img_mongle.startAnimation(AnimationUtils.loadAnimation(this.context,R.anim.mongle_in))

    }
}