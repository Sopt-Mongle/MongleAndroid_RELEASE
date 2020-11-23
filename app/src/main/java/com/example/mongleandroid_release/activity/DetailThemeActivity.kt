package com.example.mongleandroid_release.activity

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.DetailThemeAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.request.RequestWritingSentenceData
import com.example.mongleandroid_release.network.data.response.ResponseThemeBookmarkNumData
import com.example.mongleandroid_release.network.data.response.ResponseThemeDetailData
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_detail_theme.*
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailThemeActivity : AppCompatActivity() {

    companion object{
        var writingSentenceInThemeData: RequestWritingSentenceData = RequestWritingSentenceData()
    }

    //DataTheme 데이터
    //private var themeIdx = 0
    private var theme = ""
    private var themeImg = ""
    private var saves = 0
    private var writer = ""
    private var writerImg = ""
    private var alreadyBookmarked = false
    private var sentenceNum = 0

    //DataSentence 데이터1
    private var sentenceIdx = 0
    private var sentence = ""
    private var likes = 0
    private var title = "String"
    private var author = "String"
    private var publisher = "String"
    private var timestamp = "String"
    private var alreadyLiked = false


    val requestToServer = RequestToServer


    private lateinit var detailThemeAdapter: DetailThemeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_theme)

        img_detail_theme_activity_back_btn.setOnClickListener {
            finish()
        }
        // 테마에 문장쓰기 액티비티로 전환하면서 해당테마의 이름 전달해주기
        img_writing_sentence_in_theme_btn.setOnClickListener {
            val intent = Intent(this@DetailThemeActivity, WritingSentenceInThemeActivity::class.java)
            intent.putExtra("param", tv_main_theme_title.text.toString())

            startActivity(intent)
        }

        requestThemeData()
        requestMainThemeData()

        // 테마 북마크
        btn_detail_theme_bookmark_box.setOnClickListener {
            requestThemeBookmark()
        }
    }

    private fun requestThemeData() {
        requestToServer.service.GetDetailTheme(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(
            object : Callback<ResponseThemeDetailData> {
                override fun onFailure(call: Call<ResponseThemeDetailData>, t: Throwable) {
                    Log.e("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseThemeDetailData>,
                    response: Response<ResponseThemeDetailData>
                ) {
                    if(response.isSuccessful) {
                        //var themesData = intent.getParcelableArrayExtra("mainThemes")
                        Log.d("아 제발","${response.body()!!.data!!.theme[0]}번 리스트 선택")

                        if (response.body()!!.data?.theme.isNullOrEmpty()) {

                        } else {
                            // 이미지 둥글게
                            img_detail_theme_writerimg.background = ShapeDrawable(OvalShape())
                            img_detail_theme_writerimg.clipToOutline = true

                            if(response.body()!!.data!!.theme[0].writerImg == null) {
                                Glide.with(this@DetailThemeActivity).load(R.drawable.theme_img_profile).into(img_detail_theme_writerimg)
                            } else {
                                Glide.with(this@DetailThemeActivity).load(response.body()!!.data!!.theme[0].writerImg).into(img_detail_theme_writerimg)
                            }
                            tv_main_theme_title.text = response.body()!!.data!!.theme[0].theme
                            tv_main_theme_author.text = response.body()!!.data!!.theme[0].writer
                            textView12.text = response.body()!!.data!!.theme[0].sentenceNum.toString()
                            textView11.text = response.body()!!.data!!.theme[0].saves.toString()
                            Glide.with(this@DetailThemeActivity).load(response.body()!!.data!!.theme[0].themeImg)
                                .apply(RequestOptions.bitmapTransform(BlurTransformation(7, 3))).into(imageView5)
                            if (response.body()!!.data!!.theme[0].alreadyBookmarked) {
                                img_detail_theme_bookmark.setImageResource(R.drawable.theme_detail_ic_bookmark_on)
                            } else {
                                img_detail_theme_bookmark.setImageResource(R.drawable.theme_detail_ic_bookmark_off)
                            }

                            // 해당 테마 인덱스 저장
                            writingSentenceInThemeData.themeIdx = response.body()!!.data!!.theme[0].themeIdx

                            // 테마에 문장 쓰러 가기
//                            img_writing_sentence_in_theme_btn.setOnClickListener {
//                                val intent = Intent(this@DetailThemeActivity, WritingSentenceInThemeActivity::class.java)
//                                intent.putExtra("param", response.body()!!.data!!.theme[0].themeIdx)
//                            }
                        }
                        //tv_main_theme_title.text = themesData.get(1).toString()
//                        for (i in 0..5) {
//                            tv_main_theme_title.text = response.body()!!.data!!.theme[i].theme
//                            tv_main_theme_author.text = response.body()!!.data!!.theme[i].writer
//                            textView12.text = response.body()!!.data!!.theme[i].sentenceNum.toString()
//                            textView11.text = response.body()!!.data!!.theme[i].saves.toString()
//                        }
                        //val themeIdx = intent.getIntExtra("param", 0)

                    }
                }

            }
        )
    }
// 리사이클러뷰 통신
    private fun requestMainThemeData() {

        requestToServer.service.GetDetailTheme(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(
            object : Callback<ResponseThemeDetailData> {


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
                                val intent = Intent(this@DetailThemeActivity, SentenceDetailNoThemeActivity::class.java)
                                intent.putExtra("param", response.body()!!.data!!.sentence[position].sentenceIdx)
                                startActivity(intent)
                            }
                        })
                    }
                }
            })
    }


    // 리사이클러뷰 아이템 통신
    private fun loadDatas() {
        requestToServer.service.GetDetailTheme(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(object : retrofit2.Callback<ResponseThemeDetailData>{
            override fun onFailure(call: Call<ResponseThemeDetailData>, t: Throwable) {
                Log.e("통신 실패", t.toString())
            }

            override fun onResponse(
                call: Call<ResponseThemeDetailData>,
                response: Response<ResponseThemeDetailData>
            ) {
                if (response.isSuccessful) {
                    Log.d("테마 리사이클러뷰 통신 성공", "${response.body()!!.data}")

                }
            }

        })
    }

    private fun requestThemeBookmark() {
        requestToServer.service.putThemeBookmarkNum(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
        ).enqueue(object : Callback<ResponseThemeBookmarkNumData> {
            override fun onResponse(
                call: Call<ResponseThemeBookmarkNumData>,
                response: Response<ResponseThemeBookmarkNumData>
            ) {
                if(response.isSuccessful) {
                    if(response.body()!!.data!!.isSave) {
                        img_detail_theme_bookmark.setImageResource(R.drawable.theme_detail_ic_bookmark_on)
                        textView11.text = response.body()!!.data!!.saves.toString()

                        val customToast = layoutInflater.inflate(R.layout.toast_theme_bookmark, null)
                        val toast = Toast(applicationContext)
                        toast.duration = Toast.LENGTH_SHORT
                        toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                        toast.view = customToast
                        toast.show()
                    } else {
                        img_detail_theme_bookmark.setImageResource(R.drawable.theme_detail_ic_bookmark_off)
                        textView11.text = response.body()!!.data!!.saves.toString()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseThemeBookmarkNumData>, t: Throwable) {
                Log.e("통신 실패", t.toString())
            }

        })
    }

}