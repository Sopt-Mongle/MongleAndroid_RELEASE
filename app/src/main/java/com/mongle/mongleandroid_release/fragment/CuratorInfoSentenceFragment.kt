package com.mongle.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.CuratorInfoActivity.Companion.params
import com.mongle.mongleandroid_release.activity.SentenceDetailNoThemeActivity
import com.mongle.mongleandroid_release.adapter.CuratorInfoSentenceAdapter
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.data.response.ResponseCuratorInformationData
import kotlinx.android.synthetic.main.fragment_curator_info_sentence.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuratorInfoSentenceFragment : Fragment() {

    val requestToServer = RequestToServer

    lateinit var curatorInfoSentenceAdapter: CuratorInfoSentenceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curator_info_sentence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestCuratorInfoSentenceData()

    }

    private fun requestCuratorInfoSentenceData() {
        requestToServer.service.CuratorInformation(
            token = context?.let { SharedPreferenceController.getAccessToken(it) },
            params = params
        ).enqueue(
            object : Callback<ResponseCuratorInformationData> {
                override fun onFailure(call: Call<ResponseCuratorInformationData>, t: Throwable) {
                    Log.d("통신실패 문장", "${t}")
                }

                override fun onResponse(
                    call: Call<ResponseCuratorInformationData>,
                    response: Response<ResponseCuratorInformationData>
                ) {
                    if(response.isSuccessful) {
                        Log.d("큐레이터 상세보기 문장 성공", "${response.body()!!.data!!.sentence}")
                        if(response.body()!!.data!!.sentence.isNullOrEmpty()) {
                            Log.d("큐레이터 문장 널 테스트", "null ???")
                        } else {
                            curatorInfoSentenceAdapter = CuratorInfoSentenceAdapter(view!!.context, response.body()!!.data!!.sentence)
                            rv_sentence_cu_info.adapter = curatorInfoSentenceAdapter
                            curatorInfoSentenceAdapter.notifyDataSetChanged()

                            // 문장 상세보기로 이동
                            curatorInfoSentenceAdapter.setItemClickListener(object :
                                CuratorInfoSentenceAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    activity?.let {
                                        val intent = Intent(context, SentenceDetailNoThemeActivity::class.java)
                                        intent.putExtra("param", response.body()!!.data!!.sentence[position].sentenceIdx)
                                        startActivity(intent)
                                    }
                                }

                            })
                        }


                    }
                }
            }

        )
    }
}