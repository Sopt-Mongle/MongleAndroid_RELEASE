package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.CuratorInfoSentenceAdapter
import com.example.mongleandroid_release.adapter.CuratorInfoThemaAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.CuratorInfoSentenceData
import com.example.mongleandroid_release.network.data.response.ResponseCuratorInformationData
import kotlinx.android.synthetic.main.fragment_curator_info_sentence.*
import kotlinx.android.synthetic.main.fragment_curator_info_thema.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuratorInfoSentenceFragment : Fragment() {

    val requestToServer = RequestToServer

    lateinit var curatorInfoSentenceAdapter: CuratorInfoSentenceAdapter
    val datas = mutableListOf<CuratorInfoSentenceData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curator_info_sentence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        curatorInfoSentenceAdapter = CuratorInfoSentenceAdapter(view.context)
//        rv_sentence_cu_info.adapter = curatorInfoSentenceAdapter
//        loadDatas()
        requestCuratorInfoSentenceData()
    }

    private fun requestCuratorInfoSentenceData() {
        requestToServer.service.CuratorInformation(
            token = SharedPreferenceController.getAccessToken(requireView().context),
            params = 13
            //            params = intent.getIntExtra("param", 0)

        ).enqueue(
            object : Callback<ResponseCuratorInformationData> {
                override fun onFailure(call: Call<ResponseCuratorInformationData>, t: Throwable) {
                    Log.d("통신실패", "${t}")
                }

                override fun onResponse(
                    call: Call<ResponseCuratorInformationData>,
                    response: Response<ResponseCuratorInformationData>
                ) {
                    if(response.isSuccessful) {
                        Log.d("큐레이터 상세보기 조회 성공", "${response.body()}")

                        curatorInfoSentenceAdapter = CuratorInfoSentenceAdapter(view!!.context, response.body()!!.data!!.sentence)
                        rv_sentence_cu_info.adapter = curatorInfoSentenceAdapter
                        curatorInfoSentenceAdapter.notifyDataSetChanged()

                    }
                }
            }

        )
    }

//    private fun loadDatas() {
//        datas.apply {
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "몽글몽글한 테마다 몽글몽글",
//                    tv_library_sentence_sentence_cu_info = "봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리1"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//            add(
//                CuratorInfoSentenceData(
//                    tv_library_sentence_themename_cu_info = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다.",
//                    tv_item_library_sentence_username_cu_info = "예스리"
//                )
//            )
//
//        }
//        curatorInfoSentenceAdapter.data_sen_cu_info = datas
//        curatorInfoSentenceAdapter.notifyDataSetChanged()
//    }
}