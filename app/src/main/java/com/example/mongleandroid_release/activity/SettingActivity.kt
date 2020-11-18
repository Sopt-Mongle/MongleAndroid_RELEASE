package com.example.mongleandroid_release.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        img_back_setting.setOnClickListener {
            finish()
        }

        //프로필 수정 눌렀을 떄 계정 설정 창 띄우기
        profile_change.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        //계정 설정 눌렀을 떄 계정 설정 창 띄우기
        setting_account.setOnClickListener {
            val intent = Intent(this, AccountSettingActivity::class.java)
            startActivity(intent)
        }

        sb_setting_push.setOnClickListener {
            val customToast = layoutInflater.inflate(R.layout.toast_ready, null)
            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            toast.view = customToast
            toast.show()
        }

        //버전 정보 눌렀을 떄 계정 설정 창 띄우기
        setting_info_version.setOnClickListener {
            val intent = Intent(this, SettingVersionInfoActivity::class.java)
            startActivity(intent)
        }

        //개발자 정보 눌렀을 떄 계정 설정 창 띄우기
        setting_info_developer.setOnClickListener {
            val intent = Intent(this, SettingDeveloperInfoActivity::class.java)
            startActivity(intent)
        }

        // 1:1 문의하기 눌렀을 때 메일 보내기 창 뜨기
        tv_cs_one.setOnClickListener(View.OnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address =
                arrayOf("mongle.official@gmail.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, "[문의]")
            email.putExtra(Intent.EXTRA_TEXT, "*몽글팀이 빠르게 처리할 수 있게 메일 제목에 간단하게 어떤 문의인지 적어주세요!\n" +
                    "\n" +
                    "1. 문의 유형(문의/신고/버그제보/기타) : \n" +
                    "2. 회원 아이디 (필요시 기입) : \n" +
                    "3. 문의 내용 :")
            startActivity(email)
        })

        //서비스 운영정책 눌렀을 떄 계정 설정 창 띄우기
        setting_service_policy.setOnClickListener {
            val intent = Intent(this, ServicePolicyActivity::class.java)
            startActivity(intent)
        }

        //개인정보 이용약관 눌렀을 떄 계정 설정 창 띄우기
        setting_privacy_terms.setOnClickListener {
            val intent = Intent(this, PrivacyTermsActivity::class.java)
            startActivity(intent)
        }

        //서비스 이용약관 눌렀을 떄 계정 설정 창 띄우기
        setting_service_terms.setOnClickListener {
            val intent = Intent(this, ServiceTermsActivity::class.java)
            startActivity(intent)
        }

        //오픈 소스 라이선 눌렀을 떄 계정 설정 창 띄우기
        setting_opensource.setOnClickListener {
            val intent = Intent(this, OpenSourceActivity::class.java)
            startActivity(intent)
        }

    }

}