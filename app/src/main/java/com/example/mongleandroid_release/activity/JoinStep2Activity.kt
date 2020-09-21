package com.example.mongleandroid_release.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_join_step2.*

class JoinStep2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step2)

        // 프로그래스바 애니메이션
        Handler().postDelayed({
            val progressAnimator = ObjectAnimator.ofInt(activity_join_step2_pgb, "progress", 0, 50)
            progressAnimator.setDuration(500)
            progressAnimator.start()
        }, 200)

        activity_join_step2_btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        // 화면 전환 시 애니메이션 없애는 코드
        overridePendingTransition(0, 0)
    }
}