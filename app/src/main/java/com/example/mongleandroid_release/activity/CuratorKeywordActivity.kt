package com.example.mongleandroid_release.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.CuratorKeywordAdapter
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import com.example.mongleandroid_release.dialog.DialogGuest
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseCuratorFollowedData
import com.example.mongleandroid_release.network.data.response.ResponseCuratorKeywordData
import kotlinx.android.synthetic.main.activity_curator_keyword.*
import kotlinx.android.synthetic.main.item_search_curator.view.*
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

        // 그림자 박스
        rv_curator_keyword.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstCompletelyVisibleItemPosition()
                if (firstVisibleItemPosition == 0) {
                    change_gone(activity_curator_keyword_view_top)
                } else {
                    change_visible(activity_curator_keyword_view_top)
                }
            }
        })


    }

    private fun curatorKeyword() {
        requestToServer.service.getCuratorKeyword(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("params", 0)
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

                        if (response.body()!!.data.isNullOrEmpty()) {
                            Log.d("통신결과", "null or empty")
                        } else {
                            response.body().let { body ->

                                curatorKeywordAdapter = CuratorKeywordAdapter(
                                    applicationContext,
                                    body!!.data
                                )
                                rv_curator_keyword.adapter = curatorKeywordAdapter
                                curatorKeywordAdapter.notifyDataSetChanged()

                                curatorKeywordAdapter.setItemClickListener(object :
                                    CuratorKeywordAdapter.ItemClickListener {
                                    override fun onClickItem(view: View, position: Int) {
                                        val intent = Intent(
                                            applicationContext,
                                            CuratorInfoActivity::class.java
                                        )
                                        intent.putExtra("param", body.data[position].curatorIdx)
                                        startActivity(intent)
                                    }

                                    override fun onClickSubscribe(view: View, position: Int) {
                                        if (this@CuratorKeywordActivity.let {
                                                SharedPreferenceController.getAccessToken(
                                                    it
                                                )
                                            } == "guest") {

                                            val dlg = DialogGuest(this@CuratorKeywordActivity)
                                            dlg.start()

                                        } else {
                                            requestToServer.service.getFollowIdx(
                                                token = applicationContext.let {
                                                    SharedPreferenceController.getAccessToken(
                                                        it
                                                    )
                                                },
                                                params = response.body()!!.data[position].curatorIdx
                                            ).enqueue(object :
                                                Callback<ResponseCuratorFollowedData> {
                                                override fun onFailure(
                                                    call: Call<ResponseCuratorFollowedData>,
                                                    t: Throwable
                                                ) {
                                                    Log.e("통신실패", t.toString())
                                                }

                                                override fun onResponse(
                                                    call: Call<ResponseCuratorFollowedData>,
                                                    response: Response<ResponseCuratorFollowedData>
                                                ) {
                                                    if (response.isSuccessful) {
                                                        if (response.body()!!.data) {
                                                            Log.d("구독", "구독")
                                                        } else {
                                                            Log.d("구독", "구독취소")
                                                        }
                                                    }

                                                }
                                            })
                                        }

                                    }

                                })


                            }
                        }


                    }

                }
            })
    }


}