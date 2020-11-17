package com.example.mongleandroid_release.activity

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.CuratorInfoPagerAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseCuratorFollowedData
import com.example.mongleandroid_release.network.data.response.ResponseCuratorInformationData
import kotlinx.android.synthetic.main.activity_curator_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CuratorInfoActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    lateinit var curatorInfoPagerAdapter: CuratorInfoPagerAdapter

    var data_sentence_num : Int = 0
    var data_theme_num : Int = 0

    companion object {
        var params = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curator_info)

        // 이미지 둥글게
        img_curator_profile.background = ShapeDrawable(OvalShape())
        img_curator_profile.clipToOutline = true

        requestCuratorProfile()
        requestCuratorInfoThemeData()
        requestCuratorInfoSentenceData()

        vp_curator_info.adapter = CuratorInfoPagerAdapter(supportFragmentManager)
        vp_curator_info.offscreenPageLimit = 1

//        loadDatas()

        //sticky header
        main_scroll_view_cura_info.run {
            header = cl_titleLayout
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }


        cb_curator_subs_info.setOnClickListener {
            if (cb_curator_subs_info.isChecked) {
                cb_curator_subs_info.setText("구독중")
            } else cb_curator_subs_info.setText("구독")

        }



        tv_thema_num_cu_info.setOnClickListener {
            tv_thema_num_cu_info.setTextColor(Color.parseColor("#73c088"))
            tv_thema_cu_info.setTextColor(Color.parseColor("#73c088"))
            tv_sentence_num_cu_info.setTextColor(Color.parseColor("#bcbcbc"))
            tv_sentence_cu_info.setTextColor(Color.parseColor("#bcbcbc"))

            //버튼 클릭 시 페이지 넘김
            var current = vp_curator_info.currentItem
            current = 0
            if (current == 0){
                vp_curator_info.setCurrentItem(0, false)

            }
            else{
//                vp_curator_info.setCurrentItem(current-1, false)

            }

        }

        tv_thema_cu_info.setOnClickListener {
            tv_thema_num_cu_info.setTextColor(Color.parseColor("#73c088"))
            tv_thema_cu_info.setTextColor(Color.parseColor("#73c088"))
            tv_sentence_num_cu_info.setTextColor(Color.parseColor("#bcbcbc"))
            tv_sentence_cu_info.setTextColor(Color.parseColor("#bcbcbc"))

            //버튼 클릭 시 페이지 넘김
            var current = vp_curator_info.currentItem
            current = 0
            if (current == 0){
                vp_curator_info.setCurrentItem(0, false)
            }
            else{
//                vp_curator_info.setCurrentItem(current-1, false)

            }

        }

        tv_sentence_num_cu_info.setOnClickListener {
            tv_sentence_num_cu_info.setTextColor(Color.parseColor("#73c088"))
            tv_sentence_cu_info.setTextColor(Color.parseColor("#73c088"))
            tv_thema_num_cu_info.setTextColor(Color.parseColor("#bcbcbc"))
            tv_thema_cu_info.setTextColor(Color.parseColor("#bcbcbc"))

            //버튼 클릭 시 페이지 넘김
            var current = vp_curator_info.currentItem
            current = 10
            if (current == 10){
                vp_curator_info.setCurrentItem(current-1, false)
            }
            else{
//                vp_curator_info.setCurrentItem(current-1, false)

            }

        }

        tv_sentence_cu_info.setOnClickListener {
            tv_sentence_num_cu_info.setTextColor(Color.parseColor("#73c088"))
            tv_sentence_cu_info.setTextColor(Color.parseColor("#73c088"))
            tv_thema_num_cu_info.setTextColor(Color.parseColor("#bcbcbc"))
            tv_thema_cu_info.setTextColor(Color.parseColor("#bcbcbc"))

            //버튼 클릭 시 페이지 넘김
            var current = vp_curator_info.currentItem
            current = 10
            if (current == 10){
                vp_curator_info.setCurrentItem(current-1, false)
            }
            else{
//                vp_curator_info.setCurrentItem(current-1, false)

            }

        }

        //뒤로 가기 버튼 눌렀을 때 이전 화면으로 돌아가게 만들
        img_back_curator_info.setOnClickListener {
            finish()
        }



    }

    private fun requestCuratorProfile() {
        params = intent.getIntExtra("param", 0)
        requestToServer.service.CuratorInformation(
            token = applicationContext?.let { SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)
//            params = 13

                ).enqueue(
            object : Callback<ResponseCuratorInformationData> {
                override fun onResponse(
                    call: Call<ResponseCuratorInformationData>,
                    response: Response<ResponseCuratorInformationData>
                ) {
                    if (response.isSuccessful) {
                        Log.d("큐레이터 상세보기 조회 성공", "${response.body()}")

                        Glide.with(this@CuratorInfoActivity).load(response.body()!!.data!!.profile[0].img).into(img_curator_profile)
                        tv_curator_username.text = response.body()!!.data!!.profile[0].name
                        tx_curators_contents.text = response.body()!!.data!!.profile[0].introduce
                        tx_curators_keyword.text = response.body()!!.data!!.profile[0].keyword
                        cb_curator_subs_info.isChecked = response.body()!!.data!!.profile[0].alreadySubscribed
                        if(cb_curator_subs_info.isChecked) {
                            cb_curator_subs_info.text = "구독중"
                        } else {
                            cb_curator_subs_info.text = "구독"
                        }


//                        if (response.body()!!.data?.profile.isNullOrEmpty()) {
//
//                        } else {
//                            Glide.with(this@CuratorInfoActivity).load(response.body()!!.data!!.profile[0].img).into(img_curator_profile)
//
//                        }

                        // 구독여부 통신
                        cb_curator_subs_info.setOnClickListener {
                            requestToServer.service.getFollowIdx(
                                token = SharedPreferenceController.getAccessToken(applicationContext),
                                params = response.body()!!.data!!.profile[0].curatorIdx
                            ).enqueue(object : Callback<ResponseCuratorFollowedData> {
                                override fun onFailure(call: Call<ResponseCuratorFollowedData>, t: Throwable) {
                                    Log.e("통신실패", t.toString())
                                }

                                override fun onResponse(
                                    call: Call<ResponseCuratorFollowedData>,
                                    response: Response<ResponseCuratorFollowedData>
                                ) {
                                    if (response.isSuccessful) {
                                        if(response.body()!!.data) {
                                            Log.d("구독", "구독")
                                            cb_curator_subs_info.text = "구독중"
                                        } else {
                                            Log.d("구독", "구독취소")
                                            cb_curator_subs_info.text = "구독"
                                        }
                                    }

                                }
                            })
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseCuratorInformationData>, t: Throwable) {
                    Log.e("큐레이터 상세보기 통신 실패", "${t}")
                }

            }
        )
    }

    private fun requestCuratorInfoThemeData() {
        requestToServer.service.CuratorInformation(
            token = SharedPreferenceController.getAccessToken(this),
            params = intent.getIntExtra("param", 0)

        ).enqueue(
            object : Callback<ResponseCuratorInformationData> {
                override fun onFailure(call: Call<ResponseCuratorInformationData>, t: Throwable) {
                    Log.d("통신실패", "${t}")
                }

                override fun onResponse(
                    call: Call<ResponseCuratorInformationData>,
                    response: Response<ResponseCuratorInformationData>
                ) {
                    if(response.isSuccessful) {
                        Log.d("큐레이터 상세보기 테마 조회 성공", "${response.body()}")

                        data_theme_num = response.body()!!.data!!.theme.size
                        tv_thema_num_cu_info.setText(data_theme_num.toString())


                        }

                    }
            }

        )
    }

    private fun requestCuratorInfoSentenceData() {
        requestToServer.service.CuratorInformation(
            token = SharedPreferenceController.getAccessToken(this),
//            params = 13
            params = intent.getIntExtra("param", 0)

        ).enqueue(
            object : Callback<ResponseCuratorInformationData> {
                override fun onFailure(call: Call<ResponseCuratorInformationData>, t: Throwable) {
                    Log.d("통신실패", "${t}")
                }

                override fun onResponse(
                    call: Call<ResponseCuratorInformationData>,
                    response: Response<ResponseCuratorInformationData>
                ) {
                    if(response.isSuccessful) {
                        Log.d("큐레이터 상세보기 조회 성공", "${response.body()}")

                        data_sentence_num = response.body()!!.data!!.sentence.size
                        tv_sentence_num_cu_info.setText(data_sentence_num.toString())

                    }
                }
            }

        )
    }


//    private fun loadDatas() {
//        data.apply {
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "번아웃을 극복하기 위해 봐야하는 문장",
//                    thema_num_library_cu_info = "107",
//                    sentence_count_library_item_cu_info = "15"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔했던 봄을 생각하면 마음이 섬찟해지는 문장",
//                    thema_num_library_cu_info = "107",
//                    sentence_count_library_item_cu_info = "15"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "개발이 잘 안될 때 심신안정을 위해 봐야하는 문장",
//                    thema_num_library_cu_info = "509",
//                    sentence_count_library_item_cu_info = "15"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "번아웃을 극복하기 위해 봐야하는 문장",
//                    thema_num_library_cu_info = "107",
//                    sentence_count_library_item_cu_info = "15"
//                )
//            )
//            add(
//                CuratorInfoThemaData(
//                    thema_cu_info = "번아웃을 극복하기 위해 봐야하는 문장",
//                    thema_num_library_cu_info = "107",
//                    sentence_count_library_item_cu_info = "15"
//                )
//            )
//        }
//    }

}