package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.dialog.DialogDeleteSentence
import com.example.mongleandroid_release.dialog.DialogModifySentence
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.request.RequestChangePasswordData
import com.example.mongleandroid_release.network.data.response.*
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyLibraryWrittenSentenceActivity : AppCompatActivity() {

    private val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        // 뒤로 가기 버튼 눌렀을 때
        img_back_sentence_modify.setOnClickListener {
            finish()
        }

        //기존 문장 editText 받아오기
        requestToServer.service.lookLibrarySentence(
            token = SharedPreferenceController.getAccessToken(this)
        ).enqueue(object : Callback<ResponseLibrarySentenceData> {

            override fun onResponse(
                call: Call<ResponseLibrarySentenceData>,
                response: Response<ResponseLibrarySentenceData>
            ) {
                if(response.isSuccessful) {
                    Log.e("내 서재 문장 수정 성공", " ")

                    et_sentence_modify.setText(response.body()!!.data!!.write[0].sentence)
                }
            }

            override fun onFailure(call: Call<ResponseLibrarySentenceData>, t: Throwable) {
                Log.d("내 서재 문장 수정 실패", "$t")
            }

        })

        //수정하기 버튼 눌렀을 때
        tv_modify_done.setOnClickListener {

            val sentence_new = RequestBody.create(
                MediaType.parse("text/plain"),
                et_sentence_modify.text.toString()
            )

            requestToServer.service.ModifySentenceWritten(
                token = this.let { SharedPreferenceController.getAccessToken(it) },
                params = intent.getIntExtra("param", 0),
                sentence = sentence_new
            ).enqueue(object : Callback<ResponseModifySentenceWrittenData> {
                override fun onFailure(call: Call<ResponseModifySentenceWrittenData>, t: Throwable) {
                    Log.d("내 서재 문장 수정 내용 보내기 통신 실패", "$t")
                }

                override fun onResponse(
                    call: Call<ResponseModifySentenceWrittenData>,
                    response: Response<ResponseModifySentenceWrittenData>
                ) {
                    if (response.isSuccessful) {
                        Log.d("내 서재 문장 수정 내용 보내기 통신 성공", " ")

                    }
                }

            })

            //다이얼로그 띄우고 확인 누르면 종료 시키기
            val dlg = DialogModifySentence(this)
            dlg.start()
            dlg.setOnClickListener { content ->
                if(content == "확인") {
                    finish() //액티비티 종료하고
                }
            }


        }

        // 문장 글자수 카운팅
        et_sentence_modify.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 실시간 글자수
                val string_length = et_sentence_modify.text.toString()
                tv_sentence_modify_words.setText(string_length.length.toString())
            }

        })
    }
}