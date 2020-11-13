package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.CuratorInfoActivity
import com.example.mongleandroid_release.activity.DetailThemeActivity
import com.example.mongleandroid_release.activity.SentenceDetailViewActivity
import com.example.mongleandroid_release.adapter.MainHotThemeAdapter
import com.example.mongleandroid_release.adapter.MainNowHotCuratorAdapter
import com.example.mongleandroid_release.adapter.MainPagerAdapter
import com.example.mongleandroid_release.adapter.TodaySentenceAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.MainThemes
import com.example.mongleandroid_release.network.data.response.ResponseMainHotThemeData
import com.example.mongleandroid_release.network.data.response.ResponseMainNowHotData
import com.example.mongleandroid_release.network.data.response.ResponseTodaySentenceData
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {

//    private var themeIdx : Int,
//    private var theme : String,
//    private var themeImg : String,
//    private var themeImgIdx : Int,
//    private var saves : Int,
//    private var alreadyBookmarked : Boolean,
//    private var sentenceNum : Int

    private var themeIdx = 0
    private var theme = ""
    private var themeImg = ""
    private var saves = 0
    private var alreadyBookmarked = false
    private var sentenceNum = 0

    lateinit var items: MutableList<MainThemes>

    val requestToServer = RequestToServer

    private lateinit var todaySentenceAdapter: TodaySentenceAdapter
    private lateinit var mainNowHotCuratorAdapter: MainNowHotCuratorAdapter
    private lateinit var mainHotThemeAdapter: MainHotThemeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //뷰페이저 연결
        vp_main.adapter = MainPagerAdapter(childFragmentManager)
        vp_main.offscreenPageLimit = 2
        tl_main.setupWithViewPager(vp_main)

        requestTodaySentenceData() // 오늘의 문장 통신
        requestMainCurators() // 지금 인기있는 큐레이터 통신
        requestSavedTheme() // 오늘 가장 많이 저장된 테마 통신
        requestWaitThemes() // 문장을 기다리고 있는 테마 통신
        requestMainNowThemes() // 요즘 사람들이 많이 본 테마 통신

        img_main_search_btn.setOnClickListener {
            replaceFragment(SearchFragment())
        }

    }
    private fun init() {


    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.main_activity_fg, fragment)
        transaction.commit()

    }

    //오늘의 문장 통신
    private fun requestTodaySentenceData() {

        requestToServer.service.RequestMainSentences(
            token = SharedPreferenceController.getAccessToken(requireView().context)
        ).enqueue(
            object : Callback<ResponseTodaySentenceData> {
                override fun onFailure(
                    call: Call<ResponseTodaySentenceData>,
                    t: Throwable
                ) {
                    Log.d( "통신실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseTodaySentenceData>,
                    response: Response<ResponseTodaySentenceData>
                ) {
                    if (response.isSuccessful) {
                        todaySentenceAdapter = TodaySentenceAdapter(response.body()!!.data, view!!.context)
                        main_fragment_rv_today_sentence.adapter = todaySentenceAdapter
                        todaySentenceAdapter.notifyDataSetChanged()

                        //오늘의 문장 리사이클러뷰 아이템 클릭리스너 등록
                        todaySentenceAdapter.setItemClickListener(object : TodaySentenceAdapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                Log.d("SSS","${position}번 리스트 선택")
                                activity?.let{
                                    val intent = Intent(context, SentenceDetailViewActivity::class.java)
                                    intent.putExtra("param", response.body()!!.data[position].sentenceIdx)
                                    startActivity(intent)
                                }
                            }
                        })
                    }
                }

            }
        )

    }

    //인기있는 큐레이터 통신
    private fun requestMainCurators() {

        requestToServer.service.GetMainQurators().enqueue(
            object : Callback<ResponseMainNowHotData> {

                override fun onResponse(
                    call: Call<ResponseMainNowHotData>,
                    response: Response<ResponseMainNowHotData>
                ) {
                    if (response.isSuccessful) {
                        mainNowHotCuratorAdapter = MainNowHotCuratorAdapter(response.body()!!.data, view!!.context)
                        rv_main_now_hot_curator.adapter = mainNowHotCuratorAdapter
                        mainNowHotCuratorAdapter.notifyDataSetChanged()

                        //큐레이터 리사이클러뷰 아이템 클릭리스너 등록
                        mainNowHotCuratorAdapter.setItemClickListener(object : MainNowHotCuratorAdapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                Log.d("SSS","${position}번 리스트 선택")
                                activity?.let{
                                    val intent = Intent(context, CuratorInfoActivity::class.java)
                                    intent.putExtra("param", response.body()!!.data[position].curatorIdx)
                                    startActivity(intent)
                                }
                            }
                        })
                    }
                }

                override fun onFailure(call: Call<ResponseMainNowHotData>, t: Throwable) {

                }

            }
        )

    }

    //오늘 가장 많이 저장된 테마 통신
    private fun requestSavedTheme() {

        requestToServer.service.GetMainThemes(
            token = SharedPreferenceController.getAccessToken(requireView().context)
        ).enqueue(
            object : Callback<ResponseMainHotThemeData> {
                override fun onFailure(call: Call<ResponseMainHotThemeData>, t: Throwable) {
                    Log.d( "통신실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseMainHotThemeData>,
                    response: Response<ResponseMainHotThemeData>
                ) {
                    if (response.isSuccessful) {
                        mainHotThemeAdapter = MainHotThemeAdapter(response.body()!!.data, view!!.context)
                        rv_main_hot_theme.adapter = mainHotThemeAdapter
                        mainHotThemeAdapter.notifyDataSetChanged()

                        mainHotThemeAdapter.setItemClickListener(object : MainHotThemeAdapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                Log.d("SSS","${position}번 리스트 선택")
                                activity?.let{
                                    //var mainThemes = arrayListOf<MainThemes>()
                                    val intent = Intent(context, DetailThemeActivity::class.java)
                                    Log.d("ㅇㅇㅇㅇㅇ", "${response.body()!!.data[0].themeIdx}")

//                                    for (i in 0..5) {
//                                        intent.putExtra("param", response.body()!!.data[i].themeIdx)
//                                    }
                                    intent.putExtra("param", response.body()!!.data[position].themeIdx)
                                    //intent.putExtra("param", position)
                                    //intent.putParcelableArrayListExtra("mainThemes", mainThemes)
                                    startActivity(intent)
                                }

                            }
                        })
                    }
                }
            }

        )

    }

    // 문장을 기다리고 있는 테마 통신
    private fun requestWaitThemes() {
        requestToServer.service.GetMainWaitThemes(
            token = SharedPreferenceController.getAccessToken(requireView().context)
        ).enqueue(
            object : Callback<ResponseMainHotThemeData> {
                override fun onFailure(call: Call<ResponseMainHotThemeData>, t: Throwable) {
                    Log.d( "통신실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseMainHotThemeData>,
                    response: Response<ResponseMainHotThemeData>
                ) {
                    if (response.isSuccessful) {
                        mainHotThemeAdapter = MainHotThemeAdapter(response.body()!!.data, view!!.context)
                        rv_main_waiting_for_sentence_theme.adapter = mainHotThemeAdapter
                        mainHotThemeAdapter.notifyDataSetChanged()

                        mainHotThemeAdapter.setItemClickListener(object : MainHotThemeAdapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                Log.d("SSS","${position}번 리스트 선택")
                                activity?.let{
                                    val intent = Intent(context, DetailThemeActivity::class.java)
                                    Log.d("문장을 기다리는 테마", "${response.body()!!.data[position].themeIdx}")
                                    intent.putExtra("param", response.body()!!.data[position].themeIdx)
                                    startActivity(intent)
                                }
                            }
                        })
                    }
                }

            }
        )
    }

    //요즘 사람들이 많이 본 테마
    private fun requestMainNowThemes() {
        requestToServer.service.GetMainNowThemes(
            token = SharedPreferenceController.getAccessToken(requireView().context)
        ).enqueue(
            object : Callback<ResponseMainHotThemeData> {
                override fun onFailure(call: Call<ResponseMainHotThemeData>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ResponseMainHotThemeData>,
                    response: Response<ResponseMainHotThemeData>
                ) {
                    if (response.isSuccessful) {
                        mainHotThemeAdapter = MainHotThemeAdapter(response.body()!!.data, view!!.context)
                        rv_viewed_a_lot_time_theme.adapter = mainHotThemeAdapter
                        mainHotThemeAdapter.notifyDataSetChanged()

                        mainHotThemeAdapter.setItemClickListener(object : MainHotThemeAdapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                Log.d("SSS","${position}번 리스트 선택")
                                activity?.let{
                                    val intent = Intent(context, DetailThemeActivity::class.java)
                                    intent.putExtra("param", response.body()!!.data[position].themeIdx)
                                    startActivity(intent)
                                }
                            }
                        })
                    }
                }

            }
        )
    }
}