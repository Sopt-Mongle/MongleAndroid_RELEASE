package com.mongle.mongleandroid_release.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.DetailSentenceAdapter
import com.mongle.mongleandroid_release.change_gone
import com.mongle.mongleandroid_release.change_visible
import com.mongle.mongleandroid_release.dialog.DialogDeleteSentence
import com.mongle.mongleandroid_release.dialog.DialogGuest
//import com.example.mongleandroid_release.adapter.DetailSentenceAdapter
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.data.ResponseReportSentence
import com.mongle.mongleandroid_release.network.data.request.RequestReportSentence
import com.mongle.mongleandroid_release.network.data.response.*
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sentence_detail_view.*
import kotlinx.android.synthetic.main.activity_sentence_detail_view.back_btn
import kotlinx.android.synthetic.main.activity_sentence_detail_view.ccc
import kotlinx.android.synthetic.main.activity_sentence_detail_view.imageView18
import kotlinx.android.synthetic.main.activity_sentence_detail_view.img_book_thumnail
import kotlinx.android.synthetic.main.activity_sentence_detail_view.textView19
import kotlinx.android.synthetic.main.activity_sentence_detail_view.textView20
import kotlinx.android.synthetic.main.activity_sentence_detail_view.textView35
import kotlinx.android.synthetic.main.activity_sentence_detail_view.tv_author
import kotlinx.android.synthetic.main.activity_sentence_detail_view.tv_publisher
import kotlinx.android.synthetic.main.activity_sentence_detail_view.tv_theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SentenceDetailViewActivity : AppCompatActivity() {

    private var sentenceIdx: Int = 0

    val requestToServer = RequestToServer

    companion object{
        var reportSentence : RequestReportSentence = RequestReportSentence()
    }

    private lateinit var detailSentenceAdapter: DetailSentenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_detail_view)

        sentenceIdx = intent.getIntExtra("param",0)

        // 뒤로 가기 버튼
        back_btn.setOnClickListener {
            finish()
        }

        requestSentenceDetail() // 문장 상세보기 데이터 서버 통신
        requestSentenceTheme() // 이 테마의 다른 문장 리사이클러뷰 통신
        constraint_likes_num.setOnClickListener {
            if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest"){

                val dlg = DialogGuest(it.context)
                dlg.start()

            }else{
                requestSentenceLikeNum() // 문장 좋아요 누르기
            }
        }
        container_bookmark_num.setOnClickListener {
            if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest"){

                val dlg = DialogGuest(it.context)
                dlg.start()

            }else{
                requestSentenceBookmarkNum() // 문장 북마크 누르기
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestSentenceDetail()
    }

    // 문장 상세보기 데이터 서버 통신
    private fun requestSentenceDetail() {
        requestToServer.service.GetDetailSentence(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = sentenceIdx
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
                        Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data[0].themeImg)
                            .apply(RequestOptions.bitmapTransform(BlurTransformation(7, 3))).into(sentence_detail_img_theme) // 테마이미지
                        tv_theme.text = response.body()!!.data[0].theme // 해당 테마 제목
                        textView19.text = response.body()!!.data[0].sentence // 해당 테마의 문장
                        if(response.body()!!.data[0].writerImg == null) {
                            Glide.with(this@SentenceDetailViewActivity).load(R.drawable.detailview_img_profile).into(imageView18) // 문장 작성자
                        } else{
                            Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data[0].writerImg).into(imageView18) // 문장 작성자 프사
                        }
                        textView20.text = response.body()!!.data[0].writer // 문장 작성자
                        Glide.with(this@SentenceDetailViewActivity).load(response.body()!!.data[0].thumbnail).into(img_book_thumnail) // 해당 문장의 책 사진
                        textView35.text = response.body()!!.data[0].title // 책 제목
                        tv_author.text = response.body()!!.data[0].author //  책 저자
                        tv_publisher.text = response.body()!!.data[0].publisher // 출판사
                        tv_sentence_likes_num.text = response.body()!!.data[0].likes.toString() // 좋아요 수
                        if(response.body()!!.data[0].alreadyLiked) { // 좋아요 여부
                            img_sentence_detail_likes_num.setImageResource(R.drawable.sentence_btn_btn_like_g)
                        } else {
                            img_sentence_detail_likes_num.setImageResource(R.drawable.sentence_theme_o_ic_like)
                        }
                        tv_sentence_bookmark_num.text = response.body()!!.data[0].saves.toString() // 북마크 수


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
                                            if(response.body()!!.data!!.save[i].sentenceIdx == sentenceIdx) {
                                                save_check = true
                                                break
                                            } else {
                                                save_check = false
                                            }
                                        }

                                        if(save_check) {
                                            img_sentence_detail_bookmark_num.setImageResource(R.drawable.sentence_btn_btn_bookmark_g)
                                        } else {
                                            img_sentence_detail_bookmark_num.setImageResource(R.drawable.sentence_theme_o_ic_bookmark)
                                        }
                                    } else {
                                        img_sentence_detail_bookmark_num.setImageResource(R.drawable.sentence_theme_o_ic_bookmark)
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

                        // 테마 보러 가기 버튼
                        btn_go_to_theme.setOnClickListener {
                            val intent = Intent(this@SentenceDetailViewActivity, DetailThemeActivity::class.java)
                            intent.putExtra("param", response.body()!!.data[0].themeIdx)
                            startActivity(intent)
                        }

                        // 수정 기능
                        edit.setOnClickListener {  // 수정 눌렀을 때 -> 문장 수정하기 액틸비티로 이동
                            val intent = Intent(this@SentenceDetailViewActivity,ModifyLibraryWrittenSentenceActivity::class.java)
                            intent.putExtra("sentence", textView19.text.toString()) // 해당 문장 보내기
                            intent.putExtra("param", response.body()!!.data[0].sentenceIdx) // sentneceIdx 넘기기
                            startActivity(intent)
                            change_gone(ccc)
                        }

                        // 삭제 기능
                        delete.setOnClickListener { // 삭제 눌렀을 때
                            change_gone(ccc)
                            //삭제 버튼 눌렀을 때 통하는 부분
                            val dlg = DialogDeleteSentence(this@SentenceDetailViewActivity)
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
                                                    //삭제 버튼 누르면 해당 액티비티 나가기
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

                        // 허위 내용 신고
                        tv_report1111.setOnClickListener { // 허위 내용 신고 눌렀을 때!!!
                            if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest"){

                                val dlg = DialogGuest(view.context)
                                dlg.start()

                            }else{
                                val customToast = layoutInflater.inflate(R.layout.toast_report_1, null)
                                val toast = Toast(applicationContext)
                                toast.duration = Toast.LENGTH_SHORT
                                toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                                toast.view = customToast
                                toast.show()

                                // 허위내용신고 gone 처리
                                change_gone(cl_report)

                                Log.d("허위내용 신고", "허위내용신고!!!!!!!!!!!!!!!!!!1")
                                reportSentence.sort = "sentence"
                                reportSentence.idx = response.body()!!.data[0].sentenceIdx // 문장 인덱스 넘기기
                                reportSentence.content = "falseAd"
                                requestReportSentence() // 통신
                            }



                        }

                        // 부적절한 내용 신고
                        tv_report2222.setOnClickListener { // 부적절한 내용 신고 눌렀을 때 !!
                            if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest"){
                                val dlg = DialogGuest(view.context)
                                dlg.start()

                            }else{
                                val customToast = layoutInflater.inflate(R.layout.toast_report_2, null)
                                val toast = Toast(applicationContext)
                                toast.duration = Toast.LENGTH_SHORT
                                toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                                toast.view = customToast
                                toast.show()

                                // 허위내용신고 gone 처리
                                change_gone(cl_report)

                                reportSentence.sort = "sentence"
                                reportSentence.idx = response.body()!!.data[0].sentenceIdx // 문장 인덱스 넘기기
                                reportSentence.content = "inappropriate"

                                requestReportSentence() // 통신
                            }
                        }

                        // 더보기 ``` 눌렀을 때
                        more_btn_checkbox.setOnClickListener {

                            if(more_btn_checkbox.isChecked ) { // 더보기 버튼을 눌렀을 때
                                Log.d("체크체크첵첵첵스초코", "체크 되는거임???????????")
                                if (response.body()!!.data[0].writer ==  SharedPreferenceController.getName(this@SentenceDetailViewActivity)) {
                                    change_visible(ccc)
                                    //change_gone(cl_report)
                                } else { // 내가 쓴 글이 아닐 때 -> 신고 뷰 띄우기
                                    change_visible(cl_report)
                                }

                            } else { // 다시 더보기 ``` 눌렀을 때 수정/삭제/신고 박스 사라지게 하기
                                change_gone(ccc)
                                change_gone(cl_report)
                            }

                        }
