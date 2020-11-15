package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.LibraryCuratorAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.LibraryCuratorData
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

//        libraryCuratorAdapter = LibraryCuratorAdapter(view.context)
//        rv_library_curator.adapter = libraryCuratorAdapter
//        loadDatas()

        requestLibraryCuratorData()

    }


//    private fun loadDatas() {
//        libraryCuratorData.apply {
//
//            add(
//                LibraryCuratorData(
//                    library_curator_img_profile = "https://cdn11.bigcommerce.com/s-p64yd5j10d/products/2233/images/2262/RAL-6019-Pastel-Green__95839.1505828303.500.750.jpg?c=2",
//                    tv_library_curator_name = "예스리",
//                    tv_library_curator_subcount = "15",
//                    tv_library_curator_keyword = "피엠예슬"
//                )
//            )
//            add(
//                LibraryCuratorData(
//                    library_curator_img_profile = "https://cdn11.bigcommerce.com/s-p64yd5j10d/products/2233/images/2262/RAL-6019-Pastel-Green__95839.1505828303.500.750.jpg?c=2",
//                    tv_library_curator_name = "예스리",
//                    tv_library_curator_subcount = "15",
//                    tv_library_curator_keyword = "피엠예슬"
//                )
//            )
//            add(
//                LibraryCuratorData(
//                    library_curator_img_profile = "https://cdn11.bigcommerce.com/s-p64yd5j10d/products/2233/images/2262/RAL-6019-Pastel-Green__95839.1505828303.500.750.jpg?c=2",
//                    tv_library_curator_name = "예스리",
//                    tv_library_curator_subcount = "15",
//                    tv_library_curator_keyword = "피엠예슬"
//                )
//            )
//            add(
//                LibraryCuratorData(
//                    library_curator_img_profile = "https://cdn11.bigcommerce.com/s-p64yd5j10d/products/2233/images/2262/RAL-6019-Pastel-Green__95839.1505828303.500.750.jpg?c=2",
//                    tv_library_curator_name = "예스리",
//                    tv_library_curator_subcount = "15",
//                    tv_library_curator_keyword = "피엠예슬"
//                )
//            )
//            add(
//                LibraryCuratorData(
//                    library_curator_img_profile = "https://cdn11.bigcommerce.com/s-p64yd5j10d/products/2233/images/2262/RAL-6019-Pastel-Green__95839.1505828303.500.750.jpg?c=2",
//                    tv_library_curator_name = "예스리",
//                    tv_library_curator_subcount = "15",
//                    tv_library_curator_keyword = "피엠예슬"
//                )
//            )
//            add(
//                LibraryCuratorData(
//                    library_curator_img_profile = "https://cdn11.bigcommerce.com/s-p64yd5j10d/products/2233/images/2262/RAL-6019-Pastel-Green__95839.1505828303.500.750.jpg?c=2",
//                    tv_library_curator_name = "예스리",
//                    tv_library_curator_subcount = "15",
//                    tv_library_curator_keyword = "피엠예슬"
//                )
//            )
//            add(
//                LibraryCuratorData(
//                    library_curator_img_profile = "https://cdn11.bigcommerce.com/s-p64yd5j10d/products/2233/images/2262/RAL-6019-Pastel-Green__95839.1505828303.500.750.jpg?c=2",
//                    tv_library_curator_name = "예스리",
//                    tv_library_curator_subcount = "15",
//                    tv_library_curator_keyword = "피엠예슬"
//                )
//            )
//
//
//
//
//        }
//        libraryCuratorAdapter.data_cu = libraryCuratorData
//        libraryCuratorAdapter.notifyDataSetChanged()
//    }

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
                            libraryCuratorAdapter = LibraryCuratorAdapter(view!!.context, response.body()!!.data)
                            rv_library_curator.adapter = libraryCuratorAdapter
                            libraryCuratorAdapter.notifyDataSetChanged()
//                        response.body().let { body->
//
//                        }

                        }

                    }
                }
            )
    }

}
