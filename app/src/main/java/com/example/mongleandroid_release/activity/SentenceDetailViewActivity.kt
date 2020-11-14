package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.DetailSentenceAdapter
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
//import com.example.mongleandroid_release.adapter.DetailSentenceAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseSentenceDetailData
import com.example.mongleandroid_release.network.data.response.ResponseSentenceDetailOtherThemeData
import kotlinx.android.synthetic.main.activity_sentence_detail_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SentenceDetailViewActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    private lateinit var detailSentenceAdapter: DetailSentenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_detail_view)

        btn_go_to_theme.setOnClickListener {
            val intent = Intent(this, DetailThemeActivity::class.java)
            startActivity(intent)
        }
        img_sentence_detail_view_edit_btn.setOnClickListener {
            change_visible(ccc) // 수정 & 삭제 컨테이너
        }

        requestSentenceDetail()
        requestSentenceTheme()
    }
    // 문장 상세보기 데이터 서버 통신
    private fun requestSentenceDetail() {
        requestToServer.service.GetDetailSentence(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(
            object : Callback<ResponseSentenceDetailData> {
                override fun onFailure(call: Call<ResponseSentenceDetailData>, t: Throwable) {
                    Log.e("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseSentenceDetailData>,
                    response: Response<ResponseSentenceDetailData>
                ) {
                    if(response.isSuccessful) {


                        Log.d("통신성공", response.body()!!.data.toString())
                        tv_theme.text = response.body()!!.data[0].theme // 해당 테마 제목
                        textView19.text = response.body()!!.data[0].sentence // 해당 테마의 문장
                        Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data[0].writerImg).into(imageView18) // 문장 작성자 프사
                        //Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data?.thumbnail).into(imageView13) // 테마
                        textView20.text = response.body()!!.data[0].writer // 문장 작성자
                        Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data[0].thumbnail).into(imageView20) // 해당 문장의 책 사진
                        textView35.text = response.body()!!.data[0].title // 책 제목
                        tv_author.text = response.body()!!.data[0].author //  책 저자
                        tv_publisher.text = response.body()!!.data[0].publisher // 출판사

                            if (response.body()!!.data[0].writer ==  SharedPreferenceController.getName(this@SentenceDetailViewActivity)) {

                            } else {
                                img_sentence_detail_view_edit_btn.setOnClickListener {
                                    change_gone(img_sentence_detail_view_edit_btn)
                                }
                            }


                    }
                }

            }
        )
    }

    // 이 테마의 다른 문장 리사이클러뷰 통신
    private fun requestSentenceTheme() {

        requestToServer.service.GetDetailSentenceOtherThemeSentence(
            params = intent.getIntExtra("param", 0)
        ).enqueue(
            object : Callback<ResponseSentenceDetailOtherThemeData> {
                override fun onFailure(call: Call<ResponseSentenceDetailOtherThemeData>, t: Throwable) {
                    Log.e("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseSentenceDetailOtherThemeData>,
                    response: Response<ResponseSentenceDetailOtherThemeData>
                ) {
                    if (response.isSuccessful) {
                        Log.d("테마 통신 성공", "${response.body()!!.data}")

                        if (response.body()!!.data.isNullOrEmpty()) {

                        } else {

                            if(response.body()!!.data[0].likes == 0) {
                                Log.d("테스트", "0이어서 문제야")
                            }

                            detailSentenceAdapter =
                                DetailSentenceAdapter(response.body()!!.data, applicationContext)
                            rv_sentence_detail_view_theme_other_sentence.adapter =
                                detailSentenceAdapter
                            detailSentenceAdapter.notifyDataSetChanged()

                            detailSentenceAdapter.setItemClickListener(object : DetailSentenceAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    Log.d("SSS", "${position}번 리스트 선택")

                                }
                            })

                        }
                    }
                }
            }
        )

    }

}