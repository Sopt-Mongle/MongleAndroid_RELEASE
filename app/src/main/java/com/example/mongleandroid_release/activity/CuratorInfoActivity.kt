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
import com.example.mongleandroid_release.fragment.CuratorInfoThemaFragment
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseCuratorInformationData
import kotlinx.android.synthetic.main.activity_curator_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CuratorInfoActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    lateinit var curatorInfoPagerAdapter: CuratorInfoPagerAdapter

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


                    }
                }

                override fun onFailure(call: Call<ResponseCuratorInformationData>, t: Throwable) {
                    Log.e("큐레이터 상세보기 통신 실패", "${t}")
                }

            }
        )
    }



}