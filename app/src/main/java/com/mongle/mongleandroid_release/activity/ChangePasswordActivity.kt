package com.mongle.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.change_gone
import com.mongle.mongleandroid_release.change_visible
import com.mongle.mongleandroid_release.dialog.DialogChangePassword
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.customEnqueue
import com.mongle.mongleandroid_release.network.data.request.RequestChangePasswordData
import kotlinx.android.synthetic.main.activity_change_password.*
import java.util.regex.Pattern

class ChangePasswordActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        //뒤로 가기 아이콘 눌렀을 때 화면 전환
        img_back_change_password.setOnClickListener {
            finish()
        }

        // 다음 버튼 눌렀을 때 비어있는 칸 경고문구 설정
        tv_complete_change_password.setOnClickListener {
            allGone()
            if(et_now_password.text.isEmpty()) {
                et_now_password.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(img_now_password_warning)
                change_visible(tv_now_password_warning)
                change_gone(tv_now_password_valid)
                change_gone(tv_now_password_nomatch)
            } else if(SharedPreferenceController.getPasswd(this) != et_now_password.text.toString()) {
                change_visible(img_now_password_warning)
                change_visible(tv_now_password_nomatch)
            }

            if(et_new_password.text.isEmpty()) {
                et_new_password.background = resources.getDrawable(R.drawable.et_area_red, null)
                et_new_password_check.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(img_new_password_warning)
                change_visible(tv_new_password_warning)
                img_new_password_warning.setImageResource(R.drawable.ic_warning)
                change_gone(tv_new_password_check_warning)
                change_gone(tv_new_password_valid)
                change_gone(tv_new_password_nomatch)
            } else if(et_new_password_check.text.isEmpty()) {
                et_new_password_check.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(img_new_password_warning)
                img_new_password_warning.setImageResource(R.drawable.ic_warning)
                change_visible(tv_new_password_check_warning)
                change_gone(tv_new_password_warning)
                change_gone(tv_new_password_valid)
                change_gone(tv_new_password_nomatch)
            } else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", et_new_password.text.toString())) {
                change_visible(img_new_password_warning)
                img_new_password_warning.setImageResource(R.drawable.ic_warning)
                change_gone(tv_new_password_check_warning)
                change_gone(tv_new_password_warning)
                change_gone(tv_new_password_nomatch)
                change_visible(tv_new_password_valid)
            } else if(et_new_password_check.text.toString() != et_new_password.text.toString()) {
                change_visible(img_new_password_warning)
                img_new_password_warning.setImageResource(R.drawable.ic_warning)
                change_visible(tv_new_password_nomatch)
                change_gone(tv_new_password_check_warning)
                change_gone(tv_new_password_warning)
                change_gone(tv_new_password_valid)
            } else {
                changePasswordSuccess()
            }

        }

        //현재 비밀번호 창 focus 시에 테두리 색 변경
        et_now_password.setOnFocusChangeListener { _, hasFocus ->
            et_now_password.background = resources.getDrawable(R.drawable.et_area_green, null)
            allGone()
            //edittext 버튼 지우는 버튼
            et_now_password.clearText(img_now_password_erase)

            if(!hasFocus) {
                et_now_password.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(img_now_password_erase)

            }
        }

        et_now_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                et_now_password.background = resources.getDrawable(R.drawable.et_area_green, null)
                change_gone(tv_now_password_nomatch)
                change_gone(tv_now_password_valid)
                change_gone(tv_now_password_warning)

                // 비밀번호 유효성 검사
                if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", et_now_password.text.toString())) {
                    change_visible(img_now_password_warning)
                    change_visible(tv_now_password_valid)
                } else {
                    change_gone(img_now_password_warning)
                    change_gone(tv_now_password_valid)
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        //새 비밀번호 창 focus 시에 테두리 색 변경
        et_new_password.setOnFocusChangeListener { _, hasFocus ->
            et_new_password.background = resources.getDrawable(R.drawable.et_area_green, null)
            allGone()
            et_new_password_check.background = resources.getDrawable(R.drawable.et_area, null)

            // 현재 비밀번호 확인
            if(SharedPreferenceController.getPasswd(this) != et_now_password.text.toString()) {
                change_visible(img_now_password_warning)
                change_visible(tv_now_password_nomatch)
            }

            //edittext 버튼 지우는 버튼
            et_new_password.clearText(img_new_password_erase)

            if(!hasFocus) {
                et_new_password.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(img_new_password_erase)
            }
        }

        et_new_password.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 비밀번호 유효성 검사
                if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", et_new_password.text.toString())) {
                    change_visible(img_new_password_warning)
                    change_visible(tv_new_password_valid)
                } else {
                    change_gone(img_new_password_warning)
                    change_gone(tv_new_password_valid)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        // 새 비밀번호 확인 창 포커스
        et_new_password_check.setOnFocusChangeListener { _, hasFocus ->
            et_new_password_check.background = resources.getDrawable(R.drawable.et_area_green, null)
            allGone()
            et_now_password.background = resources.getDrawable(R.drawable.et_area, null)
            et_new_password.background = resources.getDrawable(R.drawable.et_area, null)

            // clear text
            et_new_password_check.clearText(img_new_password_check_erase)

            if(!hasFocus) {
                et_new_password_check.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(img_new_password_check_erase)
            }
        }

        et_new_password_check.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_new_password.text.toString() != et_new_password_check.text.toString()) {
                    change_visible(img_new_password_warning)
                    change_visible(tv_new_password_nomatch)
                    change_gone(img_new_password_match)
                    change_gone(tv_new_password_match)
                } else {
                    change_gone(img_new_password_warning)
                    change_gone(tv_new_password_nomatch)
                    change_visible(img_new_password_match)
                    change_visible(tv_new_password_match)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    private fun allGone() {
        change_gone(img_now_password_warning)
        change_gone(img_new_password_warning)
        change_gone(tv_new_password_nomatch)
        change_gone(tv_now_password_valid)
        change_gone(tv_now_password_nomatch)
        change_gone(tv_now_password_warning)
        change_gone(tv_new_password_valid)
        change_gone(tv_new_password_warning)
        change_gone(tv_new_password_check_warning)
        change_gone(img_new_password_match)
        change_gone(tv_new_password_match)
    }

    private fun changePasswordSuccess() {

        requestToServer.service.requestChangePassword(
            token = this.let{ SharedPreferenceController.getAccessToken(it) },
                body = RequestChangePasswordData(
                        password = et_new_password.text.toString()
                )
        ).customEnqueue(
            onError = {
                Log.e("error", "changePassword 통신 실패")
            },
            onSuccess = {
                Log.d("success", "changePassword 통신 성공")

                val dlg = DialogChangePassword(this)
                dlg.start()
                dlg.setOnClickListener { content ->
                    if(content == "재로그인") {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        ActivityCompat.finishAffinity(this)
                    }
                }
            }
        )
    }

    // edittext 지우는 x버튼
    private fun EditText.clearText(button : ImageView) {
        change_visible(button)
        button.setOnClickListener {
            this.setText("")
        }
    }
}