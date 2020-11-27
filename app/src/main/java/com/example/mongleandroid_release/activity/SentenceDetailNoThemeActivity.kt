package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import com.example.mongleandroid_release.dialog.DialogDeleteSentence
import com.example.mongleandroid_release.dialog.DialogGuest
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.*
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.*
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.back_btn
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.imageView18
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.textView19_notheme
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.textView20
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.textView35
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.tv_author
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.tv_publisher
import kotlinx.android.synthetic.main.activity_sentence_detail_no_theme.tv_theme
import kotlinx.android.synthetic.main.activity_sentence_detail_view.*
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
            if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest"){

                val dlg = DialogGuest(it.context)
                dlg.start()

            }else{
                requestSentenceLike() // 문장 북마크 누르기
            }

        }

        container_bookmark_num_notheme.setOnClickListener {
            if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest"){

                val dlg = DialogGuest(it.context)
                dlg.start()

            }else{
                requestSentenceBookmark()// 문장 북마크 누르기
            }

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
                        textView19_notheme.text = response.body()!!.data[0].sentence // 해당 테마의 문장

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
                            Glide.with(this@SentenceDetailNoThemeActivity).load(R.drawable.sentence_theme_o_img_book).into(img_book_thumnail_notheme) // 문장 작성자
                        } else{
                            Glide.with(this@SentenceDetailNoThemeActivity).load(response.body()!!.data[0].thumbnail).into(img_book_thumnail_notheme) // 문장 작성자 프사
                        }

                        //Glide.with(this@SentenceDetailNoThemeActivity).load(response.body()!!.data[0].thumbnail).into(img_book_thumnail_notheme) // 해당 문장의 책 사진
                        textView35.text = response.body()!!.data[0].title // 책 제목
                        tv_author.text = response.body()!!.data[0].author //  책 저자
                        tv_publisher.text = response.body()!!.data[0].publisher // 출판사
                        tv_sentence_detail_likes_notheme.text = response.body()!!.data[0].likes.toString() // 좋아요 수
                        if(response.body()!!.data[0].alreadyLiked) { // 좋아요 여부
                            img_sentence_detail_likes_notheme.setImageResource(R.drawable.sentence_btn_btn_like_g)
                        } else {
                            img_sentence_detail_likes_notheme.setImageResource(R.drawable.sentence_theme_o_ic_like)
                        }

                        tv_sentence_detail_bookmark_num_notheme.text = response.body()!!.data[0].saves.toString() // 북마크 수


                        // 북마크 여부
                        val alreadyBookmarked = response.body()!!.data[0].alreadyBookmarked

                        // 내서재 문장 불러와서 비교
                        requestToServer.service.lookLibrarySentence(
                            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) }
                        ).enqueue(object : Callback<ResponseLibrarySentenceData> {
                            override fun onResponse(
                                call: Call<ResponseLibrarySentenceData>,
                                response: Response<ResponseLibrarySentenceData>
                            ) {
                                if(response.isSuccessful) {
                                    val sentence_size = response.body()!!.data!!.save.size

                                    if(alreadyBookmarked) {
                                        var save_check = false

                                        for(i in 0 until sentence_size) {
                                            if(response.body()!!.data!!.save[i].sentenceIdx
                                                == intent.getIntExtra("param", 0)) {
                                                save_check = true
                                                break
                                            } else {
                                                save_check = false
                                            }
                                        }

                                        if(save_check) {
                                            img_sentence_detail_bookmark_notheme.setImageResource(R.drawable.sentence_btn_btn_bookmark_g)
                                        } else {
                                            img_sentence_detail_bookmark_notheme.setImageResource(R.drawable.sentence_theme_o_ic_bookmark)
                                        }
                                    } else {
                                        img_sentence_detail_bookmark_notheme.setImageResource(R.drawable.sentence_theme_o_ic_bookmark)
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<ResponseLibrarySentenceData>,
                                t: Throwable
                            ) {
                                Log.d("통신 실패", "$t")
                            }

                        })

                        // 수정 기능
                        edit_noTheme.setOnClickListener {
                            val intent = Intent(this@SentenceDetailNoThemeActivity,ModifyLibraryWrittenSentenceActivity::class.java)
                            intent.putExtra("sentence", textView19_notheme.text.toString()) // 해당 문장 보내기
                            intent.putExtra("param", response.body()!!.data[0].sentenceIdx) // sentneceIdx 넘기기
                            startActivity(intent)
                        }
                        // 삭제 기능
                        delete_noTheme.setOnClickListener {
                            val dlg = DialogDeleteSentence(this@SentenceDetailNoThemeActivity)
                            dlg.start()
                            dlg.setOnClickListener { content ->
                                if (content == "삭제") {
                                    requestToServer.service.DeleteSentenceWritten(
                                        token = SharedPreferenceController.getAccessToken(
                                            applicationContext
                                        ),
                                        params = response.body()!!.data[0].sentenceIdx

                                    ).enqueue(
                                        object :
                                            Callback<ResponseDeleteSentenceWritten> {
                                            override fun onResponse(
                                                call: Call<ResponseDeleteSentenceWritten>,
                                                response: Response<ResponseDeleteSentenceWritten>
                                            ) {
                                                if (response.isSuccessful) {
                                                    //액티비티 나가기
                                                    finish()


                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<ResponseDeleteSentenceWritten>,
                                                t: Throwable
                                            ) {
                                                Log.d("문장 삭제 통신 실패", "$t")

                                            }

                                        }
                                    )
                                }

                            }
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
                        // 부적절한 내용 신고
                        tv_report_no_theme_222.setOnClickListener {
                            val customToast = layoutInflater.inflate(R.layout.toast_report_2, null)
                            val toast = Toast(applicationContext)
                            toast.duration = Toast.LENGTH_SHORT
                            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                            toast.view = customToast
                            toast.show()

                            change_gone(cl_no_theme_report)
                        }
                        // 더보기 ``` 눌렀을 때
                        checkbox_more_btn.setOnClickListener {
                            if(checkbox_more_btn.isChecked) {
                                if(response.body()!!.data[0].writer == SharedPreferenceController.getName(this@SentenceDetailNoThemeActivity)) {
                                    change_visible(ccc_noTheme)
                                } else {
                                    change_visible(cl_no_theme_report)
                                }
                            } else { // 다시 더보기 ``` 눌렀을 때 수정/삭제/신고 박스 사라지게 하기
                                change_gone(ccc_noTheme)
                                change_gone(cl_no_theme_report)
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