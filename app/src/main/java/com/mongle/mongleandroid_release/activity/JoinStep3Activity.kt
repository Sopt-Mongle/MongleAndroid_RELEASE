package com.mongle.mongleandroid_release.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.change_gone
import com.mongle.mongleandroid_release.change_visible
import com.mongle.mongleandroid_release.dialog.DialogJoinStep3
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.customEnqueue
import com.mongle.mongleandroid_release.network.data.request.RequestCodeData
import com.mongle.mongleandroid_release.network.data.request.RequestJoinData
import com.mongle.mongleandroid_release.network.data.response.ResponseCodeData
import kotlinx.android.synthetic.main.activity_join_step3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timer


class JoinStep3Activity : AppCompatActivity() {

    val requestToServer = RequestToServer
    private var timerTask : Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step3)

        val email = intent.getStringExtra("email")

        activity_join_step3_tv_usersmail.setText("입력한 $email 으로 전송된\n메일 속 본인 확인 인증 코드를 입력해주세요.")
        // 프로그래스바 애니메이션
        Handler().postDelayed({
            val progressAnimator_step3 = ObjectAnimator.ofInt(
                activity_join_step3_pgb,
                "progress",
                50,
                100
            )
            progressAnimator_step3.duration = 500
            progressAnimator_step3.start()
        }, 200)

        // 밖에 누르면 키보드 없어짐
        activity_join_step3_cl_in.setOnClickListener {
            hideKeyboard()
        }

        // 인증번호 칸에 포커스
        activity_join_step3_et_code1.requestFocus()

        // 옆칸으로 이동
        activity_join_step3_et_code1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length == 1) {
                    activity_join_step3_et_code2.requestFocus()
                }
            }

        })

        activity_join_step3_et_code2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length == 1) {
                    activity_join_step3_et_code3.requestFocus()
                }
            }

        })

        activity_join_step3_et_code3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length == 1) {
                    activity_join_step3_et_code4.requestFocus()
                }
            }

        })

        activity_join_step3_et_code4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length == 1) {
                    activity_join_step3_et_code5.requestFocus()
                }
            }

        })

        activity_join_step3_et_code5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length == 1) {
                    activity_join_step3_et_code6.requestFocus()
                }
            }

        })

        if(activity_join_step3_et_code1.requestFocus()) {
            activity_join_step3_et_code1.background = resources.getDrawable(
                R.drawable.et_circle_join3_on,
                null
            )
            // 뷰 줄어듦
            Handler().postDelayed({
                hideEmpty()
            }, 400)
        }

        // 뒤로가기 버튼
        activity_join_step3_btn_back.setOnClickListener {
            finish()
        }

        // 3단계 넘어오자마자 인증번호 보냄 & 타이머 가동
        startTimer()
        authUser()

        // 이메일을 받지 못했을 때 -> dialog -> 재전송
        activity_join_step3_btn_popup.setOnClickListener {
            val dlg = DialogJoinStep3(this)
            dlg.dialog_spammail()
            dlg.setOnOKClickedListener { content ->
                if(content == "재전송") {
                    repeatCodeToast()
                    startTimer()
                    authUser()
                }
            }
            showEmpty()
        }

        // 재전송 버튼
        activity_join_step3_btn_repeat.setOnClickListener {
            repeatCodeToast()
            startTimer()
            authUser()

            hideKeyboard()
            showEmpty()
        }

        changeCodeBackground(activity_join_step3_et_code1)
        changeCodeBackground(activity_join_step3_et_code2)
        changeCodeBackground(activity_join_step3_et_code3)
        changeCodeBackground(activity_join_step3_et_code4)
        changeCodeBackground(activity_join_step3_et_code5)
        changeCodeBackground(activity_join_step3_et_code6)

        etTextChangedListener(activity_join_step3_et_code1)
        etTextChangedListener(activity_join_step3_et_code2)
        etTextChangedListener(activity_join_step3_et_code3)
        etTextChangedListener(activity_join_step3_et_code4)
        etTextChangedListener(activity_join_step3_et_code5)
        etTextChangedListener(activity_join_step3_et_code6)

    }

    private fun etTextChangedListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (activity_join_step3_et_code1.text.isNotEmpty() && activity_join_step3_et_code2.text.isNotEmpty() &&
                    activity_join_step3_et_code3.text.isNotEmpty() && activity_join_step3_et_code4.text.isNotEmpty() &&
                    activity_join_step3_et_code5.text.isNotEmpty() && activity_join_step3_et_code6.text.isNotEmpty()
                ) {
                    hideKeyboard()
                    editText.isCursorVisible = false
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
                changeCodeBackGray()
                editText.isCursorVisible = true
                editText.background = resources.getDrawable(R.drawable.et_circle_join3_on, null)
            }
        })
    }

    private fun hideKeyboard() {
        (applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity_join_step3_et_code1.windowToken, 0)
        (applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity_join_step3_et_code2.windowToken, 0)
        (applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity_join_step3_et_code3.windowToken, 0)
        (applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity_join_step3_et_code4.windowToken, 0)
        (applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity_join_step3_et_code5.windowToken, 0)
        (applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity_join_step3_et_code6.windowToken, 0)

        showEmpty()

    }

    private fun repeatCodeToast() {
        val customToast = layoutInflater.inflate(R.layout.toast_code_repeat, null)
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
        toast.view = customToast
        toast.show()
    }

    private fun authUser() {
        // 인증번호 보냄
        val intent = intent
        val email = intent.getStringExtra("email")

        Log.d("email", email)
        requestToServer.service.requestCode(
            RequestCodeData(
                email = email
            )
        ).enqueue(object : Callback<ResponseCodeData> {
            override fun onFailure(call: Call<ResponseCodeData>, t: Throwable) {
                Log.d("error", "auth 통신 실패")
            }

            override fun onResponse(
                call: Call<ResponseCodeData>,
                response: Response<ResponseCodeData>
            ) {
                if (response.isSuccessful) {
                    val code = response.body()!!.data.authNum
                    activity_join_step3_btn_next.setOnClickListener {
                        val userCode = activity_join_step3_et_code1.text.toString() +
                                activity_join_step3_et_code2.text.toString() +
                                activity_join_step3_et_code3.text.toString() +
                                activity_join_step3_et_code4.text.toString() +
                                activity_join_step3_et_code5.text.toString() +
                                activity_join_step3_et_code6.text.toString()

                        if (code == userCode) {
                            signUpSuccess()
                        } else {
                            hideKeyboard()
                            showErrorMsg()
                        }
                    }
                }

            }

        })

    }

    private fun startTimer() {

        resetTimer()

        var minute = 5
        var second = 0

        timerTask = timer(period = 1000) {

            runOnUiThread {
                var timer = String.format("%02d:%02d", minute, second)
                activity_join_step3_tv_timer.text = timer
                Log.d("타이머", "$minute : $second")
            }

            if(second == 0 && minute == 0) {
                //타이머 종료
                runOnUiThread {
                    activity_join_step3_tv_timer.text = "00:00"
                    showTimeoutDialog()
                }
                cancel()
            }
            if(second == 0) {
                minute--
                second = 60
            }
            second--
        }

    }

    private fun showTimeoutDialog() {
        // 타이머 5분 지났을 때
        if(activity_join_step3_tv_timer.text == "00:00") {
            var dlg = DialogJoinStep3(this)
            dlg.dialog_timeout()
            dlg.setOnOKClickedListener { content ->
                if(content == "재전송") {
                    repeatCodeToast()
                    startTimer()
                    authUser()
                }
            }
            showEmpty()
        }
    }

    private fun resetTimer() {
        timerTask?.cancel()

        var minute = 5
        var second = 0
        var timer = String.format("\n%02d:%02d", minute, second)
        activity_join_step3_tv_timer.text = timer

    }

    private fun signUpSuccess() {

        timerTask?.cancel()

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
                SharedPreferenceController.setMail(this, email)
                SharedPreferenceController.setPasswd(this, password)
                SharedPreferenceController.setName(this, name)
                SharedPreferenceController.setAccessToken(this, it.data.accessToken)
                val intent = Intent(this, JoinFinishActivity::class.java)
                startActivity(intent)
                // 화면 전환 시 애니메이션 없애는 코드
                overridePendingTransition(0, 0)
            }
        )
    }

    private fun changeCodeBackground(editText: EditText) {
        editText.setOnClickListener {
            hideEmpty()
        }

        editText.setOnFocusChangeListener { _, hasFocus ->

            changeCodeBackGray()
            editText.isCursorVisible = true
            editText.background = resources.getDrawable(R.drawable.et_circle_join3_on, null)
            if(!hasFocus) {
                editText.clearFocus()
                if(editText.text.isNotEmpty()) {
                    editText.background = resources.getDrawable(R.drawable.et_circle_join3_on, null)
                } else {
                    editText.background = resources.getDrawable(
                        R.drawable.et_circle_join3_off,
                        null
                    )
                }
            }
        }
    }

    private fun showErrorMsg() {
        activity_join_step3_et_code1.background = resources.getDrawable(
            R.drawable.et_circle_join3_red,
            null
        )
        activity_join_step3_et_code2.background = resources.getDrawable(
            R.drawable.et_circle_join3_red,
            null
        )
        activity_join_step3_et_code3.background = resources.getDrawable(
            R.drawable.et_circle_join3_red,
            null
        )
        activity_join_step3_et_code4.background = resources.getDrawable(
            R.drawable.et_circle_join3_red,
            null
        )
        activity_join_step3_et_code5.background = resources.getDrawable(
            R.drawable.et_circle_join3_red,
            null
        )
        activity_join_step3_et_code6.background = resources.getDrawable(
            R.drawable.et_circle_join3_red,
            null
        )

        change_visible(activity_join_step3_ic_error)
        change_visible(activity_join_step3_tv_error)
        
    }

    private fun changeCodeBackGray() {
        activity_join_step3_et_code1.background = resources.getDrawable(
            R.drawable.et_circle_join3_off,
            null
        )
        activity_join_step3_et_code2.background = resources.getDrawable(
            R.drawable.et_circle_join3_off,
            null
        )
        activity_join_step3_et_code3.background = resources.getDrawable(
            R.drawable.et_circle_join3_off,
            null
        )
        activity_join_step3_et_code4.background = resources.getDrawable(
            R.drawable.et_circle_join3_off,
            null
        )
        activity_join_step3_et_code5.background = resources.getDrawable(
            R.drawable.et_circle_join3_off,
            null
        )
        activity_join_step3_et_code6.background = resources.getDrawable(
            R.drawable.et_circle_join3_off,
            null
        )

        change_gone(activity_join_step3_ic_error)
        change_gone(activity_join_step3_tv_error)
    }


    private fun hideEmpty() {
        view_empty1.visibility = GONE
        view_empty2.visibility = GONE
        view_empty3.visibility = GONE
        view_empty4.visibility = GONE
        view_empty5.visibility = GONE
    }

    private fun showEmpty() {
        view_empty1.visibility = VISIBLE
        view_empty2.visibility = VISIBLE
        view_empty3.visibility = VISIBLE
        view_empty4.visibility = VISIBLE
        view_empty5.visibility = VISIBLE
    }

    override fun onPause() {
        super.onPause()
        // 화면 전환 시 애니메이션 없애는 코드
        overridePendingTransition(0, 0)
    }
}