package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.dialog.DialogChangePassword
import com.example.mongleandroid_release.dialog.DialogLogout
import com.example.mongleandroid_release.dialog.DialogQuitService
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.request.RequestQuitUserData
import com.example.mongleandroid_release.network.data.response.ResponseQuitUserData
import kotlinx.android.synthetic.main.activity_account_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountSettingActivity : AppCompatActivity() {

    val requestToServer = RequestToServer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)

        //뒤로 가기 아이콘 눌렀을 때 화면 전환
        img_back_setting_account.setOnClickListener {
            finish()
        }

        //비밀번호 변경 아이콘 눌렀을 때 화면 전환
        password_change.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        //로그 아웃 버튼 눌렀을 떄 dialog 띄우기
        logout.setOnClickListener {
            SharedPreferenceController.setAccessToken(this, "logout")
            val dlg = DialogLogout(this)
            dlg.start()
            dlg.setOnClickListener { content ->
                if(content == "로그아웃") {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    ActivityCompat.finishAffinity(this)
                }
            }
        }

        //서비스 탈퇴 버튼 눌렀을 때 dialog 띄우기
        quit_service.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            val dlg = DialogQuitService(this)
            dlg.start()
            dlg.setOnClickListener { content ->
                if(content == "탈퇴하기") {

                    requestToServer.service.QuitUser(
                        token =  SharedPreferenceController.getAccessToken(this),
                        body = RequestQuitUserData(
                            email = this.let { SharedPreferenceController.getMail(it).toString() },
                            password = this.let {SharedPreferenceController.getPasswd(it).toString()}
                        )
                    ).enqueue(
                        object : Callback<ResponseQuitUserData> {
                            override fun onResponse(
                                call: Call<ResponseQuitUserData>,
                                response: Response<ResponseQuitUserData>
                            ) {
                                Log.d("탈퇴 통신 성공", " ")
                                if (response.isSuccessful) {

                                }
                            }

                            override fun onFailure(call: Call<ResponseQuitUserData>, t: Throwable) {
                                Log.d("탈퇴 통신 실패", "$t")

                            }

                        }
                    )
                    //현 액티비티 완전 종료
                    ActivityCompat.finishAffinity(this)
                }
            }

        }

    }
}