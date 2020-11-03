package com.example.mongleandroid_release.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.DetailThemeAdapter
import kotlinx.android.synthetic.main.activity_detail_theme.*

class DetailThemeActivity : AppCompatActivity() {

    private lateinit var detailThemeAdapter: DetailThemeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_theme)

        img_detail_theme_activity_back_btn.setOnClickListener {
            finish()
        }

    }

}