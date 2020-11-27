package com.mongle.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mongle.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_join_finish.*

class JoinFinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_finish)

        activity_join_finish_tv_gomain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onPause() {
        super.onPause()
        // 화면 전환 시 애니메이션 없애는 코드
        overridePendingTransition(0, 0)
    }
}