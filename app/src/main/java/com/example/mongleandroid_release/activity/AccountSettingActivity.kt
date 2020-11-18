package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.dialog.DialogChangePassword
import com.example.mongleandroid_release.dialog.DialogLogout
import com.example.mongleandroid_release.dialog.DialogQuitService
import com.example.mongleandroid_release.network.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_account_setting.*

class AccountSettingActivity : AppCompatActivity() {
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
            val dlg = DialogQuitService(this)
            dlg.start()
            dlg.setOnClickListener { content ->
                if(content == "탈퇴하기") {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    //현 액티비티 완전 종료
                    ActivityCompat.finishAffinity(this)
                }
            }
        }

    }
}