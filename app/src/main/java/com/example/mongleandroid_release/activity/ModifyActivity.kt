package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.app.ActivityCompat
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.dialog.DialogDeleteSentence
import com.example.mongleandroid_release.dialog.DialogModifySentence
import kotlinx.android.synthetic.main.activity_modify.*

class ModifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        // 뒤로 가기 버튼 눌렀을 때
        img_back_sentence_modify.setOnClickListener {
            finish()
        }

        //수정하기 버튼 눌렀을 때
        tv_modify_done.setOnClickListener {

            //다이얼로그 띄우고 확인 누르면 종료 시키기
            val dlg = DialogModifySentence(this)
            dlg.start()
            dlg.setOnClickListener { content ->
                if(content == "확인") {
                    finish() //액티비티 종료하고
                }
            }

            //수정된 내용 반영하기
//            et_sentence_modify.toString()
        }

        // 문장 글자수 카운팅
        et_sentence_modify.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 실시간 글자수
                val string_length = et_sentence_modify.text.toString()
                tv_sentence_modify_words.setText(string_length.length.toString())
            }

        })
    }
}