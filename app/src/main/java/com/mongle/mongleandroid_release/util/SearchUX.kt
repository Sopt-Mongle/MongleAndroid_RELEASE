package com.mongle.mongleandroid_release.util

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.mongle.mongleandroid_release.*
import com.mongle.mongleandroid_release.activity.MainActivity

//예상 적용 지점 : 테문, 문장, 검색 탭

//1. 키보드 올라오는 시점
//<맨 처음 화면>
//requestFocus()
//showKeyboard()
//controlEditText()
//controlButton()
//
//<검색창 눌렀을 때>
//requestFocus()
//showKeyboard()
//controlEditText()
//
//2. 키보드 내려가는 시점 ( == 검색 했을 때)
//<검색 버튼 눌렀을 때>
//controlEditText()
//unshowKeyboard()
//통신 함수 호출
//
//<엔터키로 검색했을 때>
//controlEditText()
//unshowKeyboard()
//통신 함수 호출


// **키보드 제어
//키보드 올리기 내리기는 ShowKeyboard 사용
// 검색창에 초점 맞추고, 키보드 올리는 부분


// **커서 제어
// **검색창 활성화
// 전역 변수 선언해서 state 저장 및 관리하세요
fun controlEditText(editText : EditText, state : Boolean){
    if(state){ // 활성화
        editText.setBackgroundResource(R.drawable.et_area_green)
        editText.isCursorVisible = true

    }else{ // 비활성화
        editText.setBackgroundResource(R.drawable.et_area)
        editText.isCursorVisible = false
    }
}

// **x 버튼 제어
fun controlButton(editText : EditText, button: View, cntText: TextView){

    editText.setOnFocusChangeListener { _, hasFocus ->
        controlEditText(editText, true)
        if(editText.text.isNotEmpty()) {
            change_visible(button)
            button.setOnClickListener {
                editText.setText("")
            }
        } else {
            change_gone(button)
        }

        if(!hasFocus) {
            controlEditText(editText, false)
            change_gone(button)
        }
    }

    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            controlEditText(editText, true)

            //실시간 글자 수 세기
            cntText.text =
                editText.text.toString().length.toString()

            if (editText.text.isNotEmpty()) {
                change_visible(button)
                button.setOnClickListener {
                    editText.setText("")
                    editText.showKeyboard()
                }
            } else {
                change_gone(button)
            }

        }

        override fun afterTextChanged(s: Editable?) {

        }

    })

}