package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_account_setting.*
import kotlinx.android.synthetic.main.activity_account_setting.img_back_setting_account
import kotlinx.android.synthetic.main.activity_setting_developer_info.*

class SettingDeveloperInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_developer_info)

        //뒤로 가기 아이콘 눌렀을 때 화면 전환
        img_back_setting_developer_info.setOnClickListener {
            finish()
        }
    }
}