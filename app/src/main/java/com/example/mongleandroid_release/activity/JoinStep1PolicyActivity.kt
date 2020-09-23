package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_join_step1_policy.*

class JoinStep1PolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step1_policy)

        activity_join_policy_btn_back.setOnClickListener {
            finish()
        }
    }
}