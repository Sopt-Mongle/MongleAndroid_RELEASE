package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import kotlinx.android.synthetic.main.activity_open_source_detail.*

class OpenSourceDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source_detail)

        tv_open_detail.text = intent.getStringExtra("title")
        tv_open_source_content.text = intent.getStringExtra("content")

        activity_open_detail_btn_back.setOnClickListener {
            finish()
        }

        sv_open_detail.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            change_visible(activity_open_detail_view_top)
            if(scrollY == 0){
                change_gone(activity_open_detail_view_top)
            }
        }
    }
}