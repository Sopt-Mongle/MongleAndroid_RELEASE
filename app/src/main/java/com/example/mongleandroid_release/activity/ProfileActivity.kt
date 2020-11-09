package com.example.mongleandroid_release.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.data.request.RequestDuplicateData
import com.example.mongleandroid_release.network.data.response.ResponseDuplicateData
import kotlinx.android.synthetic.main.activity_join_step2.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.jar.Manifest

class ProfileActivity : AppCompatActivity() {

    private val requestToServer = RequestToServer
    private val takeGallery = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // 이미지 둥글게
        activity_profile_img.background = ShapeDrawable(OvalShape())
        activity_profile_img.clipToOutline = true

        // 키워드 선택 파라미터
        var keywordIdx : Int = 0

        activity_profile_btn1.setOnClickListener {
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 1
            remove_keyword_warning()
        }

        activity_profile_btn2.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 2
            remove_keyword_warning()
        }

        activity_profile_btn3.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 3
            remove_keyword_warning()
        }

        activity_profile_btn4.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 4
            remove_keyword_warning()
        }

        activity_profile_btn5.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIdx = 5
            remove_keyword_warning()
        }

        activity_profile_btn6.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            keywordIdx = 6
            remove_keyword_warning()
        }


        activity_profile_btn_camera.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, takeGallery)
        }



        // 다음버튼 눌렀을 때 비어있는 칸 경고문구 설정
        activity_profile_btn_next.setOnClickListener {
            if(activity_profile_et_nickname.text.isEmpty()) {
                activity_profile_et_nickname.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(activity_profile_img_nickname_warning)
                activity_profile_img_nickname_warning.setImageResource(R.drawable.ic_warning)
                change_visible(activity_profile_tv_nickname_warning)
                change_gone(activity_profile_tv_nickname_exist)
            } else if(keywordIdx == 0) {
                change_visible(activity_profile_img_keyword_warning)
                change_visible(activity_profile_tv_keyword_warning)
            } else if(activity_profile_et_introduce.text.isEmpty()) {
                activity_profile_et_introduce.background = resources.getDrawable(R.drawable.et_area_red, null)
                change_visible(activity_profile_img_introduce_warning)
                change_visible(activity_profile_tv_introduce_warning)
            } else {
                // 서버
            }
        }

        // 닉네임 입력창 리스너
        activity_profile_et_nickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // 경고문구 해제
                activity_profile_et_nickname.background = resources.getDrawable(R.drawable.et_area_green, null)
                change_gone(activity_profile_img_nickname_warning)
                change_gone(activity_profile_tv_nickname_warning)
                change_gone(activity_profile_tv_nickname_exist)
                change_gone(activity_profile_tv_nickname_possible)

                // 실시간 글자수
                if(activity_profile_et_nickname.text.isEmpty()) {
                    change_gone(activity_profile_tv_nickname_cnt)
                    change_gone(activity_profile_tv_nickname_cnt_max)
                } else {
                    change_visible(activity_profile_tv_nickname_cnt)
                    change_visible(activity_profile_tv_nickname_cnt_max)
                    val nickname_length = activity_profile_et_nickname.text.length.toString()
                    activity_profile_tv_nickname_cnt.text = nickname_length
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        activity_profile_et_nickname.setOnFocusChangeListener { _, hasFocus ->
            activity_profile_et_nickname.background = resources.getDrawable(R.drawable.et_area_green, null)
            change_gone(activity_profile_img_nickname_warning)
            change_gone(activity_profile_tv_nickname_warning)
            change_gone(activity_profile_tv_nickname_exist)
            change_gone(activity_profile_tv_nickname_possible)

            // erase
            activity_profile_et_nickname.clearText(activity_profile_btn_nickname_erase)

            if(!hasFocus) {
                activity_profile_et_nickname.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(activity_profile_btn_nickname_erase)

                // 닉네임 중복체크
                requestToServer.service.requestDuplicate(
                    RequestDuplicateData(
                        email = "닉네임만체크함",
                        name = activity_profile_et_nickname.text.toString()
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
                            if(response.body()!!.data.duplicate == "name") {
                                activity_profile_et_nickname.background = resources.getDrawable(R.drawable.et_area_red, null)
                                change_visible(activity_profile_img_nickname_warning)
                                activity_profile_img_nickname_warning.setImageResource(R.drawable.ic_warning)
                                change_visible(activity_profile_tv_nickname_exist)
                            } else {
                                change_visible(activity_profile_img_nickname_warning)
                                activity_profile_img_nickname_warning.setImageResource(R.drawable.ic_possible)
                                change_visible(activity_profile_tv_nickname_possible)
                            }
                        }
                    }
                })
            }
        }

        // 소개 입력창 리스너
        activity_profile_et_introduce.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 경고문구 해제
                activity_profile_et_introduce.background = resources.getDrawable(R.drawable.et_area_green, null)
                change_gone(activity_profile_img_introduce_warning)
                change_gone(activity_profile_tv_introduce_warning)

                // 실시간 글자수
                if(activity_profile_et_introduce.text.isEmpty()) {
                    change_gone(activity_profile_tv_introduce_cnt)
                    change_gone(activity_profile_tv_introduce_cnt_max)
                } else {
                    change_visible(activity_profile_tv_introduce_cnt)
                    change_visible(activity_profile_tv_introduce_cnt_max)
                    val introduce_length = activity_profile_et_introduce.text.length.toString()
                    activity_profile_tv_introduce_cnt.text = introduce_length
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        activity_profile_et_introduce.setOnFocusChangeListener { _, hasFocus ->
            activity_profile_et_introduce.background = resources.getDrawable(R.drawable.et_area_green, null)
            change_gone(activity_profile_img_introduce_warning)
            change_gone(activity_profile_tv_introduce_warning)

            // erase
            activity_profile_et_introduce.clearText(activity_profile_btn_introduce_erase)

            if(!hasFocus) {
                activity_profile_et_introduce.background = resources.getDrawable(R.drawable.et_area, null)
                change_gone(activity_profile_btn_introduce_erase)
            }
        }

    }

    private fun remove_keyword_warning() {
        change_gone(activity_profile_img_keyword_warning)
        change_gone(activity_profile_tv_keyword_warning)
    }

    // edittext 지우는 x버튼
    private fun EditText.clearText(button : ImageView) {
        change_visible(button)
        button.setOnClickListener {
            this.setText("")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == takeGallery) {
            if(resultCode == Activity.RESULT_OK) {
                val file = data!!.data
                Glide.with(this).load(file).into(activity_profile_img)
            }
        }
    }
}