//                            if (response.body()!!.data[0].writer ==  SharedPreferenceController.getName(this@SentenceDetailViewActivity)) {
//                                more_btn_checkbox. { // 더보기를 눌렀을 때 내가 쓴 글이면 수정/삭제 뷰 보이게 하기
//                                    change_visible(ccc) // 수정 & 삭제 컨테이너
//
//                                }
//                            } else {
//
//                                    change_visible(cl_report)
//
//                            }


//                        constraint_likes_num.setOnClickListener {
//                            if (constraint_likes_num.isChecked) {
//                                img_sentence_detail_likes_num.setImageResource(R.drawable.sentence_btn_btn_like_g)
//                                val likes_num
//                                val likes : Int = tv_sentence_likes_num
//                                tv_sentence_likes_num.text = likes.toString()
//                            } else {
//                                img_sentence_detail_likes_num.setImageResource(R.drawable.sentence_theme_o_ic_like)
//                            }
//                        }


                    }
                }

            }
        )
    }

    // 신고하기 통신
    private fun requestReportSentence() {

        requestToServer.service.ReportSentence(
            token = applicationContext.let { SharedPreferenceController.getAccessToken(it) },
            body = reportSentence
        ).enqueue(object : Callback<ResponseReportSentence> {
            @SuppressLint("신고하기 통신~~~")
            override fun onFailure(call: Call<ResponseReportSentence>, t: Throwable) {
                Log.e("ReportSentence 통신실패",t.toString())            }

            @SuppressLint("신고신고신고")
            override fun onResponse(
                call: Call<ResponseReportSentence>,
                response: Response<ResponseReportSentence>
            ) {
                if(response.isSuccessful) {
                    Log.e("통신응답성공", "신고하기 통신통신")

                }
            }

        }
        )
    }

    // 이 테마의 다른 문장 리사이클러뷰 통신
    private fun requestSentenceTheme() {

        requestToServer.service.GetDetailSentenceOtherThemeSentence(
            params = sentenceIdx
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

                            detailSentenceAdapter =
                                DetailSentenceAdapter(response.body()!!.data, applicationContext)
                            rv_sentence_detail_view_theme_other_sentence.adapter =
                                detailSentenceAdapter
                            detailSentenceAdapter.notifyDataSetChanged()

                            detailSentenceAdapter.setItemClickListener(object : DetailSentenceAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    Log.d("SSS", "${position}번 리스트 선택")
                                   val intent = Intent(this@SentenceDetailViewActivity, SentenceDetailNoThemeActivity::class.java)
                                    intent.putExtra("param", response.body()!!.data[position].sentenceIdx)
                                    startActivity(intent)
                                }
                            })

                        }
                    }
                }
            }
        )

    }

    // 문장 좋아요 누르기
    private fun requestSentenceLikeNum() {
        requestToServer.service.PutsentenceLikeNum(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = sentenceIdx
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
                            img_sentence_detail_likes_num.setImageResource(R.drawable.sentence_btn_btn_like_g)
                            val result : Int = response.body()!!.data!!.likes
                            tv_sentence_likes_num.text = result.toString()
                        } else {
                            img_sentence_detail_likes_num.setImageResource(R.drawable.sentence_theme_o_ic_like)
                            val result : Int = response.body()!!.data!!.likes
                            tv_sentence_likes_num.text = result.toString()
                        }
                    }
                }

            }
        )
    }


    // 문장 북마크 하기
    private fun requestSentenceBookmarkNum() {
        requestToServer.service.PutsentenceBookmarkNum(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = sentenceIdx
        ).enqueue(
            object : Callback<ResponseSentenceBookmarkNumData> {
                override fun onResponse(
                    call: Call<ResponseSentenceBookmarkNumData>,
                    response: Response<ResponseSentenceBookmarkNumData>
                ) {
                    if(response.isSuccessful) {
                        if(response.body()!!.data!!.isSave) {
                            img_sentence_detail_bookmark_num.setImageResource(R.drawable.sentence_btn_btn_bookmark_g)
                            val result : Int = response.body()!!.data!!.saves
                            tv_sentence_bookmark_num.text = result.toString()

                            val customToast = layoutInflater.inflate(R.layout.toast_sentence_bookmark, null)
                            val toast = Toast(applicationContext)
                            toast.duration = Toast.LENGTH_SHORT
                            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                            toast.view = customToast
                            toast.show()
                        } else {
                            img_sentence_detail_bookmark_num.setImageResource(R.drawable.sentence_theme_o_ic_bookmark)
                            val result : Int = response.body()!!.data!!.saves
                            tv_sentence_bookmark_num.text = result.toString()
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

