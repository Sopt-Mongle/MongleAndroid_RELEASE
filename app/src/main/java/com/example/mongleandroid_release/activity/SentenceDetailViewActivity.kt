package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
//import com.example.mongleandroid_release.adapter.DetailSentenceAdapter
import com.example.mongleandroid_release.adapter.DetailThemeAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseSentenceDetailData
import com.example.mongleandroid_release.network.data.response.SentenceDatailData
import kotlinx.android.synthetic.main.activity_sentence_detail_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SentenceDetailViewActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    //private lateinit var detailSentenceAdapter: DetailSentenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_detail_view)

        btn_go_to_theme.setOnClickListener {
            val intent = Intent(this, DetailThemeActivity::class.java)
            startActivity(intent)
        }
        requestSentenceDetail()
        //requestSentenceTheme()
    }
    // 문장 상세보기 데이터 서버 통신
    private fun requestSentenceDetail() {
        requestToServer.service.GetDetailSentence(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(
            object : Callback<ResponseSentenceDetailData> {
                override fun onFailure(call: Call<ResponseSentenceDetailData>, t: Throwable) {
                    Log.e("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseSentenceDetailData>,
                    response: Response<ResponseSentenceDetailData>
                ) {
                    if(response.isSuccessful) {
                        Log.d("통신성공", response.body()!!.data?.sentenceIdx.toString())
                        tv_theme.text = response.body()!!.data?.theme // 해당 테마 제목
                        textView19.text = response.body()!!.data?.sentence // 해당 테마의 문장
                        Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data?.writerImg).into(imageView18) // 문장 작성자 프사
                        //Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data?.thumbnail).into(imageView13) // 테마
                        textView20.text = response.body()!!.data?.writer // 문장 작성자
                        Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data?.thumbnail).into(imageView20) // 해당 문장의 책 사진
                    }
                }

            }
        )
    }

    // 이 테마의 다른 문장 리사이클러뷰 통신
//    private fun requestSentenceTheme() {
//
//        requestToServer.service.GetDetailSentence(
//            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
//            params = intent.getIntExtra("param", 0)
//        ).enqueue(
//            object : retrofit2.Callback<ResponseSentenceDetailData> {
//                override fun onFailure(call: Call<ResponseSentenceDetailData>, t: Throwable) {
//                    Log.e("통신 실패", t.toString())
//                }
//
//                override fun onResponse(
//                    call: Call<ResponseSentenceDetailData>,
//                    response: Response<ResponseSentenceDetailData>
//                ) {
//                    if(response. isSuccessful) {
//                        Log.d("테마 통신 성공", "${response.body()!!.data}")
//                        detailSentenceAdapter = DetailSentenceAdapter( response.body()!!.data , applicationContext)
//                        rv_sentence_detail_view_theme_other_sentence.adapter = detailSentenceAdapter
//                        detailSentenceAdapter.notifyDataSetChanged()
//
//                        detailSentenceAdapter.setItemClickListener(object : DetailSentenceAdapter.ItemClickListener{
//                            override fun onClick(view: View, position: Int) {
//                                Log.d("SSS","${position}번 리스트 선택")
//                                //val intent = Intent(this@SentenceDetailViewActivity, SentenceDetailViewActivity::class.java)
//                                //startActivity(intent)
//                            }
//                        })
//
//                    }
//                }
//
//            }
//        )
//
//    }

}