package com.example.mongleandroid_release.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_join_step1.*


class JoinStep1Activity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step1)

        activity_join_step1_btn_next.setOnClickListener {
            val intent = Intent(this, JoinStep2Activity::class.java)
            startActivity(intent)
        }


    }
}