package com.mongle.mongleandroid_release.activity

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
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.DetailThemeAdapter
import com.mongle.mongleandroid_release.change_gone
import com.mongle.mongleandroid_release.change_visible
import com.mongle.mongleandroid_release.dialog.DialogGuest
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.data.request.RequestWritingSentenceData
import com.mongle.mongleandroid_release.network.data.response.ResponseLibraryThemeData
import com.mongle.mongleandroid_release.network.data.response.ResponseThemeBookmarkNumData
import com.mongle.mongleandroid_release.network.data.response.ResponseThemeDetailData
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_detail_theme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailThemeActivity : AppCompatActivity() {

    companion object{
        var writingSentenceInThemeData: RequestWritingSentenceData = RequestWritingSentenceData()
    }

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
            if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest"){
                val dlg = DialogGuest(it.context)
                dlg.start()

            }else{
                val intent = Intent(this@DetailThemeActivity, ThemeWritingSentenceActivity::class.java)
                intent.putExtra("param", tv_main_theme_title.text.toString())

                startActivity(intent)
            }

        }

        requestThemeData()
        requestMainThemeData()

        // 테마 북마크
        btn_detail_theme_bookmark_box.setOnClickListener {


            if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest"){
                val dlg = DialogGuest(it.context)
                dlg.start()

            }else{
                requestThemeBookmark()
            }

        }
        // 테마 더보기 ```눌렀을 때
        checkbox_theme_more_btn.setOnClickListener {
            if(checkbox_theme_more_btn.isChecked) { // 더보기 버튼을 눌렀을 때
                change_visible(cl_report_detailTheme)
            } else {
                change_gone(cl_report_detailTheme)
            }
        }
        // 허위내용신고
        tv_report_theme1.setOnClickListener {
            val customToast = layoutInflater.inflate(R.layout.toast_report_1, null)
            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            toast.view = customToast
            toast.show()
        }
        //부적절한내용신고
        tv_report2222.setOnClickListener {
            val customToast = layoutInflater.inflate(R.layout.toast_report_2, null)
            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            toast.view = customToast
            toast.show()
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

                            // 데이터 세팅
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

                            // 북마크 여부
                            val alreadyBookmarked = response.body()!!.data!!.theme[0].alreadyBookmarked

                            // 내서재 테마 불러와서 비교
                            requestToServer.service.lookLibraryThema(
                                token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) }
                            ).enqueue(object : Callback<ResponseLibraryThemeData> {
                                override fun onResponse(
                                    call: Call<ResponseLibraryThemeData>,
                                    response: Response<ResponseLibraryThemeData>
                                ) {
                                    if(response.isSuccessful) {
                                        val theme_size = response.body()!!.data!!.save.size
                                        if(alreadyBookmarked) {
                                            var save_check = false

                                            for(i in 0 until theme_size) {
                                                // 테마 인덱스가 내가 저장한 테마이면
                                                if(response.body()!!.data!!.save[i].themeIdx
                                                    == intent.getIntExtra("param", 0)) {
                                                    save_check = true
                                                    Log.d("테스트", "저장한 테마")
                                                    break
                                                } else {
                                                    save_check = false
                                                    Log.d("테스트", "저장하지 않은 테마")
                                                }
                                            }

                                            if(save_check) {
                                                img_detail_theme_bookmark.setImageResource(R.drawable.theme_detail_ic_bookmark_on)
                                            } else {
                                                img_detail_theme_bookmark.setImageResource(R.drawable.theme_detail_ic_bookmark_off)
                                            }

                                        } else {
                                            img_detail_theme_bookmark.setImageResource(R.drawable.theme_detail_ic_bookmark_off)
                                        }

                                    }
                                }

                                override fun onFailure(call: Call<ResponseLibraryThemeData>, t: Throwable) {
                                    Log.d("통신 실패", "$t")
                                }

                            })

                            // 해당 테마 인덱스 저장
                            writingSentenceInThemeData.themeIdx = response.body()!!.data!!.theme[0].themeIdx

                            // 테마에 문장 쓰러 가기
//                            img_writing_sentence_in_theme_btn.setOnClickListener {
//                                val intent = Intent(this@DetailThemeActivity, ThemeWritingSentenceActivity::class.java)
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


    // 테마 북마크 통신
    private fun requestThemeBookmark() {

        // 테마 북마크 누르기
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