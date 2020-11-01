package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.MainHotThemeAdapter
import com.example.mongleandroid_release.adapter.MainNowHotCuratorAdapter
import com.example.mongleandroid_release.adapter.MainPagerAdapter
import com.example.mongleandroid_release.adapter.TodaySentenceAdapter
import com.example.mongleandroid_release.network.data.response.ResponseMainHotThemeData
import com.example.mongleandroid_release.network.data.response.ResponseMainNowHotData
import com.example.mongleandroid_release.network.data.response.ResponseTodaySentenceData
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private var data = mutableListOf<ResponseTodaySentenceData>() // 오늘의 문장
    private var data2 = mutableListOf<ResponseMainNowHotData>() // 지금 인기있는 큐레이터
    private var data3 = mutableListOf<ResponseMainHotThemeData>() // 테마

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

        setHotThemeAdapter(data3) // 인기있는 테마 리사이클러뷰
        setHotCuratorAdapter(data2) // 지금 인기있는 큐레이터 리사이클러뷰
        setAdatodaySentenceAdapterpter(data)//오늘의 문장 리사이클러뷰

        img_main_search_btn.setOnClickListener {
            replaceFragment(SearchFragment())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.main_activity_fg, fragment)
        transaction.commit()

    }

        private fun setHotThemeAdapter(mainHotThemeItem: MutableList<ResponseMainHotThemeData>) {
        mainHotThemeAdapter =
            MainHotThemeAdapter(
                mainHotThemeItem,
                this.requireContext()
            )

        rv_main_hot_theme.adapter = mainHotThemeAdapter
        rv_main_waiting_for_sentence_theme.adapter = mainHotThemeAdapter
        rv_viewed_a_lot_time_theme.adapter = mainHotThemeAdapter


        mainHotThemeAdapter.setItemClickListener(object : MainHotThemeAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Log.d("SSS","${position}번 리스트 선택")
                activity?.let{
//                    val intent = Intent(context, MainThemeActivity::class.java)
//                    startActivity(intent)
                }
            }
        })
    }

//지금 인기있는 큐레이터 어댑터 연결
    private fun setHotCuratorAdapter(mainNowHotCuratorItem: MutableList<ResponseMainNowHotData>) {
        mainNowHotCuratorAdapter =
            MainNowHotCuratorAdapter(
                mainNowHotCuratorItem,
                this.requireContext()
            )

        rv_main_now_hot_curator.adapter = mainNowHotCuratorAdapter

        //리사이클러뷰 아이템 클릭리스너 등록
        mainNowHotCuratorAdapter.setItemClickListener(object : MainNowHotCuratorAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Log.d("SSS","${position}번 리스트 선택")
            }
        })
    }



     //오늘의 문장 어댑터 연결
    private fun setAdatodaySentenceAdapterpter(todaySentenceItem : MutableList<ResponseTodaySentenceData>) {
         todaySentenceAdapter =
            TodaySentenceAdapter(
                todaySentenceItem,
                this.requireContext()
            )
        main_fragment_rv_today_sentence.adapter = todaySentenceAdapter

        //오늘의 문장 리사이클러뷰 아이템 클릭리스너 등록
        todaySentenceAdapter.setItemClickListener(object : TodaySentenceAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Log.d("SSS","${position}번 리스트 선택")
                activity?.let{
//                    val intent = Intent(context, SentenceDetailViewActivity::class.java)
//                    startActivity(intent)
                }
            }
        })

    }

}