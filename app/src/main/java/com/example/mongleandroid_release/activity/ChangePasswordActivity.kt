package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import com.example.mongleandroid_release.dialog.DialogChangePassword
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.customEnqueue
import com.example.mongleandroid_release.network.data.request.RequestChangePasswordData
import com.example.mongleandroid_release.network.data.request.RequestDuplicateData
import com.example.mongleandroid_release.network.data.response.ResponseDuplicateData
import kotlinx.android.synthetic.main.activity_account_setting.*
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_join_step2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class ChangePasswordActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        //뒤로 가기 아이콘 눌렀을 때 화면 전환
        img_back_change_password.setOnClickListener {
            val intent = Intent(this, AccountSettingActivity::class.java)
            startActivity(intent)
        }

        // 다음 버튼 눌렀을 때 비어있는 칸 경고문구 설정
        tv_complete_change_password.setOnClickListener {
            if(et_now_password.text.isEmpty()) {
                et_now_password.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(img_warning_now_password)
                img_warning_now_password.setImageResource(R.drawable.ic_warning)
                change_visible(tv_now_password_empty)
                change_gone(tv_warning_now_password_number)
                change_gone(tv_not_now_password)
            } else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", et_now_password.text.toString())) {
                change_visible(img_warning_now_password)
                img_warning_now_password.setImageResource(R.drawable.ic_warning)
                change_gone(tv_now_password_empty)
                change_gone(tv_not_now_password)
                change_visible(tv_warning_now_password_number)
            } else if(et_new_password.text.isEmpty()) {
                et_new_password.background = resources.getDrawable(R.drawable.et_area_red, null)
                et_new_password_confirm.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(img_warning_new_password)
                img_warning_new_password.setImageResource(R.drawable.ic_warning)
                change_visible(tv_warning_new_password_empty)
                change_gone(tv_warning_new_password_onemore)
                change_gone(tv_warning_new_password_number)
                change_gone(tv_warning_new_password_same)
            } else if(et_new_password_confirm.text.isEmpty()) {
                et_new_password_confirm.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(img_warning_new_password)
                img_warning_new_password.setImageResource(R.drawable.ic_warning)
                change_visible(tv_warning_new_password_onemore)
                change_gone(tv_warning_new_password_empty)
                change_gone(tv_warning_new_password_number)
                change_gone(tv_warning_new_password_same)
            } else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", et_new_password.text.toString())) {
                change_visible(img_warning_new_password)
                img_warning_new_password.setImageResource(R.drawable.ic_warning)
                change_gone(tv_warning_new_password_onemore)
                change_gone(tv_warning_new_password_empty)
                change_gone(tv_warning_new_password_same)
                change_visible(tv_warning_new_password_number)
            } else if(et_new_password_confirm.text.toString() != et_new_password.text.toString()) {
                change_visible(img_warning_new_password)
                img_warning_new_password.setImageResource(R.drawable.ic_warning)
                change_visible(tv_warning_new_password_same)
                change_gone(tv_warning_new_password_onemore)
                change_gone(tv_warning_new_password_empty)
                change_gone(tv_warning_new_password_number)
            } else {
                //완료 버튼을 눌렀을 때 다이얼로그 띄우기
                val dlg = DialogChangePassword(this)
                dlg.start()

                //다이얼로그가 띄워진 사이에 변경된 비밀번호가 서버로 전송됨.
                changePasswordSuccess()

            }

        }

        //현재 비밀번호 창 focus 시에 테두리 색 변경
        et_now_password.setOnFocusChangeListener { _, hasFocus ->
            et_now_password.background = resources.getDrawable(R.drawable.et_area_green, null)
            change_gone(img_warning_new_password)
            change_gone(tv_not_now_password)
            change_gone(tv_warning_now_password_number)
            change_gone(tv_now_password_empty)

            //edittext 버튼 지우는 버튼
            et_now_password.clearText(img_erase_now_password)

            if(!hasFocus) {
                et_now_password.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(img_erase_now_password)

                // 현재 비밀번호 일치체크
//                checkNowPassword()

            }
        }

        //새 비밀번호 창 focus 시에 테두리 색 변경
        et_new_password.setOnFocusChangeListener { _, hasFocus ->
            et_new_password.background = resources.getDrawable(R.drawable.et_area_green, null)
            change_gone(img_warning_new_password)
            change_gone(tv_warning_new_password_same)
            change_gone(tv_warning_new_password_number)
            change_gone(tv_warning_new_password_empty)
            change_gone(tv_warning_new_password_onemore)

            //edittext 버튼 지우는 버튼
            et_new_password.clearText(img_erase_new_password)
            et_new_password_confirm.clearText(img_erase_new_password)

            if(!hasFocus) {
                et_new_password.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(img_erase_new_password)

                et_new_password_confirm.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(img_erase_new_password_onemore)
            }
        }

    }

    private fun checkNowPassword() {

        if(et_now_password.text.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(et_now_password.text.toString()).matches()) {
            change_visible(img_warning_now_password)
            change_visible(tv_warning_now_password_number)
        } else {
            change_gone(img_warning_now_password)
            change_gone(tv_warning_now_password_number)
        }

        // 현재 비밀번호 일치체크
//        if(et_now_password.text.isNotEmpty() && response.body()!!.data.duplicate == "password") {
//            activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_red, null)
//            change_visible(activity_join_step2_img_email_warning)
//            change_visible(activity_join_step2_tv_exist_email)
//        }
    }

    private fun changePasswordSuccess() {
        val password = intent.getStringExtra("password")

        requestToServer.service.requestChangePassword(
            SharedPreferenceController.getAccessToken(this),
            RequestChangePasswordData(
                password = password
            )
        ).customEnqueue(
            onError = {
                Log.e("error", "changePassword 통신 실패")
            },
            onSuccess = {
                Log.d("success", "changePassword 통신 성공")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
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