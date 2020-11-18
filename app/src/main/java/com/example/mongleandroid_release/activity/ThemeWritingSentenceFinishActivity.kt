package com.example.mongleandroid_release.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.ResponseWritingSentenceData
import kotlinx.android.synthetic.main.activity_theme_writing_sentence_finish.*

class ThemeWritingSentenceFinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_writing_sentence_finish)

//        val sentenceIdx = intent.getIntExtra("param",0)

        // 문장 보러가기
        theme_writing_sentence_finish_btn_sentence.setOnClickListener {
            val intent = Intent(this,SentenceDetailNoThemeActivity::class.java)
            intent.putExtra("param", intent.getIntExtra("param",0))
            startActivity(intent)
        }
        // 메인으로 돌아가기
        theme_writing_sentence_finish_btn_main.setOnClickListener {

            // 쌓인 액티비티 스택 정리
            val intent = Intent(this,MainActivity::class.java)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }
    }
}