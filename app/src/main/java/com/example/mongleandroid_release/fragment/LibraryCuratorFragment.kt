package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.CuratorInfoActivity
import com.example.mongleandroid_release.adapter.LibraryCuratorAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.LibraryCuratorData
import com.example.mongleandroid_release.network.data.response.ResponseCuratorFollowedData
import com.example.mongleandroid_release.network.data.response.ResponseLibraryCuratorData
import kotlinx.android.synthetic.main.fragment_library_curator.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryCuratorFragment : Fragment() {

    val requestToServer = RequestToServer

    lateinit var libraryCuratorAdapter: LibraryCuratorAdapter
    val libraryCuratorData = mutableListOf<LibraryCuratorData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library_curator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestLibraryCuratorData()

    }

    private fun requestLibraryCuratorData() {
        requestToServer.service.lookLibraryCurator(
            token = SharedPreferenceController.getAccessToken(requireView().context)
        )
            .enqueue(
                object : Callback<ResponseLibraryCuratorData> {
                    override fun onFailure(call: Call<ResponseLibraryCuratorData>, t: Throwable) {
                        Log.d("통신실패", "${t}")
                    }

                    override fun onResponse(
                        call: Call<ResponseLibraryCuratorData>,
                        response: Response<ResponseLibraryCuratorData>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("내 서재 큐레이터 조회", "${response.body()}")
                            if (response.body()!!.data.isNullOrEmpty()) {
                                Log.d("내 서재 테마 null", "${response.body()}")
                            } else {
                            libraryCuratorAdapter =
                                LibraryCuratorAdapter(view!!.context, response.body()!!.data)
                            rv_library_curator.adapter = libraryCuratorAdapter
                            libraryCuratorAdapter.notifyDataSetChanged()

                            libraryCuratorAdapter.setItemClickListener(object :
                                LibraryCuratorAdapter.ItemClickListener {
                                override fun onClickItem(view: View, position: Int) {
                                    // 큐레이터 상세로 이동
                                    val intent = Intent(context, CuratorInfoActivity::class.java)
                                    intent.putExtra(
                                        "param",
                                        response.body()!!.data[position].curatorIdx
                                    )
                                    startActivity(intent)
                                }

                                override fun onClickSubscribe(view: View, position: Int) {
                                    // 구독 클릭했을 때
                                    requestToServer.service.getFollowIdx(
                                        token = context?.let {
                                            SharedPreferenceController.getAccessToken(
                                                it
                                            )
                                        },
                                        params = response.body()!!.data[position].curatorIdx
                                    ).enqueue(object : Callback<ResponseCuratorFollowedData> {
                                        override fun onFailure(
                                            call: Call<ResponseCuratorFollowedData>,
                                            t: Throwable
                                        ) {
                                            Log.e("통신실패", t.toString())
                                        }

                                        override fun onResponse(
                                            call: Call<ResponseCuratorFollowedData>,
                                            response: Response<ResponseCuratorFollowedData>
                                        ) {
                                            if (response.isSuccessful) {

                                                if (response.body()!!.data) {
                                                    Log.d("구독", "구독")
                                                } else {
                                                    Log.d("구독", "구독취소")
                                                }
                                            }

                                        }
                                    })
                                }

                            })
                        }

                        }

                    }
                }
            )
    }

}
