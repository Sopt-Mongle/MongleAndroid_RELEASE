package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_theme_writing_sentence_finish.*

class ThemeWritingSentenceFinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_writing_sentence_finish)


        // 문장 보러가기
        theme_writing_sentence_finish_btn_sentence.setOnClickListener {

        }
        // 메인으로 돌아가기
        theme_writing_sentence_finish_btn_main.setOnClickListener {

        }
    }
}