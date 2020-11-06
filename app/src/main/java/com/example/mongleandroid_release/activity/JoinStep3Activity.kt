package com.example.mongleandroid_release.activity

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.example.mongleandroid_release.dialog.DialogJoinStep3
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.customEnqueue
import com.example.mongleandroid_release.network.data.request.RequestCodeData
import com.example.mongleandroid_release.network.data.request.RequestJoinData
import kotlinx.android.synthetic.main.activity_join_step2.*
import kotlinx.android.synthetic.main.activity_join_step3.*

class JoinStep3Activity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step3)

        // 프로그래스바 애니메이션
        Handler().postDelayed({
            val progressAnimator_step3 = ObjectAnimator.ofInt(activity_join_step3_pgb, "progress", 50, 100)
            progressAnimator_step3.duration = 500
            progressAnimator_step3.start()
        }, 200)

        // 인증번호 칸에 포커스
        activity_join_step3_et_code1.requestFocus()
        if(activity_join_step3_et_code1.requestFocus()) {
            activity_join_step3_et_code1.background = resources.getDrawable(R.drawable.et_circle_join3_on, null)
        }

        // 뒤로가기 버튼
        activity_join_step3_btn_back.setOnClickListener {
            finish()
        }

        // 인증번호 보냄
        val intent = intent
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val name = intent.getStringExtra("name")

        Log.d("email", email)
        requestToServer.service.requestCode(
            RequestCodeData(
                email = email
            )
        ).customEnqueue(
            onError = {
                Log.d("error", "auth 통신 실패")
            },
            onSuccess = {
                val code = it.data.authNum
                activity_join_step3_btn_next.setOnClickListener {
                    val userCode = activity_join_step3_et_code1.text.toString() +
                            activity_join_step3_et_code2.text.toString() +
                            activity_join_step3_et_code3.text.toString() +
                            activity_join_step3_et_code4.text.toString() +
                            activity_join_step3_et_code5.text.toString() +
                            activity_join_step3_et_code6.text.toString()


                    if(code == userCode) {
                        signUpSuccess()
                    }
                }
                Log.d("메일 보냄", it.data.authNum)
            }
        )

        // 이메일을 받지 못했을 때 -> dialog -> 재전송
        activity_join_step3_btn_popup.setOnClickListener {
            val dlg = DialogJoinStep3(this)
            dlg.start()
        }

        changeCodeBackground(activity_join_step3_et_code1)
        changeCodeBackground(activity_join_step3_et_code2)
        changeCodeBackground(activity_join_step3_et_code3)
        changeCodeBackground(activity_join_step3_et_code4)
        changeCodeBackground(activity_join_step3_et_code5)
        changeCodeBackground(activity_join_step3_et_code6)

        activity_join_step3_et_code6.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(activity_join_step3_et_code1.text.isNotEmpty() && activity_join_step3_et_code2.text.isNotEmpty() &&
                    activity_join_step3_et_code3.text.isNotEmpty() && activity_join_step3_et_code4.text.isNotEmpty() &&
                    activity_join_step3_et_code5.text.isNotEmpty() && activity_join_step3_et_code6.text.isNotEmpty()
                ) {
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

        // 다음 버튼
        // 인증번호 체크 & 회원가입 통신
        activity_join_step3_btn_next.setOnClickListener {



            // 회원가입 통신


        }


    }

    fun signUpSuccess() {
        val intent = intent
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val name = intent.getStringExtra("name")

        requestToServer.service.requestJoin(
            RequestJoinData(
                email = email,
                password = password,
                name = name
            )
        ).customEnqueue(
            onError = {
                Log.d("error", "singup 통신 실패")
            },
            onSuccess = {
                val intent = Intent(this, JoinFinishActivity::class.java)
                startActivity(intent)
                // 화면 전환 시 애니메이션 없애는 코드
                overridePendingTransition(0, 0)
            }
        )
    }

    private fun changeCodeBackground(editText: EditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
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