package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.DetailThemeActivity
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.data.response.ResponseEditorsPickData
import kotlinx.android.synthetic.main.fragment_main_view_pager1.*
import kotlinx.android.synthetic.main.fragment_main_view_pager2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewPager1Fragment : Fragment() {

    private val requestToServer = RequestToServer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestToServer.service.RequestEditorsPick()
            .enqueue(object : Callback<ResponseEditorsPickData> {
                override fun onResponse(
                    call: Call<ResponseEditorsPickData>,
                    response: Response<ResponseEditorsPickData>
                ) {
                    if(response.isSuccessful) {
                        if(response.body()!!.data.isNullOrEmpty()) {

                        } else {
                            Glide.with(view!!).load(response.body()!!.data[0].illust).into(img_main_editor_pick_theme1)
                            main1_sentence_num.text = "문장 " + response.body()!!.data[0].sentenceNum + "개"
                            img_main_editor_pick_theme1.setOnClickListener {
                                activity?.let {
                                    val intent = Intent(context, DetailThemeActivity::class.java)
                                    intent.putExtra("param", response.body()!!.data[0].themeIdx)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseEditorsPickData>, t: Throwable) {
                    Log.d("통신실패", "$t")
                }

            })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_view_pager1, container, false)
    }
}