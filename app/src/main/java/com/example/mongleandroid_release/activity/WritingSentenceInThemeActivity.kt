package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_detail_theme.*
import kotlinx.android.synthetic.main.activity_writing_sentence_in_theme.*

class WritingSentenceInThemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_sentence_in_theme)

        //테마 이름 받아오기
        activity_theme_writing_sentence_tv_themename.text = intent.getStringExtra("param")

        // X 아이콘 눌렀을 때
        activity_theme_writing_sentence_btn_out.setOnClickListener {
            finish()
        }

        // 다음 버튼 눌렀을 때 책검색 화면 넘어가기
        activity_theme_writing_sentence_btn_next.setOnClickListener {

        }

        // editText칸 안이 비어있을 때 경고
        activity_theme_writing_sentence_btn_next.setOnClickListener {
            if(activity_theme_writing_sentence_et_sentence.text.isEmpty()) {
                writing_sentence_step1_ll_warning.visibility = View.VISIBLE
                activity_theme_writing_sentence_et_sentence.setBackgroundResource(R.drawable.et_area_red)
            } else { // 다음 액티비티로 넘어가기

            }
        }
        activity_theme_writing_sentence_et_sentence.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 실시간 글자수
                val string_length = activity_theme_writing_sentence_et_sentence.text.toString()
                activity_theme_writing_sentence_tv_cnt.setText(string_length.length.toString())
            }

        })


    }
}