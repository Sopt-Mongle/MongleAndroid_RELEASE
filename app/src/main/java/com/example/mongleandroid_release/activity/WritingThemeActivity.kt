package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.unshowKeyboard
import com.example.mongleandroid_release.util.DialogMakethemeCheck
import kotlinx.android.synthetic.main.writing_theme_finish.*
import kotlinx.android.synthetic.main.writing_theme_step1.*

class WritingThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.writing_theme_step1)

        // MARK :: PROPERTIES

        // MARK :: XML
        // 뒤로가기 버튼
        writing_theme_step1_btn_out.setOnClickListener{
            Toast.makeText(this, "메인화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show()
            finish() //onDestroy() 호출되어 아예 메모리에 액티비티 삭제!
        }
        // 테마 이름 입력창
        // 텍스트 변화 감지를 위해서 addTextChangedListener에 TextWatcher 객체 생성해서 넣어줌
        writing_theme_step1_et_sentence.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // writing_theme_step1_et_sentence 빨간 박스 해제
                // 테마 이름 글자 수 카운팅
            }

        })

        // 테마 사진 리스트
//        //rv 동작
//        writingSentenceBookSearchAdapter = WritingSentenceBookSearchAdapter(this)
//        activity_writing_sentence_book_search_rv_after.adapter = writingSentenceBookSearchAdapter
//        activity_writing_sentence_book_search_rv_after.addItemDecoration(ItemDecoration())

        // 테마 등록 버튼
        writing_theme_step1_next.setOnClickListener {
            Toast.makeText(this, "등록 팝업 등장.", Toast.LENGTH_SHORT).show()

            val dlg = DialogMakethemeCheck(this)
            dlg.start()
            dlg.setOnOKClickedListener{
            }
        }






        // MARK :: METHOD


    }
}