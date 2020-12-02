package com.mongle.mongleandroid_release.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.change_gone
import kotlinx.android.synthetic.main.activity_writing_sentence_in_theme.*

class ThemeWritingSentenceActivity : AppCompatActivity() {

    companion object{
        var activity : Activity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_sentence_in_theme)

        activity = this

        //테마 이름 받아오기
        activity_theme_writing_sentence_tv_themename.text = intent.getStringExtra("param")
        //테마 인덱스 받아오기
        val themeIdx_from_detail = intent.getIntExtra("themeIdx", 0)
        val themeIdx_from_make = intent.getIntExtra("param2", 0)
        if(themeIdx_from_detail != 0) {
            DetailThemeActivity.writingSentenceInThemeData.themeIdx = themeIdx_from_detail
        } else if(themeIdx_from_make != 0) {
            DetailThemeActivity.writingSentenceInThemeData.themeIdx = themeIdx_from_make
        }


        // X 아이콘 눌렀을 때
        activity_theme_writing_sentence_btn_out.setOnClickListener {
            finish()
        }

        // editText칸 안이 비어있을 때 경고
        activity_theme_writing_sentence_btn_next.setOnClickListener {
            if(activity_theme_writing_sentence_et_sentence.text.isEmpty()) {
                writing_sentence_step1_ll_warning.visibility = View.VISIBLE
                change_gone(activity_theme_writing_sentence_tv_cnt) // 글자수 세는 카운팅 안보이게하기
                change_gone(activity_theme_writing_sentence_tv_cnt2) // 글자수 카운팅 안보이게하기
                activity_theme_writing_sentence_et_sentence.setBackgroundResource(R.drawable.et_area_red)
            } else { // 다음 액티비티로 넘어가기
                Log.d("다음 버튼 누름!!!!!!!!!1", "다음다음다음다음")
                val intent = Intent(this,ThemeWritingSentenceBookActivity::class.java)
                DetailThemeActivity.writingSentenceInThemeData.sentence = activity_theme_writing_sentence_et_sentence.text.toString()
//                intent.putExtra("sentence", activity_theme_writing_sentence_et_sentence.text.toString())
                startActivity(intent)
            }
        }
        // 문장 글자수 카운팅
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