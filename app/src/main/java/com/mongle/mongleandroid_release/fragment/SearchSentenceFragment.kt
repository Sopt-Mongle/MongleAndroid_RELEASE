package com.mongle.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.MainActivity.Companion.search_result
import com.mongle.mongleandroid_release.activity.SentenceDetailViewActivity
import com.mongle.mongleandroid_release.adapter.SearchSentenceAdapter
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.data.response.ResponseSearchSentenceData
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_sentence, container, false)
    }

    override fun onResume() {
        super.onResume()
        requestSearchSentence()
    }

    private fun requestSearchSentence() {
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
                    if(response.body()!!.data.isNullOrEmpty()) {
                        fragment_search_sentence_cl.visibility = View.GONE
                        fragment_search_sentence_cl_noresult.visibility = View.VISIBLE
                    } else {
                        fragment_search_sentence_cl.visibility = View.VISIBLE
                        fragment_search_sentence_cl_noresult.visibility = View.GONE

                        response.body().let { body ->
                            Log.d("문장 검색", response.body()!!.message)
                            fragment_search_sentence_tv_count.text = body!!.data.size.toString()
                            searchSentenceAdapter = SearchSentenceAdapter(view!!.context, body.data)
                            rv_search_sentence.adapter = searchSentenceAdapter
                            searchSentenceAdapter.notifyDataSetChanged()

                            searchSentenceAdapter.setItemClickListener(object : SearchSentenceAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    activity?.let {
                                        val intent = Intent(context, SentenceDetailViewActivity::class.java)
                                        intent.putExtra("param", body.data[position].sentenceIdx)
                                        startActivity(intent)
                                    }
                                }

                            })
                        }
                    }

                } else {
                    fragment_search_sentence_cl_noresult.visibility = View.VISIBLE
                }
            }
        })
    }

}