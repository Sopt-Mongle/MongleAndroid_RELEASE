package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseSentenceBookmarkNumData
import com.example.mongleandroid_release.network.data.response.ResponseSentenceDetailData
import com.example.mongleandroid_release.network.data.response.ResponseSentenceLikeNumData
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.*
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

        constraint_sentence_like_num_notheme.setOnClickListener {
            requestSentenceLike()
        }

        container_bookmark_num_notheme.setOnClickListener {
            requestSentenceBookmark()
        }

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

                        Glide.with(this@SentenceDetailNoThemeActivity).load(response.body()!!.data[0].themeImg)
                            .apply(RequestOptions.bitmapTransform(BlurTransformation(7, 3))).into(sentence_detail_img_notheme) // 테마
                        textView20.text = response.body()!!.data[0].writer // 문장 작성자

                        if(response.body()!!.data[0].thumbnail.isNullOrEmpty()) {
                            Glide.with(this@SentenceDetailNoThemeActivity).load(R.drawable.sentence_theme_o_img_book).into(imageView18) // 문장 작성자
                        } else{
                            Glide.with(this@SentenceDetailNoThemeActivity).load(response.body()!!.data[0].thumbnail).into(imageView18) // 문장 작성자 프사
                        }

                        Glide.with(this@SentenceDetailNoThemeActivity).load(response.body()!!.data[0].thumbnail).into(img_book_thumnail_notheme) // 해당 문장의 책 사진
                        textView35.text = response.body()!!.data[0].title // 책 제목
                        tv_author.text = response.body()!!.data[0].author //  책 저자
                        tv_publisher.text = response.body()!!.data[0].publisher // 출판사

                        tv_sentence_detail_likes_notheme.text = response.body()!!.data[0].likes.toString()
                        if(response.body()!!.data[0].alreadyLiked) { // 좋아요 여부
                            img_sentence_detail_likes_notheme.setImageResource(R.drawable.sentence_btn_btn_like_g)
                        } else {
                            img_sentence_detail_likes_notheme.setImageResource(R.drawable.sentence_theme_o_ic_like)
                        }

                        tv_sentence_detail_bookmark_num_notheme.text = response.body()!!.data[0].saves.toString()
                        if(response.body()!!.data[0].alreadyBookmarked) { // 북마크 여부
                            img_sentence_detail_bookmark_notheme.setImageResource(R.drawable.sentence_btn_btn_bookmark_g)
                        } else {
                            img_sentence_detail_bookmark_notheme.setImageResource(R.drawable.sentence_theme_o_ic_bookmark)
                        }

                        tv_report_no_theme_111.setOnClickListener { // 허위 내용 신고 눌렀을 때!!!
                            val customToast = layoutInflater.inflate(R.layout.toast_report_1, null)
                            val toast = Toast(applicationContext)
                            toast.duration = Toast.LENGTH_SHORT
                            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                            toast.view = customToast
                            toast.show()

                            change_gone(cl_no_theme_report)
                        }

                        tv_report_no_theme_222.setOnClickListener {
                            val customToast = layoutInflater.inflate(R.layout.toast_report_2, null)
                            val toast = Toast(applicationContext)
                            toast.duration = Toast.LENGTH_SHORT
                            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                            toast.view = customToast
                            toast.show()

                            change_gone(cl_no_theme_report)
                        }

                        checkbox_more_btn.setOnClickListener {
                            if(checkbox_more_btn.isChecked) {
                                if(response.body()!!.data[0].writer == SharedPreferenceController.getName(this@SentenceDetailNoThemeActivity)) {
                                    change_visible(ccc_noTheme)
                                } else {
                                    change_visible(cl_no_theme_report)
                                }
                            }
                        }

//                        if (response.body()!!.data[0].writer ==  SharedPreferenceController.getName(this@SentenceDetailNoThemeActivity)) {
//                            img_sentence_detail_view_edit_btn.setOnClickListener {
//                                change_visible(ccc) // 수정 & 삭제 컨테이너
//                            }
//                        } else {
//
//                            change_gone(img_sentence_detail_view_edit_btn)
//
//                        }


                    }
                }

            }
        )
    }

    // 좋아요 통신
    private fun requestSentenceLike() {
        requestToServer.service.PutsentenceLikeNum(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(
            object : Callback<ResponseSentenceLikeNumData> {
                override fun onFailure(call: Call<ResponseSentenceLikeNumData>, t: Throwable) {
                    Log.e("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseSentenceLikeNumData>,
                    response: Response<ResponseSentenceLikeNumData>
                ) {
                    if(response.isSuccessful) {
                        Log.d("통신 성공", "통신 성공")

                        if(response.body()!!.data!!.isLike) {
                            img_sentence_detail_likes_notheme.setImageResource(R.drawable.sentence_btn_btn_like_g)
                            val result : Int = response.body()!!.data!!.likes
                            tv_sentence_detail_likes_notheme.text = result.toString()
                        } else {
                            img_sentence_detail_likes_notheme.setImageResource(R.drawable.sentence_theme_o_ic_like)
                            val result : Int = response.body()!!.data!!.likes
                            tv_sentence_detail_likes_notheme.text = result.toString()
                        }
                    }
                }

            }
        )
    }

    // 문장 북마크 하기
    private fun requestSentenceBookmark() {
        requestToServer.service.PutsentenceBookmarkNum(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(
            object : Callback<ResponseSentenceBookmarkNumData> {
                override fun onResponse(
                    call: Call<ResponseSentenceBookmarkNumData>,
                    response: Response<ResponseSentenceBookmarkNumData>
                ) {
                    if(response.isSuccessful) {
                        if(response.body()!!.data!!.isSave) {
                            img_sentence_detail_bookmark_notheme.setImageResource(R.drawable.sentence_btn_btn_bookmark_g)
                            val result : Int = response.body()!!.data!!.saves
                            tv_sentence_detail_bookmark_num_notheme.text = result.toString()

                            val customToast = layoutInflater.inflate(R.layout.toast_sentence_bookmark, null)
                            val toast = Toast(applicationContext)
                            toast.duration = Toast.LENGTH_SHORT
                            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                            toast.view = customToast
                            toast.show()
                        } else {
                            img_sentence_detail_bookmark_notheme.setImageResource(R.drawable.sentence_theme_o_ic_bookmark)
                            val result : Int = response.body()!!.data!!.saves
                            tv_sentence_detail_bookmark_num_notheme.text = result.toString()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseSentenceBookmarkNumData>, t: Throwable) {
                    Log.e("통신 실패", t.toString())
                }

            }
        )
    }

}