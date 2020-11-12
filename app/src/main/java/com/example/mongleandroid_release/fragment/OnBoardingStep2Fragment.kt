package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.LoginActivity
import com.example.mongleandroid_release.activity.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.onboarding_step1.*
import kotlinx.android.synthetic.main.onboarding_step2.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


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
        // list up

        // vp 이동

        // btn 이동
        onboarding_step2_next.setOnClickListener {

            suspend fun fetchTwoDocs() = coroutineScope {
                val deferreds = listOf(     // fetch two docs at the same time
                    async { // mongle3 로 변경
                        onboarding_2_img_mongle.setImageResource(R.drawable.onboarding_3_img_mongle) },  // async returns a result for the first doc
                    async { onboarding_2_img_mongle.startAnimation(AnimationUtils.loadAnimation(context,R.anim.mongle_out)) } ,
                    async { act.onboarding_vp.currentItem =2 }
                )
                deferreds.awaitAll()
            }


//
//
//            // mongle3 로 변경
//            onboarding_2_img_mongle.setImageResource(R.drawable.onboarding_3_img_mongle)
//            // mongle exit anim
//            onboarding_2_img_mongle.startAnimation(AnimationUtils.loadAnimation(this.context,R.anim.mongle_out))
//
//
//            act.onboarding_vp.currentItem =2
        }

        onboarding_step2_skip.setOnClickListener {
            val intent = Intent(it.context , LoginActivity::class.java)
            it.context.startActivity(intent)
            act.finish()
        }

    }

    override fun onResume() {
        super.onResume()
        onboarding_2_img_mongle.setImageResource(R.drawable.onboarding_2_img_mongle)
        onboarding_step2_img_list.startAnimation(AnimationUtils.loadAnimation(this.context,R.anim.mongle_list_in))

    }




}