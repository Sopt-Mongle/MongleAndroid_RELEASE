package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.MainActivity.Companion.search_result
import com.example.mongleandroid_release.activity.SentenceDetailViewActivity
import com.example.mongleandroid_release.adapter.SearchSentenceAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.data.response.ResponseSearchSentenceData
import com.example.mongleandroid_release.network.data.response.SearchSentence
import kotlinx.android.synthetic.main.fragment_search_sentence.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchSentenceFragment : Fragment() {

    lateinit var searchSentenceAdapter: SearchSentenceAdapter

    private val requestToServer = RequestToServer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestToServer.service.requestResultSentenceData(
            words = search_result
        ).enqueue(object : Callback<ResponseSearchSentenceData> {
            override fun onFailure(call: Call<ResponseSearchSentenceData>, t: Throwable) {
                Log.e("통신실패",t.toString())
            }
            override fun onResponse(
                call: Call<ResponseSearchSentenceData>,
                response: Response<ResponseSearchSentenceData>
            ) {
                fragment_search_sentence_cl.visibility = View.GONE
                fragment_search_sentence_cl_noresult.visibility = View.GONE
                if (response.isSuccessful){
                    fragment_search_sentence_cl.visibility = View.VISIBLE
                    response.body().let { body ->
                        Log.d("문장 검색", response.body()!!.message)
                        fragment_search_sentence_tv_count.text = body!!.data.size.toString()
                        searchSentenceAdapter = SearchSentenceAdapter(view!!.context, body.data)
                        rv_search_sentence.adapter = searchSentenceAdapter
                        searchSentenceAdapter.notifyDataSetChanged()

                        searchSentenceAdapter.setItemClickListener(object : SearchSentenceAdapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                val intent = Intent(context, SentenceDetailViewActivity::class.java)
                                intent.putExtra("param", body.data[position].sentenceIdx)
                                startActivity(intent)
                            }

                        })
                    }
                } else {
                    fragment_search_sentence_cl_noresult.visibility = View.VISIBLE
                }
            }
        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_sentence, container, false)
    }

}