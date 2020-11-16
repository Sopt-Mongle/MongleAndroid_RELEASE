package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseSentenceDetailData
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.back_btn
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.ccc
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.imageView18
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.imageView20
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.img_sentence_detail_view_edit_btn
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.textView19
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.textView20
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.textView35
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.tv_author
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.tv_publisher
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.tv_theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SentenceDetailNoThemeActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_detail_no_theme)

        // 뒤로 가기 버튼
        back_btn.setOnClickListener {
            finish()
        }

        requestSentenceData() // 문장 상세보기 뷰 통신
    }

    // 문장 상세보기 뷰 통신
    private fun requestSentenceData() {
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

                        if(response.body()!!.data[0].writerImg.isNullOrEmpty()) {
                            Log.d("이미지 제대로 오냐?", response.body()!!.data.toString())
                            Glide.with(this@SentenceDetailNoThemeActivity).load(R.drawable.detailview_img_profile).into(imageView18) // 문장 작성자
                        } else{
                            Glide.with(this@SentenceDetailNoThemeActivity).load(response.body()!!.data[0].writerImg).into(imageView18) // 문장 작성자 프사
                        }

                        //Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data?.thumbnail).into(imageView13) // 테마
                        textView20.text = response.body()!!.data[0].writer // 문장 작성자

                        if(response.body()!!.data[0].thumbnail.isNullOrEmpty()) {
                            Glide.with(this@SentenceDetailNoThemeActivity).load(R.drawable.sentence_theme_o_img_book).into(imageView20) // 문장 작성자
                        } else{
                            Glide.with(this@SentenceDetailNoThemeActivity).load(response.body()!!.data[0].thumbnail).into(imageView20) // 문장 작성자 프사
                        }

                        //Glide.with(this@SentenceDetailNoThemeActivity).load(response.body()!!.data[0].thumbnail).into(imageView20) // 해당 문장의 책 사진
                        textView35.text = response.body()!!.data[0].title // 책 제목
                        tv_author.text = response.body()!!.data[0].author //  책 저자
                        tv_publisher.text = response.body()!!.data[0].publisher // 출판사

                        if (response.body()!!.data[0].writer ==  SharedPreferenceController.getName(this@SentenceDetailNoThemeActivity)) {
                            img_sentence_detail_view_edit_btn.setOnClickListener {
                                change_visible(ccc) // 수정 & 삭제 컨테이너
                            }
                        } else {

                            change_gone(img_sentence_detail_view_edit_btn)

                        }


                    }
                }

            }
        )
    }
}