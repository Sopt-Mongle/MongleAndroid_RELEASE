package com.mongle.mongleandroid_release.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.dialog.DialogModifySentence
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.data.request.RequestModifySentenceData
import com.mongle.mongleandroid_release.network.data.response.ResponseModifySentenceWrittenData
import kotlinx.android.synthetic.main.activity_modify.*
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
        et_sentence_modify.setText(intent.getStringExtra("sentence"))
        val sentenceSize = et_sentence_modify.text.length.toString()
        tv_sentence_modify_words.setText(sentenceSize)

        //수정하기 버튼 눌렀을 때
        tv_modify_done.setOnClickListener {

            requestToServer.service.ModifySentenceWritten(
                token = this.let { SharedPreferenceController.getAccessToken(it) },
                params = intent.getIntExtra("param", 0),
                body = RequestModifySentenceData(
                    sentence = et_sentence_modify.text.toString()
                )
            ).enqueue(object : Callback<ResponseModifySentenceWrittenData> {
                override fun onFailure(
                    call: Call<ResponseModifySentenceWrittenData>,
                    t: Throwable
                ) {
                    Log.d("내 서재 문장 수정 내용 보내기 통신 실패", "$t")
                }

                override fun onResponse(
                    call: Call<ResponseModifySentenceWrittenData>,
                    response: Response<ResponseModifySentenceWrittenData>
                ) {
                    if (response.isSuccessful) {
                        Log.d("내 서재 문장 수정 내용 보내기 통신 성공", " ")




                        // 성공하면 다이얼로그 띄우고 확인 누르면 종료 시키기
                        val dlg = DialogModifySentence(this@ModifyLibraryWrittenSentenceActivity)
                        dlg.start()
                        dlg.setOnClickListener { content ->
                            if (content == "확인") {
                                finish() //액티비티 종료하고
                                // 프래그먼트 갱신
                                var fmReload : Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_library_view)
                                val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
//                                    ft.replace(R.id.fragment_library_view, LibraryFragment())
                                if (fmReload != null) {
                                    ft.detach(fmReload)
                                    ft.attach(fmReload)

                                }
                                    ft.commit()
                            }
                        }

                    }
                }

            })




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