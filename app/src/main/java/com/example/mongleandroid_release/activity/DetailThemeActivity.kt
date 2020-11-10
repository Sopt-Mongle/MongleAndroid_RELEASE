package com.example.mongleandroid_release.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.DetailThemeAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseThemeDetailData
import kotlinx.android.synthetic.main.activity_detail_theme.*
import retrofit2.Call
import retrofit2.Response

class DetailThemeActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    private lateinit var detailThemeAdapter: DetailThemeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_theme)

        img_detail_theme_activity_back_btn.setOnClickListener {
            finish()
        }

//        img_writing_sentence_in_theme_btn.setOnClickListener {
//            val intent = Intent(this, ThemeWritingSentenceActivity::class.java)
//            startActivity(intent)
//        }

        requestThemeData()
        requestMainThemeData()
    }

    private fun requestThemeData() {
        requestToServer.service.GetDetailTheme(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(
            object : retrofit2.Callback<ResponseThemeDetailData> {
                override fun onFailure(call: Call<ResponseThemeDetailData>, t: Throwable) {
                    Log.e("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseThemeDetailData>,
                    response: Response<ResponseThemeDetailData>
                ) {
                    if(response.isSuccessful) {
                        tv_main_theme_title.text = response.body()!!.data!!.theme[0].theme
                        tv_main_theme_author.text = response.body()!!.data!!.theme[0].writer
                        textView12.text = response.body()!!.data!!.theme[0].sentenceNum.toString()
                        textView11.text = response.body()!!.data!!.theme[0].saves.toString()
                    }
                }

            }
        )
    }

    private fun requestMainThemeData() {

        requestToServer.service.GetDetailTheme(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(
            object : retrofit2.Callback<ResponseThemeDetailData> {


                override fun onFailure(call: Call<ResponseThemeDetailData>, t: Throwable) {
                    Log.e("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseThemeDetailData>,
                    response: Response<ResponseThemeDetailData>
                ) {
                    if(response. isSuccessful) {
                        Log.d("테마 통신 성공", "${response.body()!!.data}")
                        //rv_activity_theme.adapter = mainThemeAdapter

                        detailThemeAdapter = DetailThemeAdapter( response.body()!!.data!!.sentence , applicationContext)
                        rv_activity_theme.adapter = detailThemeAdapter
                        detailThemeAdapter.notifyDataSetChanged()

                        //리사이클러뷰 아이템 클릭리스너 등록
                        detailThemeAdapter.setItemClickListener(object : DetailThemeAdapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                Log.d("SSS","${position}번 리스트 선택")
                                val intent = Intent(this@DetailThemeActivity, SentenceDetailViewActivity::class.java)
                                startActivity(intent)
                            }
                        })
                    }
                }
            })
    }

}