package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.DetailThemeActivity
import com.example.mongleandroid_release.activity.MainActivity
import com.example.mongleandroid_release.activity.MainActivity.Companion.search_result
import com.example.mongleandroid_release.adapter.SearchTabAdapter
import com.example.mongleandroid_release.adapter.SearchThemeAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseSearchThemeData
import com.example.mongleandroid_release.network.data.response.SearchTheme
import kotlinx.android.synthetic.main.fragment_search_theme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchThemeFragment : Fragment() {

    lateinit var searchThemeAdapter: SearchThemeAdapter

    private val requestToServer = RequestToServer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestToServer.service.requestSearchTheme(
            token = context?.let { SharedPreferenceController.getAccessToken(it) },
            words = search_result
        ).enqueue(object : Callback<ResponseSearchThemeData> {
            override fun onFailure(call: Call<ResponseSearchThemeData>, t: Throwable) {
                Log.e("통신실패", t.toString())
            }

            override fun onResponse(
                call: Call<ResponseSearchThemeData>,
                response: Response<ResponseSearchThemeData>
            ) {
                fragment_search_theme_cl.visibility = View.GONE
                fragment_search_theme_cl_noresult.visibility = View.GONE

                if (response.isSuccessful) {
                    fragment_search_theme_cl.visibility = View.VISIBLE
                    response.body().let { body ->
                        Log.d("테마 검색", response.body()!!.message)
                        fragment_search_theme_tv_count.text = body!!.data.size.toString()
                        searchThemeAdapter = SearchThemeAdapter(view!!.context, body.data)
                        rv_search_theme.adapter = searchThemeAdapter
                        searchThemeAdapter.notifyDataSetChanged()

                        searchThemeAdapter.setItemClickListener(object : SearchThemeAdapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                val intent = Intent(context, DetailThemeActivity::class.java)
                                intent.putExtra("param", body.data[position].themeIdx)
                                startActivity(intent)
                            }

                        })
                    }
                } else {
                    fragment_search_theme_cl_noresult.visibility = View.VISIBLE
                }

            }
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_theme, container, false)
    }
}