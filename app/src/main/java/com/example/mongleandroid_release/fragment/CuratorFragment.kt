package com.example.mongleandroid_release.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.CuratorKeywordActivity
import com.example.mongleandroid_release.adapter.CuratorInThemeAdapter
import com.example.mongleandroid_release.adapter.CuratorRecommendAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseCuratorFollowedData
import com.example.mongleandroid_release.network.data.response.ResponseCuratorInThemeData
import com.example.mongleandroid_release.network.data.response.ResponseRecommendCuratorData
import kotlinx.android.synthetic.main.fragment_curator.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuratorFragment : Fragment() {

    lateinit var curatorRecommendAdapter: CuratorRecommendAdapter

    lateinit var curatorInThemeAdapter: CuratorInThemeAdapter

    lateinit var curatorInThemeAdapter2: CuratorInThemeAdapter

    private val requestToServer = RequestToServer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        curatorRecommend()
        curatorInThemeData()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goKeywordActivity(fragment_curator_tv_gamsung, 1)
        goKeywordActivity(fragment_curator_tv_donggi, 2)
        goKeywordActivity(fragment_curator_tv_jagi, 3)
        goKeywordActivity(fragment_curator_tv_kipeun, 4)
        goKeywordActivity(fragment_curator_tv_dokseo, 5)
        goKeywordActivity(fragment_curator_tv_ilsang, 6)



    }

    // 키워드 큐레이터로 이동
    private fun goKeywordActivity(view: View, value : Int) {
        view.setOnClickListener {
            activity?.let{
                val intent = Intent(context, CuratorKeywordActivity::class.java)
                intent.putExtra("params", value)
                startActivity(intent)
            }
        }
    }

    // 추천 큐레이터
    private fun curatorRecommend() {
        requestToServer.service.getRecommendCurator()
            .enqueue(
                object : Callback<ResponseRecommendCuratorData> {
                    override fun onFailure(call: Call<ResponseRecommendCuratorData>, t: Throwable) {
                        Log.d("통신실패", "$t")
                    }

                    override fun onResponse(
                        call: Call<ResponseRecommendCuratorData>,
                        response: Response<ResponseRecommendCuratorData>
                    ) {
                        Log.d("통신", response.body().toString())
                        if (response.isSuccessful) {
                            Log.d("추천 큐레이터", "${response.body()}")
                            curatorRecommendAdapter = CuratorRecommendAdapter(view!!.context, response.body()!!.data)
                            fragment_curator_rv_recommend.adapter = curatorRecommendAdapter
                            curatorRecommendAdapter.notifyDataSetChanged()
                        }

                    }
                }
            )
    }

    // 테마 속 큐레이터
    private fun curatorInThemeData() {
        requestToServer.service.requestCuratorInThemeData(
            token = context?.let { SharedPreferenceController.getAccessToken(it) }
        ).enqueue(
            object : Callback<ResponseCuratorInThemeData> {

                override fun onFailure(call: Call<ResponseCuratorInThemeData>, t: Throwable) {
                    Log.d("통신실패", "$t")
                }

                override fun onResponse(
                    call: Call<ResponseCuratorInThemeData>,
                    response: Response<ResponseCuratorInThemeData>
                ) {
                    if (response.isSuccessful) {
                        if(response.body()!!.data!!.theme.isNullOrEmpty()) {
                            constraintLayout4.visibility = GONE
                            fragment_curator_rv_curator1.visibility = GONE
                            constraintLayout5.visibility = GONE
                            fragment_curator_rv_curator2.visibility = GONE
                        } else {
                            constraintLayout4.visibility = VISIBLE
                            fragment_curator_rv_curator1.visibility = VISIBLE
                            constraintLayout5.visibility = VISIBLE
                            fragment_curator_rv_curator2.visibility = VISIBLE

                            response.body().let { body ->

                                fragment_curator_tv_themename.text = body!!.data!!.theme[0].theme
                                fragment_curator_tv_curator_count.text = body.data!!.theme[0].curatorNum.toString()

                                fragment_curator_tv_themename2.text = body.data.theme[1].theme
                                fragment_curator_tv_curator_count2.text = body.data.theme[1].curatorNum.toString()

                                curatorInThemeAdapter = CuratorInThemeAdapter(view!!.context, body.data.theme[0].curators)
                                fragment_curator_rv_curator1.adapter = curatorInThemeAdapter
                                curatorInThemeAdapter.notifyDataSetChanged()

                                curatorInThemeAdapter2 = CuratorInThemeAdapter(view!!.context, body.data.theme[1].curators)
                                fragment_curator_rv_curator2.adapter = curatorInThemeAdapter2
                                curatorInThemeAdapter2.notifyDataSetChanged()

                                // 구독여부 통신
                                fun subscribed(position : Int) {
                                    requestToServer.service.getFollowIdx(
                                        token = context?.let { SharedPreferenceController.getAccessToken(it) },
                                        params = response.body()!!.data!!.theme[1].curators[position].curatorIdx
                                    ).enqueue(object : Callback<ResponseCuratorFollowedData> {
                                        override fun onFailure(call: Call<ResponseCuratorFollowedData>, t: Throwable) {
                                            Log.e("통신실패", t.toString())
                                        }

                                        @SuppressLint("ResourceAsColor")
                                        override fun onResponse(
                                            call: Call<ResponseCuratorFollowedData>,
                                            response: Response<ResponseCuratorFollowedData>
                                        ) {
                                            if (response.isSuccessful) {
                                                if(response.body()!!.data) {
                                                    Log.d("구독", "구독")
                                                } else {
                                                    Log.d("구독", "구독취소")
                                                }
                                            }

                                        }
                                    })
                                }

                                curatorInThemeAdapter.setItemClickListener(object : CuratorInThemeAdapter.ItemClickListener{
                                    override fun onClickItem(view: View, position: Int) {
                                        // 큐레이터 상세로 이동
                                    }

                                    override fun onClickSubscribe(view: View, position: Int) {
                                        subscribed(position)
                                    }
                                })


                                curatorInThemeAdapter2.setItemClickListener(object : CuratorInThemeAdapter.ItemClickListener{
                                    override fun onClickItem(view: View, position: Int) {
                                        // 큐레이터 상세로 이동
                                    }

                                    override fun onClickSubscribe(view: View, position: Int) {
                                        subscribed(position)
                                    }
                                })

                            }
                        }

                    }
                }
            }
        )

    }

}