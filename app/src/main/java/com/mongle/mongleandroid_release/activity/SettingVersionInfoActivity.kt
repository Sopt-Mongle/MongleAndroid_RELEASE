package com.mongle.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mongle.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_setting_version_info.*

class SettingVersionInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_version_info)

        //뒤로 가기 아이콘 눌렀을 때 화면 전환
        img_back_setting_version_info.setOnClickListener {
            finish()
        }

    }
}