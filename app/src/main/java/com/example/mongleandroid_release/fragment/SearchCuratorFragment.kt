package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.MainActivity.Companion.search_result
import com.example.mongleandroid_release.adapter.SearchCuratorAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseSearchCuratorData
import kotlinx.android.synthetic.main.fragment_search_curator.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchCuratorFragment : Fragment() {

    lateinit var searchCuratorAdapter: SearchCuratorAdapter

    private val requestToServer = RequestToServer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestToServer.service.requestResultCuratorData(
            token = context?.let { SharedPreferenceController.getAccessToken(it) },
            words = search_result
        ).enqueue(object : Callback<ResponseSearchCuratorData> {
            override fun onFailure(call: Call<ResponseSearchCuratorData>, t: Throwable) {
                Log.e("통신실패",t.toString())
            }
            override fun onResponse(
                call: Call<ResponseSearchCuratorData>,
                response: Response<ResponseSearchCuratorData>
            ) {
                fragment_search_curator_cl.visibility = View.GONE
                fragment_search_curator_cl_noresult.visibility = View.GONE
                if (response.isSuccessful){
                    fragment_search_curator_cl.visibility = View.VISIBLE
                    response.body().let { body ->
                        Log.d("큐레이터 검색", response.body()!!.message)
                        fragment_search_curator_tv_count.text = body!!.data.size.toString()
                        searchCuratorAdapter = SearchCuratorAdapter(view!!.context, body.data)
                        rv_search_curator.adapter = searchCuratorAdapter
                        searchCuratorAdapter.notifyDataSetChanged()
                    }
                } else {
                    fragment_search_curator_cl_noresult.visibility = View.VISIBLE
                }
            }
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_curator, container, false)
    }


}