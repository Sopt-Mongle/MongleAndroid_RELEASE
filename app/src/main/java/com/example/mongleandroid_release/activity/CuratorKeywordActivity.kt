package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.CuratorKeywordAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseCuratorFollowedData
import com.example.mongleandroid_release.network.data.response.ResponseCuratorKeywordData
import kotlinx.android.synthetic.main.activity_curator_keyword.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuratorKeywordActivity : AppCompatActivity() {

    lateinit var curatorKeywordAdapter: CuratorKeywordAdapter

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curator_keyword)

        var keywordValue = intent.getIntExtra("params", 0)
        when(keywordValue) {
            1 -> activity_curator_keyword_tv_keyword.text = "감성자극"
            2 -> activity_curator_keyword_tv_keyword.text = "동기부여"
            3 -> activity_curator_keyword_tv_keyword.text = "자기계발"
            4 -> activity_curator_keyword_tv_keyword.text = "깊은생각"
            5 -> activity_curator_keyword_tv_keyword.text = "독서기록"
            6 -> activity_curator_keyword_tv_keyword.text = "일상문장"
        }

        curatorKeyword()

        activity_curator_keyword_btn_back.setOnClickListener {
            finish()
        }
    }

    private fun curatorKeyword() {
        requestToServer.service.getCuratorKeyword(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("params",0)
        ).enqueue(
            object : Callback<ResponseCuratorKeywordData> {

            override fun onFailure(call: Call<ResponseCuratorKeywordData>, t: Throwable) {
                Log.e("통신실패", "$t")
            }

            override fun onResponse(
                call: Call<ResponseCuratorKeywordData>,
                response: Response<ResponseCuratorKeywordData>
            ) {
                if (response.isSuccessful) {

                    if(response.body()!!.data.isNullOrEmpty()) {
                        Log.d("통신결과", "null or empty")
                    } else {
                        response.body().let { body ->
                            Log.d("키워드 큐레이터 출력", "${body!!.data}")

                            curatorKeywordAdapter = CuratorKeywordAdapter(applicationContext, body.data)
                            rv_curator_keyword.adapter = curatorKeywordAdapter
                            curatorKeywordAdapter.notifyDataSetChanged()

//                        if(response.body()!!.data[0].curatorIdx == 1) {
//                            Log.d("구독", "구독시작")
//                            curatorKeywordAdapter.setItemClickListener(object : CuratorKeywordAdapter.ItemClickListener{
//                                override fun onClick(view: View, position: Int) {
//                                    Log.d("SSS","${position}번 리스트 선택")
//                                    requestToServer.service.getFollowIdx(
//                                        token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
//                                        params = response.body()!!.data[0].curatorIdx
//                                    ).enqueue(object : Callback<ResponseCuratorFollowedData> {
//                                        override fun onFailure(call: Call<ResponseCuratorFollowedData>, t: Throwable) {
//                                            Log.e("통신실패", t.toString())
//                                        }
//
//                                        override fun onResponse(
//                                            call: Call<ResponseCuratorFollowedData>,
//                                            response: Response<ResponseCuratorFollowedData>
//                                        ) {
//                                            if (response.isSuccessful) {
//                                                if(response.body()!!.data) {
//                                                    Log.d("구독", "구독됨")
//                                                }
//                                            }
//
//                                        }
//                                    })
//                                }
//                            })
//
//                        }



                        }
                    }


                }

            }
        })
    }


}