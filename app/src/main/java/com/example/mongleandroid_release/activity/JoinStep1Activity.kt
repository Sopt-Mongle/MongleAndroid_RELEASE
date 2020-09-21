package com.example.mongleandroid_release.activity

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_join_step1.*


class JoinStep1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step1)

        // 개인정보 처리방침 설명창으로 이동
        activity_join_step1_btn_agreeinfo.setOnClickListener {
            val intent = Intent(this, JoinStep1PolicyActivity::class.java)
            startActivity(intent)
        }

        // 회원가입 2단계로 이동
        activity_join_step1_btn_next.setOnClickListener {
            if(activity_join_step1_cb.isChecked) {
                val intent = Intent(this, JoinStep2Activity::class.java)
                startActivity(intent)
            } else {
                activity_join_step1_img_warning.visibility = VISIBLE
                activity_join_step1_tv_warning.visibility = VISIBLE
            }
        }

        // 체크박스 여부에 따른 경고문구 표시 & 프로그래스바 색상 변경
        activity_join_step1_cb.setOnClickListener {
            if(activity_join_step1_cb.isChecked) {
                activity_join_step1_img_warning.visibility = GONE
                activity_join_step1_tv_warning.visibility = GONE
                activity_join_step1_pgb_out.setBackgroundResource(R.drawable.dot_circle_progresson_out)
                activity_join_step1_pgb_in.setBackgroundResource(R.drawable.dot_circle_progresson_in)
            } else {
                activity_join_step1_pgb_out.setBackgroundResource(R.drawable.dot_circle_progressoff_out)
                activity_join_step1_pgb_in.setBackgroundResource(R.drawable.dot_circle_progressoff_in)
            }
        }





    }
}