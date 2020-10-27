package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.CuratorRecommendAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.customEnqueue
import com.example.mongleandroid_release.network.data.response.ResponseRecommendCuratorData
import kotlinx.android.synthetic.main.fragment_curator.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuratorFragment : Fragment() {

    lateinit var curatorRecommendAdapter: CuratorRecommendAdapter

    private val requestToServer = RequestToServer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        curatorRecommend()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curator, container, false)
    }

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

}