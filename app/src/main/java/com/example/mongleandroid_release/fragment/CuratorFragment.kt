package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.CuratorKeywordActivity
import com.example.mongleandroid_release.adapter.CuratorInThemeAdapter
import com.example.mongleandroid_release.adapter.CuratorRecommendAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.customEnqueue
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
                        response.body().let { body ->

                            fragment_curator_tv_themename.text = body!!.data!!.theme[0].theme
                            fragment_curator_tv_curator_count.text = body.data!!.theme[0].curatorNum.toString()

                            fragment_curator_tv_themename2.text = body.data.theme[1].theme
                            fragment_curator_tv_curator_count2.text = body.data.theme[1].curatorNum.toString()

                            Log.d("테마속 큐레이터", "${response.body()!!.data!!.theme[0].curators}")
                            curatorInThemeAdapter = CuratorInThemeAdapter(view!!.context, body.data.theme[0].curators)
                            fragment_curator_rv_curator1.adapter = curatorInThemeAdapter
                            curatorInThemeAdapter.notifyDataSetChanged()

                            curatorInThemeAdapter2 = CuratorInThemeAdapter(view!!.context, body.data.theme[1].curators)
                            fragment_curator_rv_curator2.adapter = curatorInThemeAdapter2
                            curatorInThemeAdapter2.notifyDataSetChanged()
                        }


                    }
                }
            }
        )

    }

}