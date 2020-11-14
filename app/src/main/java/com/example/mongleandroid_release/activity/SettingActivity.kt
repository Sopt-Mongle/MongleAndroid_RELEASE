package com.example.mongleandroid_release.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        // 1:1 문의하기 눌렀을 때 메일 보내기 창 뜨기
        tv_cs_one.setOnClickListener(View.OnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address =
                arrayOf("mongle.official@gmail.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, "test@test")
            email.putExtra(Intent.EXTRA_TEXT, "내용 미리보기 (미리적을 수 있음)")
            startActivity(email)
        })

    }

}