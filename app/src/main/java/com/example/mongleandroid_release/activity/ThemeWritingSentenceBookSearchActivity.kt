package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.showKeyboard
import kotlinx.android.synthetic.main.activity_theme_writing_sentence_book_search.*

class ThemeWritingSentenceBookSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_writing_sentence_book_search)

        // X 버튼 눌렀을 때 나가기
        theme_writing_sentence_book_search_btn_out.setOnClickListener {
            finish()
        }

        //검색창 비우기

        //포커스는 검색창에
        theme_writing_sentence_book_search_et_search.requestFocus()

        // 키보드 등장
        theme_writing_sentence_book_search_et_search.showKeyboard()

        // 글자수 카운팅 및 경고 박스
        theme_writing_sentence_book_search_et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val string_length = theme_writing_sentence_book_search_tv_cnt.text.toString()
                theme_writing_sentence_book_search_tv_cnt.setText(string_length.length.toString())
            }

        })
    }
}