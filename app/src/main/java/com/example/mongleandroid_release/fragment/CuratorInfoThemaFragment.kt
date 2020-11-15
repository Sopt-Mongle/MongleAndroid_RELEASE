package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid.adapter.LibraryThemaAdapter
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.CuratorInfoThemaAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.CuratorInfoThemaData
import com.example.mongleandroid_release.network.data.response.ResponseCuratorInformationData
import com.example.mongleandroid_release.network.data.response.ResponseLibraryThemeData
import kotlinx.android.synthetic.main.fragment_curator_info_thema.*
import kotlinx.android.synthetic.main.fragment_library_thema.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CuratorInfoThemaFragment : Fragment() {

    val requestToServer = RequestToServer

    lateinit var curatorInfoThemaAdapter: CuratorInfoThemaAdapter
    val curatorInfoThemadatas = mutableListOf<CuratorInfoThemaData>()

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

                        curatorInfoThemaAdapter = CuratorInfoThemaAdapter(view!!.context, response.body()!!.data!!.theme)
                        rv_thema_cu_info.adapter = curatorInfoThemaAdapter
                        curatorInfoThemaAdapter.notifyDataSetChanged()

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