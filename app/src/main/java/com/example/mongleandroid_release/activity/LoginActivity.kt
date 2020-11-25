package com.example.mongleandroid_release.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.mongleandroid_release.dialog.DialogLogin
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import com.example.mongleandroid_release.dialog.DialogFind
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.customEnqueue
import com.example.mongleandroid_release.network.data.request.RequestLoginData
import kotlinx.android.synthetic.main.activity_join_step3.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        activity_login_tv_join.setOnClickListener {
            val intent = Intent(this, JoinStep1Activity::class.java)
            startActivity(intent)
        }

        activity_login_tv_findid.setOnClickListener {
            val dlg = DialogFind(this)
            dlg.start()
            dlg.setOnClickListener { content ->
                if(content == "메일") {
                    val email = Intent(Intent.ACTION_SEND)
                    email.type = "plain/text"
                    val address =
                        arrayOf("mongle.official@gmail.com")
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, "[문의]")
                    email.putExtra(Intent.EXTRA_TEXT, "*몽글팀이 빠르게 처리할 수 있게 메일 제목에 간단하게 어떤 문의인지 적어주세요!\n" +
                            "\n" +
                            "1. 문의 유형(문의/신고/버그제보/기타) : \n" +
                            "2. 회원 아이디 (필요시 기입) : \n" +
                            "3. 문의 내용 :")
                    startActivity(email)
                }
            }
        }

        activity_login_tv_findpass.setOnClickListener {
            val dlg = DialogFind(this)
            dlg.start()
            dlg.setOnClickListener { content ->
                if(content == "메일") {
                    val email = Intent(Intent.ACTION_SEND)
                    email.type = "plain/text"
                    val address =
                        arrayOf("mongle.official@gmail.com")
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, "[문의]")
                    email.putExtra(Intent.EXTRA_TEXT, "*몽글팀이 빠르게 처리할 수 있게 메일 제목에 간단하게 어떤 문의인지 적어주세요!\n" +
                            "\n" +
                            "1. 문의 유형(문의/신고/버그제보/기타) : \n" +
                            "2. 회원 아이디 (필요시 기입) : \n" +
                            "3. 문의 내용 :")
                    startActivity(email)
                }
            }
        }

        activity_login_btn_login.setOnClickListener {
            requestToServer.service.requestLogin(
                RequestLoginData(
                    email = activity_login_et_email.text.toString(),
                    password = activity_login_et_pass.text.toString()
                )
            ).customEnqueue(
                onError = {
                    Log.d("error", "에러")
                    val dlg = DialogLogin(this)
                    dlg.start()
                },
                onSuccess = {
                    if(it.status == 200) {
                        Log.e("토큰", " $it")
                        SharedPreferenceController.setAccessToken(this, it.data.accessToken)
                        SharedPreferenceController.setMail(this, activity_login_et_email.text.toString())
                        SharedPreferenceController.setPasswd(this, activity_login_et_pass.text.toString())

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else if (it.status == 400) {
                        val dlg = DialogLogin(this)
                        dlg.start()
                    }
                }
            )


        }



        // email 입력창
        activity_login_et_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                activity_login_et_email.clearText(activity_login_btn_email_erase)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        // passwd 입력창창
        activity_login_et_pass.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                activity_login_et_pass.clearText(activity_login_btn_pass_erase)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        activity_login_et_email.setOnFocusChangeListener { _, hasFocus ->
            activity_login_et_email.background = resources.getDrawable(R.drawable.et_area_green, null)
            if(activity_login_et_email.text.isNotEmpty()) {
                activity_login_et_email.clearText(activity_login_btn_email_erase)
            }

            if(!hasFocus) {
                activity_login_et_email.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(activity_login_btn_email_erase)
            }
        }

        activity_login_et_pass.setOnFocusChangeListener { _, hasFocus ->
            activity_login_et_pass.background = resources.getDrawable(R.drawable.et_area_green, null)
            if(activity_login_et_pass.text.isNotEmpty()) {
                activity_login_et_pass.clearText(activity_login_btn_pass_erase)
            }


            if(!hasFocus) {
                activity_login_et_pass.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(activity_login_btn_pass_erase)
            }
        }

    }

    // edittext 지우는 x버튼
    private fun EditText.clearText(button : ImageView) {
        change_visible(button)
        button.setOnClickListener {
            this.setText("")
        }
    }
}