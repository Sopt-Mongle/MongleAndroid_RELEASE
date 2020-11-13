package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mongleandroid_release.R
//import com.example.mongleandroid_release.adapter.DetailSentenceAdapter
import com.example.mongleandroid_release.adapter.DetailThemeAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseSentenceDetailData
import kotlinx.android.synthetic.main.activity_sentence_detail_view.*
import retrofit2.Call
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

        //requestSentenceTheme()
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