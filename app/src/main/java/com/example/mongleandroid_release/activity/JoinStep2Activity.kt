package com.example.mongleandroid_release.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.customEnqueue
import com.example.mongleandroid_release.network.data.request.RequestDuplicateData
import com.example.mongleandroid_release.network.data.response.ResponseDuplicateData
import kotlinx.android.synthetic.main.activity_join_step2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class JoinStep2Activity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step2)

        // 프로그래스바 애니메이션
        Handler().postDelayed({
            val progressAnimator_step2 = ObjectAnimator.ofInt(activity_join_step2_pgb, "progress", 0, 50)
            progressAnimator_step2.duration = 500
            progressAnimator_step2.start()
        }, 200)

        activity_join_step2_sv.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            change_visible(activity_join_step2_top_blur)

            if(scrollY == 0) {
                change_gone(activity_join_step2_top_blur)
            }
        }


        // 다음 버튼 눌렀을 때 비어있는 칸 경고문구 설정
        activity_join_step2_btn_next.setOnClickListener {
            if(activity_join_step2_et_email.text.isEmpty()) {
                activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_red, null)
                activity_join_step2_img_email_warning.setImageResource(R.drawable.ic_warning)
                change_visible(activity_join_step2_img_email_warning)
                change_visible(activity_join_step2_tv_email_warning)
                change_gone(activity_join_step2_tv_email_valid_warning)
            } else if(activity_join_step2_et_pass.text.isEmpty()) {
                activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area_red, null)
                activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(activity_join_step2_img_pass_warning)
                activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                change_visible(activity_join_step2_tv_pass_warning)
                change_gone(activity_join_step2_tv_pass_nomatch)
            } else if(activity_join_step2_et_passcheck.text.isEmpty()) {
                activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(activity_join_step2_img_pass_warning)
                activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                change_visible(activity_join_step2_tv_passcheck_warning)
                change_gone(activity_join_step2_tv_pass_nomatch)
                change_gone(activity_join_step2_tv_pass_valid)
            } else if(activity_join_step2_et_nickname.text.isEmpty()) {
                activity_join_step2_et_nickname.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(activity_join_step2_img_nickname_warning)
                change_visible(activity_join_step2_tv_nickname_warning)
            } else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", activity_join_step2_et_pass.text.toString())) {
                activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                change_gone(activity_join_step2_tv_pass_match)
                change_gone(activity_join_step2_tv_pass_nomatch)
                change_visible(activity_join_step2_img_pass_warning)
                change_visible(activity_join_step2_tv_pass_valid)
            } else if(activity_join_step2_et_pass.text.toString() != activity_join_step2_et_passcheck.text.toString()) {
                change_visible(activity_join_step2_img_pass_warning)
                change_visible(activity_join_step2_tv_pass_nomatch)
                activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                change_gone(activity_join_step2_tv_pass_match)
            } else {

                val intent = Intent(this, JoinStep3Activity::class.java)

                // 전체 중복체크
                requestToServer.service.requestDuplicate(
                    RequestDuplicateData(
                        email = activity_join_step2_et_email.text.toString(),
                        name = activity_join_step2_et_nickname.text.toString()
                    )
                ).enqueue(object : Callback<ResponseDuplicateData> {
                    override fun onFailure(call: Call<ResponseDuplicateData>, t: Throwable) {
                        Log.d("error", "duplicate / $t")
                    }

                    override fun onResponse(
                        call: Call<ResponseDuplicateData>,
                        response: Response<ResponseDuplicateData>
                    ) {
                        if(response.isSuccessful) {
                            Log.d("중복", response.body().toString())
                            if(response.body()!!.data!!.duplicate == "email") {
                                activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_red, null)
                                activity_join_step2_img_email_warning.setImageResource(R.drawable.ic_warning)
                                change_visible(activity_join_step2_img_email_warning)
                                change_visible(activity_join_step2_tv_exist_email)
                            } else if(response.body()!!.data!!.duplicate == "name") {
                                activity_join_step2_et_nickname.background = resources.getDrawable(R.drawable.et_area_red, null)
                                change_visible(activity_join_step2_img_nickname_warning)
                                change_visible(activity_join_step2_tv_exist_nickname)
                            } else {
                                // 중복 없으면 다음으로 정보와 함께 이동
                                intent.putExtra("email", activity_join_step2_et_email.text.toString())
                                intent.putExtra("password", activity_join_step2_et_pass.text.toString())
                                intent.putExtra("name", activity_join_step2_et_nickname.text.toString())
                                startActivity(intent)
                                // 화면 전환 시 애니메이션 없애는 코드
                                overridePendingTransition(0, 0)
                            }
                        }
                    }
                })



            }

        }

        // email 입력창 리스너
        activity_join_step2_et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                forProgressOn()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_green, null)
                change_gone(activity_join_step2_img_email_warning)
                change_gone(activity_join_step2_tv_email_warning)
                change_gone(activity_join_step2_tv_email_valid_warning)
                change_gone(activity_join_step2_tv_exist_email)

                if(activity_join_step2_et_email.text.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(activity_join_step2_et_email.text.toString()).matches()) {
                    activity_join_step2_img_email_warning.setImageResource(R.drawable.ic_warning)
                    change_visible(activity_join_step2_img_email_warning)
                    change_visible(activity_join_step2_tv_email_valid_warning)
                } else {
                    change_gone(activity_join_step2_img_email_warning)
                    change_gone(activity_join_step2_tv_email_valid_warning)
                }
            }
        })

        // email 입력창 focus -> warning 문구 해제, et_green 설정
        activity_join_step2_et_email.setOnFocusChangeListener { _, hasFocus ->
            activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_green, null)
            change_gone(activity_join_step2_img_email_warning)
            change_gone(activity_join_step2_tv_email_warning)
            change_gone(activity_join_step2_tv_email_valid_warning)
            change_gone(activity_join_step2_tv_exist_email)
            change_gone(activity_join_step2_tv_possible_email)

            // edittext 지우는 x버튼
            activity_join_step2_et_email.clearText(activity_join_step2_btn_email_erase)

            // 패스워드 일치 문구 해제
            if(activity_join_step2_et_pass.text.toString() == activity_join_step2_et_passcheck.text.toString()) {
                change_gone(activity_join_step2_img_pass_warning)
                change_gone(activity_join_step2_tv_pass_match)
            } else {
                change_visible(activity_join_step2_img_pass_warning)
                change_visible(activity_join_step2_tv_pass_nomatch)
            }

            if(!hasFocus) {
                activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(activity_join_step2_btn_email_erase)

//                if(activity_join_step2_et_email.text.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(activity_join_step2_et_email.text.toString()).matches()) {
//                    activity_join_step2_img_email_warning.setImageResource(R.drawable.ic_warning)
//                    change_visible(activity_join_step2_img_email_warning)
//                    change_visible(activity_join_step2_tv_email_valid_warning)
//                } else {
//                    change_gone(activity_join_step2_img_email_warning)
//                    change_gone(activity_join_step2_tv_email_valid_warning)
//                }

                // 이메일 중복체크
                requestToServer.service.requestDuplicate(
                    RequestDuplicateData(
                        email = activity_join_step2_et_email.text.toString(),
                        name = "이메일만체크함"
                    )
                ).enqueue(object : Callback<ResponseDuplicateData> {
                    override fun onFailure(call: Call<ResponseDuplicateData>, t: Throwable) {
                        Log.d("error", "duplicate / $t")
                    }

                    override fun onResponse(
                        call: Call<ResponseDuplicateData>,
                        response: Response<ResponseDuplicateData>
                    ) {
                        if(response.isSuccessful) {
                            if(response.body()!!.data.duplicate == "email") {
                                activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_red, null)
                                activity_join_step2_img_email_warning.setImageResource(R.drawable.ic_warning)
                                change_visible(activity_join_step2_img_email_warning)
                                change_visible(activity_join_step2_tv_exist_email)
                            } else {
                                if(activity_join_step2_et_email.text.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(activity_join_step2_et_email.text.toString()).matches()) {
                                    activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_red, null)
                                    activity_join_step2_img_email_warning.setImageResource(R.drawable.ic_warning)
                                    change_visible(activity_join_step2_img_email_warning)
                                    change_visible(activity_join_step2_tv_email_valid_warning)
                                } else {
                                    change_gone(activity_join_step2_img_email_warning)
                                    change_gone(activity_join_step2_tv_email_valid_warning)

                                    activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area, null)
                                    activity_join_step2_img_email_warning.setImageResource(R.drawable.ic_possible)
                                    change_visible(activity_join_step2_img_email_warning)
                                    change_visible(activity_join_step2_tv_possible_email)
                                }

                            }
                        }
                    }
                })



            }
        }


        // password 입력창 focus -> warning 문구 해제, et_green 설정
        activity_join_step2_et_pass.setOnFocusChangeListener { _, hasFocus ->

            // warning 문구 해제
            activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area_green, null)
            activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area, null)
            removePassWarning()

            // edittext 지우는 x버튼
            activity_join_step2_et_pass.clearText(activity_join_step2_btn_pass_erase)

            // password 입력창 리스너
            activity_join_step2_et_pass.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    forProgressOn()
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // warning 문구 해제
                    activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area_green, null)
                    activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area, null)
                    removePassWarning()

                    // 패스워드와 패스워드 확인 값이 다르면
                    if(activity_join_step2_et_pass.text.isNotEmpty() && activity_join_step2_et_passcheck.text.isNotEmpty()) {
                        if(activity_join_step2_et_pass.text.toString() != activity_join_step2_et_passcheck.text.toString()) {
                            change_visible(activity_join_step2_img_pass_warning)
                            change_visible(activity_join_step2_tv_pass_nomatch)
                            activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                            change_gone(activity_join_step2_tv_pass_match)
                        } else {
                            change_visible(activity_join_step2_img_pass_warning)
                            //activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_possible)
                            change_gone(activity_join_step2_tv_pass_nomatch)
                            change_visible(activity_join_step2_tv_pass_match)
                        }
                    }

                    if(activity_join_step2_et_passcheck.text.isEmpty() || activity_join_step2_et_passcheck.text.isNotEmpty()) {
                        change_gone(activity_join_step2_img_pass_warning)
                        change_gone(activity_join_step2_tv_pass_nomatch)
                        change_gone(activity_join_step2_tv_pass_match)
                        change_gone(activity_join_step2_tv_pass_valid)

                        // 비밀번호 유효성 검사
                        if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", activity_join_step2_et_pass.text.toString())) {
                            change_visible(activity_join_step2_img_pass_warning)
                            change_visible(activity_join_step2_tv_pass_valid)
                        } else {
                            change_gone(activity_join_step2_img_pass_warning)
                            change_gone(activity_join_step2_tv_pass_valid)
                        }

                    }

                }
            })

            if(!hasFocus) {
                activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(activity_join_step2_btn_pass_erase)
            }

        }



        // password check 입력창 focus -> warning 문구 해제, et_green 설정
        activity_join_step2_et_passcheck.setOnFocusChangeListener { _, hasFocus ->
            // warning 문구 해제
            activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area, null)
            activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area_green, null)
            removePassWarning()

            // edittext 지우는 x버튼
            activity_join_step2_et_passcheck.clearText(activity_join_step2_btn_passcheck_erase)

            // password 확인 입력창 리스너
            activity_join_step2_et_passcheck.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    forProgressOn()
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    // warning 문구 해제
                    activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area, null)
                    activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area_green, null)
                    removePassWarning()

                    // 패스워드와 패스워드 확인 값이 다르면
                    if(activity_join_step2_et_pass.text.toString() != activity_join_step2_et_passcheck.text.toString()) {
                        change_visible(activity_join_step2_img_pass_warning)
                        change_visible(activity_join_step2_tv_pass_nomatch)
                        activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                        change_gone(activity_join_step2_tv_pass_match)
                    } else {
                        change_visible(activity_join_step2_img_pass_warning)
                        change_gone(activity_join_step2_tv_pass_nomatch)
                        activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_possible)
                        change_visible(activity_join_step2_tv_pass_match)
                    }

                    if(activity_join_step2_et_pass.text.isEmpty() && activity_join_step2_et_passcheck.text.isEmpty()) {
                        activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                        change_gone(activity_join_step2_img_pass_warning)
                        change_gone(activity_join_step2_tv_pass_nomatch)
                        change_gone(activity_join_step2_tv_pass_match)
                    }

                }
            })

            if(!hasFocus) {
                activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(activity_join_step2_btn_passcheck_erase)
            }

        }

        // nickname 입력창 리스너
        activity_join_step2_et_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                forProgressOn()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                // 경고문구 해제
                activity_join_step2_et_nickname.background = resources.getDrawable(R.drawable.et_area_green, null)
                change_gone(activity_join_step2_img_nickname_warning)
                change_gone(activity_join_step2_tv_nickname_warning)
                change_gone(activity_join_step2_tv_exist_nickname)

                // 실시간 글자수
                if(activity_join_step2_et_nickname.text.isEmpty()) {
                    change_gone(activity_join_step2_tv_nickname_cnt)
                    change_gone(activity_join_step2_tv_nickname_cnt_max)
                } else {
                    change_visible(activity_join_step2_tv_nickname_cnt)
                    change_visible(activity_join_step2_tv_nickname_cnt_max)
                    val nickname_length = activity_join_step2_et_nickname.text.length.toString()
                    activity_join_step2_tv_nickname_cnt.text = nickname_length
                }

            }
        })

        // nickname 입력창 focus -> warning 문구 해제, et_green 설정
        activity_join_step2_et_nickname.setOnFocusChangeListener { _, hasFocus ->

            activity_join_step2_et_nickname.background = resources.getDrawable(R.drawable.et_area_green, null)
            change_gone(activity_join_step2_img_nickname_warning)
            change_gone(activity_join_step2_tv_nickname_warning)
            change_gone(activity_join_step2_tv_exist_nickname)

            // 패스워드 일치 문구 해제
            if(activity_join_step2_et_pass.text.toString() == activity_join_step2_et_passcheck.text.toString()) {
                change_gone(activity_join_step2_img_pass_warning)
                change_gone(activity_join_step2_tv_pass_match)
            } else {
                change_visible(activity_join_step2_img_pass_warning)
                change_visible(activity_join_step2_tv_pass_nomatch)
            }

            // edittext 지우는 x버튼
            activity_join_step2_et_nickname.clearText(activity_join_step2_btn_nickname_erase)

            if(!hasFocus) {
                activity_join_step2_et_nickname.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(activity_join_step2_btn_nickname_erase)
            }
        }

        // 왼쪽 상단 뒤로가기 버튼
        activity_join_step2_btn_back.setOnClickListener {
            finish()
        }
    }

    // 모든 칸 입력 완료 -> 프로그래스바 불 켜짐
    private fun forProgressOn() {
        if(!(activity_join_step2_et_email.text.isEmpty()) && !(activity_join_step2_et_pass.text.isEmpty()) &&
            !(activity_join_step2_et_passcheck.text.isEmpty()) && !(activity_join_step2_et_nickname.text.isEmpty()) && (activity_join_step2_et_pass.text.toString() == activity_join_step2_et_passcheck.text.toString())) {
            activity_join_step2_2_pgb_out.setBackgroundResource(R.drawable.dot_circle_progresson_out)
            activity_join_step2_2_pgb_in.setBackgroundResource(R.drawable.dot_circle_progresson_in)
        } else {
            activity_join_step2_2_pgb_out.setBackgroundResource(R.drawable.dot_circle_progressoff_out)
            activity_join_step2_2_pgb_in.setBackgroundResource(R.drawable.dot_circle_progressoff_in)
        }
    }

    // warning 문구 해제 - pass, passcheck 공통
    private fun removePassWarning() {
        change_gone(activity_join_step2_img_pass_warning)
        change_gone(activity_join_step2_tv_pass_warning)
        change_gone(activity_join_step2_tv_passcheck_warning)
        change_gone(activity_join_step2_tv_pass_nomatch)
        change_gone(activity_join_step2_tv_pass_match)
        change_gone(activity_join_step2_tv_pass_valid)
    }

    // edittext 지우는 x버튼
    private fun EditText.clearText(button : ImageView) {
        change_visible(button)
        button.setOnClickListener {
            this.setText("")
        }
    }

    override fun onPause() {
        super.onPause()
        // 화면 전환 시 애니메이션 없애는 코드
        overridePendingTransition(0, 0)
    }
}