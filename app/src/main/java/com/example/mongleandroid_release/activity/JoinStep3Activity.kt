package com.example.mongleandroid_release.activity

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.view.marginTop
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_join_step2.*
import kotlinx.android.synthetic.main.activity_join_step3.*

class JoinStep3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step3)

        // 프로그래스바 애니메이션
        Handler().postDelayed({
            val progressAnimator_step3 = ObjectAnimator.ofInt(activity_join_step3_pgb, "progress", 50, 100)
            progressAnimator_step3.setDuration(500)
            progressAnimator_step3.start()
        }, 200)

        activity_join_step3_btn_back.setOnClickListener {
            finish()
        }


    }

    override fun onPause() {
        super.onPause()
        // 화면 전환 시 애니메이션 없애는 코드
        overridePendingTransition(0, 0)
    }
}