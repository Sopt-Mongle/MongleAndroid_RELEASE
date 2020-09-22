package com.example.mongleandroid_release.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_join_step2.*

class JoinStep2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step2)

        // 프로그래스바 애니메이션
        Handler().postDelayed({
            val progressAnimator = ObjectAnimator.ofInt(activity_join_step2_pgb, "progress", 0, 50)
            progressAnimator.setDuration(500)
            progressAnimator.start()
        }, 200)

        // 다음 버튼 눌렀을 때 비어있는 칸 경고문구 설정
        activity_join_step2_btn_next.setOnClickListener {
            if(activity_join_step2_et_email.text.isEmpty()) {
                activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_red, null)
                activity_join_step2_img_email_warning.visibility = VISIBLE
                activity_join_step2_tv_email_warning.visibility = VISIBLE
            } else if(activity_join_step2_et_pass.text.isEmpty()) {
                activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area_red, null)
                activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area_red, null)
                activity_join_step2_img_pass_warning.visibility = VISIBLE
                activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                activity_join_step2_tv_pass_warning.visibility = VISIBLE
                activity_join_step2_tv_pass_nomatch.visibility = GONE
            } else if(activity_join_step2_et_passcheck.text.isEmpty()) {
                activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area_red, null)
                activity_join_step2_img_pass_warning.visibility = VISIBLE
                activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                activity_join_step2_tv_passcheck_warning.visibility = VISIBLE
                activity_join_step2_tv_pass_nomatch.visibility = GONE
            } else if(activity_join_step2_et_nickname.text.isEmpty()) {
                activity_join_step2_et_nickname.background = resources.getDrawable(R.drawable.et_area_red, null)
                activity_join_step2_img_nickname_warning.visibility = VISIBLE
                activity_join_step2_tv_nickname_warning.visibility = VISIBLE
            }
        }

        // email 입력창 리스너
        activity_join_step2_et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_green, null)
                activity_join_step2_img_email_warning.visibility = GONE
                activity_join_step2_tv_email_warning.visibility = GONE
            }
        })

        // email 입력창 focus -> warning 문구 해제, et_green 설정
        activity_join_step2_et_email.setOnFocusChangeListener { v, hasFocus ->
            activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area_green, null)
            activity_join_step2_img_email_warning.visibility = GONE
            activity_join_step2_tv_email_warning.visibility = GONE
            if(!hasFocus) activity_join_step2_et_email.background = resources.getDrawable(R.drawable.et_area, null)
        }



        // password 입력창 focus -> warning 문구 해제, et_green 설정
        activity_join_step2_et_pass.setOnFocusChangeListener { v, hasFocus ->
            // warning 문구 해제
            activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area_green, null)
            activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area, null)
            activity_join_step2_img_pass_warning.visibility = GONE
            activity_join_step2_tv_pass_warning.visibility = GONE
            activity_join_step2_tv_passcheck_warning.visibility = GONE
            activity_join_step2_tv_pass_nomatch.visibility = GONE
            activity_join_step2_tv_pass_match.visibility = GONE

            // password 입력창 리스너
            activity_join_step2_et_pass.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // 경고문구 해제
                    activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area_green, null)
                    activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area, null)
                    activity_join_step2_img_pass_warning.visibility = GONE
                    activity_join_step2_tv_pass_warning.visibility = GONE
                    activity_join_step2_tv_passcheck_warning.visibility = GONE
                    activity_join_step2_tv_pass_nomatch.visibility = GONE
                    activity_join_step2_tv_pass_match.visibility = GONE

                    // 패스워드와 패스워드 확인 값이 다르면
                    if(activity_join_step2_et_pass.text.toString() != activity_join_step2_et_passcheck.text.toString()) {
                        activity_join_step2_img_pass_warning.visibility = VISIBLE
                        activity_join_step2_tv_pass_nomatch.visibility = VISIBLE
                        activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                        activity_join_step2_tv_pass_match.visibility = GONE
                    } else {
                        activity_join_step2_img_pass_warning.visibility = VISIBLE
                        activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_possible)
                        activity_join_step2_tv_pass_nomatch.visibility = GONE
                        activity_join_step2_tv_pass_match.visibility = VISIBLE
                    }

                    if(activity_join_step2_et_passcheck.text.toString().isEmpty()) {
                        activity_join_step2_img_pass_warning.visibility = GONE
                        activity_join_step2_tv_pass_nomatch.visibility = GONE
                        activity_join_step2_tv_pass_match.visibility = GONE
                    }

                }
            })

            if(!hasFocus) activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area, null)
        }



        // password check 입력창 focus -> warning 문구 해제, et_green 설정
        activity_join_step2_et_passcheck.setOnFocusChangeListener { v, hasFocus ->
            // warning 문구 해제
            activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area, null)
            activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area_green, null)
            activity_join_step2_img_pass_warning.visibility = GONE
            activity_join_step2_tv_pass_warning.visibility = GONE
            activity_join_step2_tv_passcheck_warning.visibility = GONE
            activity_join_step2_tv_pass_nomatch.visibility = GONE
            activity_join_step2_tv_pass_match.visibility = GONE

            // password 확인창 리스너
            activity_join_step2_et_passcheck.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    // warning 문구 해제
                    activity_join_step2_et_pass.background = resources.getDrawable(R.drawable.et_area, null)
                    activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area_green, null)
                    activity_join_step2_img_pass_warning.visibility = GONE
                    activity_join_step2_tv_pass_warning.visibility = GONE
                    activity_join_step2_tv_passcheck_warning.visibility = GONE
                    activity_join_step2_tv_pass_nomatch.visibility = GONE
                    activity_join_step2_tv_pass_match.visibility = GONE

                    // 패스워드와 패스워드 확인 값이 다르면
                    if(activity_join_step2_et_pass.text.toString() != activity_join_step2_et_passcheck.text.toString()) {
                        activity_join_step2_img_pass_warning.visibility = VISIBLE
                        activity_join_step2_tv_pass_nomatch.visibility = VISIBLE
                        activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                        activity_join_step2_tv_pass_match.visibility = GONE
                    } else {
                        activity_join_step2_img_pass_warning.visibility = VISIBLE
                        activity_join_step2_tv_pass_nomatch.visibility = GONE
                        activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_possible)
                        activity_join_step2_tv_pass_match.visibility = VISIBLE
                    }

                    if(activity_join_step2_et_pass.text.toString().isEmpty() && activity_join_step2_et_passcheck.text.toString().isEmpty()) {
                        activity_join_step2_img_pass_warning.setImageResource(R.drawable.ic_warning)
                        activity_join_step2_img_pass_warning.visibility = GONE
                        activity_join_step2_tv_pass_nomatch.visibility = GONE
                        activity_join_step2_tv_pass_match.visibility = GONE
                    }

                }
            })

            if(!hasFocus) activity_join_step2_et_passcheck.background = resources.getDrawable(R.drawable.et_area, null)

        }

        // nickname 입력창 리스너
        activity_join_step2_et_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                // 경고문구 해제
                activity_join_step2_et_nickname.background = resources.getDrawable(R.drawable.et_area_green, null)
                activity_join_step2_img_nickname_warning.visibility = GONE
                activity_join_step2_tv_nickname_warning.visibility = GONE

                // 실시간 글자수
                if(activity_join_step2_et_nickname.text.toString().isEmpty()) {
                    activity_join_step2_tv_nickname_cnt.visibility = GONE
                    activity_join_step2_tv_nickname_cnt_max.visibility = GONE
                } else {
                    activity_join_step2_tv_nickname_cnt.visibility = VISIBLE
                    activity_join_step2_tv_nickname_cnt_max.visibility = VISIBLE
                    val nickname_length = activity_join_step2_et_nickname.text.toString()
                    activity_join_step2_tv_nickname_cnt.setText(nickname_length.length.toString())
                }

            }
        })

        // nickname 입력창 focus -> warning 문구 해제, et_green 설정
        activity_join_step2_et_nickname.setOnFocusChangeListener { v, hasFocus ->

            activity_join_step2_et_nickname.background = resources.getDrawable(R.drawable.et_area_green, null)
            activity_join_step2_img_nickname_warning.visibility = GONE
            activity_join_step2_tv_nickname_warning.visibility = GONE

            // 패스워드 일치 문구 해제
            if(activity_join_step2_et_pass.text.toString() == activity_join_step2_et_passcheck.text.toString()) {
                activity_join_step2_img_pass_warning.visibility = GONE
                activity_join_step2_tv_pass_match.visibility = GONE
            } else {
                activity_join_step2_img_pass_warning.visibility = VISIBLE
                activity_join_step2_tv_pass_nomatch.visibility = VISIBLE
            }

            if(!hasFocus) activity_join_step2_et_nickname.background = resources.getDrawable(R.drawable.et_area, null)
        }

        activity_join_step2_btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        // 화면 전환 시 애니메이션 없애는 코드
        overridePendingTransition(0, 0)
    }
}