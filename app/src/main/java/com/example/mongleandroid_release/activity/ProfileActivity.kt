package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var keywordIdx : Int = 0

        activity_profile_btn1.setOnClickListener {
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 1
        }

        activity_profile_btn2.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 2
        }

        activity_profile_btn3.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 3
        }

        activity_profile_btn4.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 4
        }

        activity_profile_btn5.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 5
        }

        activity_profile_btn6.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            keywordIdx = 6
        }
    }
}