package com.mongle.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.customEnqueue
import com.mongle.mongleandroid_release.network.data.request.RequestLoginData
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    val SPLASH_TIME : Long = 2600

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val load: ImageView = findViewById<ImageView>(R.id.img_splash_gif)
//        Glide.with(this).load(R.drawable.splash).into(load)

        if(SharedPreferenceController.getMail(this).isNullOrBlank()) {
            // 처음 들어온 사람
            startLoading()
        } else {
            // 두번째 이상 들어온 사람
            val now = System.currentTimeMillis()
            val mDate = Date(now)
            val simpleData = SimpleDateFormat("ddkkmmss")
            val nowTime = simpleData.format(mDate).toInt()

            Log.d("시간테스트 현재시간", nowTime.toString())

            // 현재시간 - pref 시간 = 3시간이상 이면 토큰 갱신
            if(SharedPreferenceController.getCurrentTime(this) == "") {
                // 로그인
                login()
                SharedPreferenceController.setCurrentTime(this, nowTime.toString())
            } else {
                val lastTime = SharedPreferenceController.getCurrentTime(this)!!.toInt()
                Log.d("시간테스트 now - last", "${nowTime - lastTime}")
                Log.d("시간테스트 lastTime", lastTime.toString())
                Log.d("시간테스트 원래토큰", SharedPreferenceController.getAccessToken(this))
                if(nowTime - lastTime < 0 || nowTime - lastTime > 30000) {
                    // 갱신
                    Log.d("시간테스트", "갱신")
                    SharedPreferenceController.setCurrentTime(this, nowTime.toString())
                    login()
                }
            }

            startLoading()
        }

    }

    private fun login() {
        requestToServer.service.requestLogin(
            RequestLoginData(
                email = SharedPreferenceController.getMail(this).toString(),
                password = SharedPreferenceController.getPasswd(this).toString()
            )
        ).customEnqueue(
            onError = {
                Log.d("error", "에러")
            },
            onSuccess = {
                if(it.status == 200) {
                    SharedPreferenceController.setAccessToken(this, it.data.accessToken)
                }
            }
        )
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
        }, SPLASH_TIME)
    }

    private fun startLogin() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME)
    }

    private fun startOnBoarding() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(baseContext, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME)
    }


}