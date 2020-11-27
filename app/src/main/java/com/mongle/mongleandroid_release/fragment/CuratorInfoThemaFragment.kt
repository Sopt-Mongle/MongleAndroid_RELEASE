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
import com.mongle.mongleandroid_release.activity.DetailThemeActivity
import com.mongle.mongleandroid_release.adapter.CuratorInfoThemaAdapter
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.data.response.ResponseCuratorInformationData
import kotlinx.android.synthetic.main.fragment_curator_info_thema.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CuratorInfoThemaFragment() : Fragment() {

    val requestToServer = RequestToServer

    lateinit var curatorInfoThemaAdapter: CuratorInfoThemaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curator_info_thema, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        curatorInfoThemaAdapter = CuratorInfoThemaAdapter(view.context)
//        rv_thema_cu_info.adapter = curatorInfoThemaAdapter
//        loadDatas()
        requestCuratorInfoThemeData()
    }

    private fun requestCuratorInfoThemeData() {
        requestToServer.service.CuratorInformation(
            token = context?.let { SharedPreferenceController.getAccessToken(it) },
            params = params
        ).enqueue(
            object : Callback<ResponseCuratorInformationData> {
                override fun onFailure(call: Call<ResponseCuratorInformationData>, t: Throwable) {
                    Log.d("통신실패 테마", "${t}")
                }

                override fun onResponse(
                    call: Call<ResponseCuratorInformationData>,
                    response: Response<ResponseCuratorInformationData>
                ) {
                    if(response.isSuccessful) {
                        Log.d("큐레이터 상세보기 테마 성공", "${response.body()!!.data!!.theme}")
                        if(response.body()!!.data!!.theme.isNullOrEmpty()) {
                            Log.d("큐레이터 테마 널 테스트", "null ???")
                        } else {
                            curatorInfoThemaAdapter = CuratorInfoThemaAdapter(view!!.context, response.body()!!.data!!.theme)
                            rv_thema_cu_info.adapter = curatorInfoThemaAdapter
                            curatorInfoThemaAdapter.notifyDataSetChanged()

                            // 테마 상세보기로 이동
                            curatorInfoThemaAdapter.setItemClickListener(object :
                                CuratorInfoThemaAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    activity?.let {
                                        val intent = Intent(context, DetailThemeActivity::class.java)
                                        intent.putExtra("param", response.body()!!.data!!.theme[position].themeIdx)
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

//    private fun loadDatas() {
//        curatorInfoThemadatas.apply {
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "삶에 지쳐 위태롭고 비틀거릴 때, 누군가에게 \n" + "기대고만 싶을 때 보면 좋은 문장",
//                    thema_num_library_cu_info = "123",
//                    sentence_count_library_item_cu_info = "14"
//                )
//            )
//
//        }
//        curatorInfoThemaAdapter.data_the_cu_info = curatorInfoThemadatas
//        curatorInfoThemaAdapter.notifyDataSetChanged()
//    }
}