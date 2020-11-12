package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mongleandroid_release.dialog.DialogLogin
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.customEnqueue
import com.example.mongleandroid_release.network.data.request.RequestLoginData
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        activity_login_tv_join.setOnClickListener {
            val intent = Intent(this, JoinStep1Activity::class.java)
            startActivity(intent)
        }

        activity_login_btn_login.setOnClickListener {
            requestToServer.service.requestLogin(
                RequestLoginData(
                    email = activity_login_et_email.text.toString(),
                    password = activity_login_et_pass.text.toString()
                )
            ).customEnqueue(
                onError = {
                    Log.d("error", "에러")
                    val dlg = DialogLogin(this)
                    dlg.start()
                },
                onSuccess = {
                    if(it.status == 200) {
                        Log.e("토큰", " $it")
                        SharedPreferenceController.setAccessToken(this, it.data.accessToken)
                        SharedPreferenceController.setMail(this, activity_login_et_email.text.toString())
                        SharedPreferenceController.setPasswd(this, activity_login_et_pass.text.toString())

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else if (it.status == 400) {
                        val dlg = DialogLogin(this)
                        dlg.start()
                    }
                }
            )


        }

    }

//    // 비회원 / 회원 구분
//    if(SharedPreferenceController.getMail(this).isBlank() ||
//    SharedPreferenceController.getPasswd(this).isBlank())
//    {
//        // 아이디, 비밀번호가 저장되어있지 않는 경우 = 비회원
//        SharedPreferenceController.setAccessToken(this, "guest")
//    } else {
//        // 자동로그인 - 토큰 새로 얻음
//
//        Log.d("자동로그인", SharedPreferenceController.getAccessToken(this))
//        requestToServer.service.requestLogin(
//            RequestLoginData(
//                email = SharedPreferenceController.getMail(this),
//                password = SharedPreferenceController.getPasswd(this)
//            )
//        ).customEnqueue(
//            onError = {
//                Log.d("error", "에러")
//            },
//            onSuccess = {
//                if(it.status == 200) {
//                    Log.e("토큰 ", " $it")
//                    SharedPreferenceController.setAccessToken(this, it.data.accessToken)
//                }
//            }
//        )
//    }
}