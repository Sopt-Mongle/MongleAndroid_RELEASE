package com.example.mongleandroid_release.activity

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.view.marginTop
import androidx.core.widget.addTextChangedListener
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_join_step2.*
import kotlinx.android.synthetic.main.activity_join_step3.*

class JoinStep3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step3)

        // 프로그래스바 애니메이션
        Handler().postDelayed({
            val progressAnimator_step3 = ObjectAnimator.ofInt(activity_join_step3_pgb, "progress", 50, 100)
            progressAnimator_step3.setDuration(500)
            progressAnimator_step3.start()
        }, 200)

        activity_join_step3_et_code1.requestFocus()
        if(activity_join_step3_et_code1.requestFocus()) {
            activity_join_step3_et_code1.background = resources.getDrawable(R.drawable.et_circle_join3_on, null)
        }

        activity_join_step3_btn_back.setOnClickListener {
            finish()
        }

        // 인증번호가 일치한다면 -> 조건 추후에 추가
        activity_join_step3_btn_next.setOnClickListener {
            val intent = Intent(this, JoinFinishActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        changeCodeBackground(activity_join_step3_et_code1)
        changeCodeBackground(activity_join_step3_et_code2)
        changeCodeBackground(activity_join_step3_et_code3)
        changeCodeBackground(activity_join_step3_et_code4)
        changeCodeBackground(activity_join_step3_et_code5)
        changeCodeBackground(activity_join_step3_et_code6)

        activity_join_step3_et_code6.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!(activity_join_step3_et_code1.text.isEmpty()) && !(activity_join_step3_et_code2.text.isEmpty()) &&
                    !(activity_join_step3_et_code3.text.isEmpty()) && !(activity_join_step3_et_code4.text.isEmpty()) &&
                    !(activity_join_step3_et_code5.text.isEmpty()) && !(activity_join_step3_et_code6.text.isEmpty())) {
                    activity_join_step3_3_pgb_out.setBackgroundResource(R.drawable.dot_circle_progresson_out)
                    activity_join_step3_3_pgb_in.setBackgroundResource(R.drawable.dot_circle_progresson_in)
                } else {
                    activity_join_step3_3_pgb_out.setBackgroundResource(R.drawable.dot_circle_progressoff_out)
                    activity_join_step3_3_pgb_in.setBackgroundResource(R.drawable.dot_circle_progressoff_in)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

    private fun changeCodeBackground(editText: EditText) {
        editText.setOnFocusChangeListener { v, hasFocus ->
            editText.background = resources.getDrawable(R.drawable.et_circle_join3_on, null)
            if(!hasFocus) {
                if(editText.text.isNotEmpty()) {
                    editText.background = resources.getDrawable(R.drawable.et_circle_join3_on, null)
                } else {
                    editText.background = resources.getDrawable(R.drawable.et_circle_join3_off, null)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // 화면 전환 시 애니메이션 없애는 코드
        overridePendingTransition(0, 0)
    }
}