package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.SharedPreferenceController

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val load: ImageView = findViewById<ImageView>(R.id.img_splash_gif)
        Glide.with(this).load(R.drawable.splash).into(load)

        startLoading()



    }

    private fun startLoading() {

        if(SharedPreferenceController.getOnBoarding(this) == "true") {
            // 한번 실행했으면 true => 메인으로 이동
            if(SharedPreferenceController.getAccessToken(this) == "logout") {
                // 로그아웃한 사람
                startLogin()
            } else {
                startMain()
            }
        } else {
            startOnBoarding()
            // 온보딩 한번 실행 -> change true
            SharedPreferenceController.setOnBoarding(this, "true")
        }
    }

    private fun startMain() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }

    private fun startLogin() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }

    private fun startOnBoarding() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(baseContext, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }


}