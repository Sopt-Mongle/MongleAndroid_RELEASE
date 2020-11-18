package com.example.mongleandroid_release.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.DetailThemeActivity.Companion.writingSentenceInThemeData
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.request.RequestWritingSentenceData
import com.example.mongleandroid_release.network.data.response.ResponseWritingSentenceData
import kotlinx.android.synthetic.main.activity_theme_writing_sentence_book.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class ThemeWritingSentenceBookActivity : AppCompatActivity() {

    val requestToServer = RequestToServer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_writing_sentence_book)

        // 나가기
        activity_theme_writing_sentence_book_btn_out.setOnClickListener {
            finish()
        }

        // 책 추가
        activity_theme_writing_sentence_book_tv_title.setOnClickListener {
            val intent = Intent(this, ThemeWritingSentenceBookSearchActivity::class.java)
            startActivityForResult(intent, 100)
            //이거 아마 startActivityForResult로 바꿔야 함!!
        }

        // 책 제목 text가 없는 경우 경고창
        activity_theme_writing_sentence_book_btn_upload.setOnClickListener {
            if (activity_theme_writing_sentence_book_tv_title.text.toString().isEmpty()) {
                activity_theme_writing_sentence_book_tv_title.background = getResources().getDrawable(R.drawable.et_area_red)
                activity_theme_writing_sentence_book_img_warning.visibility = View.VISIBLE
                activity_theme_writing_sentence_book_tv_warning.visibility = View.VISIBLE
            } else {
                // 있는 경우
                // 다음 액티비티로 이동 (문장 등록 끝)

                DetailThemeActivity.writingSentenceInThemeData.title = activity_theme_writing_sentence_book_tv_title.text.toString()
                DetailThemeActivity.writingSentenceInThemeData.author = activity_theme_writing_sentence_book_v_author.text.toString()
                DetailThemeActivity.writingSentenceInThemeData.publisher = activity_theme_writing_sentence_book_v_publisher.text.toString()
                //DetailThemeActivity.writingSentenceInThemeData.thumbnail =

                activity_theme_writing_sentence_book_tv_title.background = getResources().getDrawable(R.drawable.et_area)
                activity_theme_writing_sentence_book_img_warning.visibility = View.GONE
                activity_theme_writing_sentence_book_tv_warning.visibility = View.GONE
                sentenceInThemePost()
                Log.d("테마에 문장쓰기 :: ", "${DetailThemeActivity.writingSentenceInThemeData.sentence} \n" +
                        " ${DetailThemeActivity.writingSentenceInThemeData.author} \n" +
                        " ${DetailThemeActivity.writingSentenceInThemeData.publisher} \n" +
                        " ${DetailThemeActivity.writingSentenceInThemeData.themeIdx} \n" +
                        " ${DetailThemeActivity.writingSentenceInThemeData.title} \n"
                )
                val intent = Intent(this, ThemeWritingSentenceFinishActivity::class.java )
                startActivity(intent)

            }
        }

        // 다음 버튼 : 테마에 문장쓰기 업로드
//        activity_theme_writing_sentence_book_btn_upload.setOnClickListener {
//            // 빈칸 경고
//            if(activity_theme_writing_sentence_book_tv_title.text.isNullOrEmpty()) {
//                activity_theme_writing_sentence_book_img_warning.visibility = View.VISIBLE
//                activity_theme_writing_sentence_book_tv_warning.visibility = View.VISIBLE
//            } else{
//                // 서버 통신
//                //sentenceInThemePost()
//                WritingSentenceInThemeActivity.writingSentenceInThemeData.title = activity_theme_writing_sentence_book_tv_title.text.toString()
//                WritingSentenceInThemeActivity.writingSentenceInThemeData.author = activity_theme_writing_sentence_book_v_author.text.toString()
//                WritingSentenceInThemeActivity.writingSentenceInThemeData.publisher = activity_theme_writing_sentence_book_v_publisher.text.toString()
//
//                val intent = Intent(this, ThemeWritingSentenceFinishActivity::class.java )
//                startActivity(intent)
//            }
//
//        }
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 100) {
                if( resultCode == 1) {
                    val title = data!!.getStringExtra("title")
                    val author = data!!.getStringExtra("author")
                    val publisher = data!!.getStringExtra("publisher")

                    activity_theme_writing_sentence_book_tv_title.setText(title)
                    activity_theme_writing_sentence_book_v_author.setText(author)
                    activity_theme_writing_sentence_book_v_publisher.setText(publisher)

                }
            }
        }

    private fun sentenceInThemePost() {
        requestToServer.service.RequestWritingSentence(
                token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
                body = DetailThemeActivity.writingSentenceInThemeData)
        .enqueue(object : Callback<ResponseWritingSentenceData> {
            @SuppressLint("LongLogTag")
            override fun onFailure(call: Call<ResponseWritingSentenceData>, t: Throwable) {
                Log.e("ResponseWritingSentenceData 통신실패",t.toString())
            }
            @SuppressLint("LongLogTag")
            override fun onResponse(
                    call: Call<ResponseWritingSentenceData>,
                    response: Response<ResponseWritingSentenceData>
            ) {
                if (response.isSuccessful){
                    response.body().let { body ->
                        Log.e("ResponseWritingSentenceData 통신응답바디", "status: ${body!!.status} message : ${body.message} data : ${body.data}\"")
                    }
                }

            }

        })
    }
}