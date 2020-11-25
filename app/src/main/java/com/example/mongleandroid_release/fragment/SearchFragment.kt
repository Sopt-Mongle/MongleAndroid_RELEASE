package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.mongleandroid_release.*
import com.example.mongleandroid_release.activity.MainActivity.Companion.search_result
import com.example.mongleandroid_release.adapter.SearchRecentAdapter
import com.example.mongleandroid_release.adapter.SearchTabAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseSearchRecentData
import com.example.mongleandroid_release.network.data.response.ResponseSearchRecentDeleteData
import com.example.mongleandroid_release.network.data.response.ResponseSearchRecommendData
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    lateinit var searchRecentAdapter: SearchRecentAdapter

    private val requestToServer = RequestToServer

    private var state = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        recentKeyword()
        recommendKeyword()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onResume() {
        super.onResume()

        if(state == 1) {
            fragment_search_et_search.setText(search_result)
            hideFocus()
        } else {
            fragment_search_et_search.setText("")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_search_cl_before.setOnClickListener {
            fragment_search_et_search.unshowKeyboard()
        }

        // 검색창에 초점 맞추고, 키보드 올리는 부분
        fragment_search_et_search.requestFocus()
        fragment_search_et_search.showKeyboard() // 확장함수 showKeyboard.kt

        // 검색창에 초점 맞았을 때
        fragment_search_et_search.setOnClickListener {
            fragment_search_et_search.background = resources.getDrawable(R.drawable.et_area_green, null)
            fragment_search_et_search.isCursorVisible = true
        }

        fragment_search_et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                fragment_search_et_search.background = resources.getDrawable(R.drawable.et_area_green, null)
                fragment_search_et_search.isCursorVisible = true
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        // 뒤로가기 버튼 - 메인으로 이동
        fragment_search_btn_back_main.setOnClickListener {
            replaceFragment(MainFragment())
            fragment_search_et_search.unshowKeyboard()
        }

        // 뒤로가기 버튼 - 원래 검색화면으로 이동
        fragment_search_btn_back_search.setOnClickListener {
            change_gone(fragment_search_cl_after)
            change_visible(fragment_search_cl_before)

            fragment_search_et_search.background = resources.getDrawable(R.drawable.et_area_green, null)
            fragment_search_et_search.isCursorVisible = true
            fragment_search_et_search.showKeyboard()

            change_gone(fragment_search_btn_back_search)
            change_visible(fragment_search_btn_back_main)

            if(fragment_search_et_search.text.isNotEmpty()) {
                fragment_search_tv_no_keyword.visibility = GONE
            }
            recentKeyword()
        }

        // 엔터 눌렀을 때 검색
        fragment_search_et_search.setOnKeyListener(View.OnKeyListener { view, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                hideFocus()

                search_result = if (view == null) {
                    val searchword = " "
                    searchword.trim()
                } else {
                    val searchword = fragment_search_et_search.text.toString()
                    searchword.trim()
                }

                // 검색 결과로 이동
                goResult()

                return@OnKeyListener true
            }
            false
        })

        // 검색 버튼
        fragment_search_btn_search.setOnClickListener {
            hideFocus()

            search_result = if(fragment_search_et_search == null) {
                val searchword = " "
                searchword.trim()
            } else {
                val searchword = fragment_search_et_search.text.toString()
                searchword.trim()
            }

            // 검색 결과로 이동
            goResult()

        }


    }

    override fun onStop() {
        super.onStop()
        state = 1
    }

    override fun onDestroy() {
        super.onDestroy()
        state = 2
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.main_activity_fg, fragment)
        transaction.commit()

    }

    private fun hideFocus() {
        fragment_search_et_search.background = resources.getDrawable(R.drawable.et_area, null)
        fragment_search_et_search.isCursorVisible = false
    }

    // 최근 키워드
    private fun recentKeyword() {
        requestToServer.service.requestSearchRecent(
            token = context?.let { SharedPreferenceController.getAccessToken(it) }
        ).enqueue(
            object : Callback<ResponseSearchRecentData> {
                override fun onFailure(call: Call<ResponseSearchRecentData>, t: Throwable) {
                    Log.d("통신실패", "$t")
                }

                override fun onResponse(
                    call: Call<ResponseSearchRecentData>,
                    response: Response<ResponseSearchRecentData>
                ) {
                    if (response.isSuccessful) {
                        if(response.body()!!.data.isNullOrEmpty()) {
                            fragment_search_tv_no_keyword.visibility = VISIBLE
                        } else {
                            removeRecentKeyword()
                            val layoutManager = LinearLayoutManager(view!!.context)
                            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                            fragment_search_rv_recent_keyword.layoutManager = layoutManager

                            searchRecentAdapter = SearchRecentAdapter(view!!.context)
                            fragment_search_rv_recent_keyword.adapter = searchRecentAdapter
                            searchRecentAdapter.datas = response.body()!!.data
                            searchRecentAdapter.notifyDataSetChanged()

                            searchRecentAdapter.setItemClickListener(
                                object : SearchRecentAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    // 선택한 최근 검색어로 검색
                                    goResult()
                                    val searchword = response.body()!!.data[position]
                                    search_result = searchword.trim()

                                    fragment_search_et_search.setText(searchword)
                                    hideFocus()
                                }

                            })
                        }
                        Log.d("최근 검색어", response.body().toString())
                    }

                }
            }
        )

    }

    // 최근 키워드 전체 삭제
    private fun removeRecentKeyword() {
        fragment_search_tv_delete.setOnClickListener {
            fragment_search_rv_recent_keyword.visibility = GONE

            requestToServer.service.requestSearchRecentDelete(
                token = context?.let { SharedPreferenceController.getAccessToken(it) }
            ).enqueue(
                object : Callback<ResponseSearchRecentDeleteData> {
                    override fun onFailure(call: Call<ResponseSearchRecentDeleteData>, t: Throwable) {
                        Log.d("통신실패", "$t")
                    }

                    override fun onResponse(
                        call: Call<ResponseSearchRecentDeleteData>,
                        response: Response<ResponseSearchRecentDeleteData>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("최근 검색어 삭제", response.body()!!.message)
                            fragment_search_rv_recent_keyword.adapter = searchRecentAdapter
                            searchRecentAdapter.notifyDataSetChanged()
                        }
                    }
                }
            )

            fragment_search_tv_no_keyword.visibility = VISIBLE
        }
    }


    // 추천 키워드
    private fun recommendKeyword() {

        requestToServer.service.getRecommendKeyword().enqueue(
            object : Callback<ResponseSearchRecommendData> {
                override fun onFailure(call: Call<ResponseSearchRecommendData>, t: Throwable) {
                    Log.d("통신실패", "$t")
                }

                override fun onResponse(
                    call: Call<ResponseSearchRecommendData>,
                    response: Response<ResponseSearchRecommendData>
                ) {
                    if (response.isSuccessful) {
                        Log.d("추천 키워드", response.body()!!.message)

                        var recommend_size = response.body()!!.data.size - 1

                        for (i in 0..recommend_size) {
                            val resId = resources.getIdentifier(
                                "@id/tv_recommend_keyword${i + 1}",
                                "id",
                                activity!!.packageName
                            )
                            val tv = view!!.findViewById(resId) as TextView?
                            tv?.text = response.body()!!.data[i].toString()
                            tv?.setOnClickListener {
                                // 선택한 추천 검색어로 검색
                                goResult()
                                val searchword = response.body()!!.data[i].toString()
                                search_result = searchword.trim()

                                fragment_search_et_search.setText(searchword)
                                hideFocus()
                            }
                        }

                    }

                }
            }
        )

    }

    // 검색결과로 이동
    fun goResult() {

        hideFocus()

        change_gone(fragment_search_btn_back_main)
        change_visible(fragment_search_btn_back_search)

        // 키보드 내리는 부분
        fragment_search_et_search.unshowKeyboard()

        fragment_search_cl_before.visibility = GONE
        fragment_search_cl_after.visibility = VISIBLE

        // tablayout 배치
        val resultTabLayout = view?.findViewById(R.id.search_result_tab) as TabLayout
        val resultViewPager = view?.findViewById(R.id.search_result_viewpager) as ViewPager
        val resultTabAdapter = SearchTabAdapter(childFragmentManager)

        resultViewPager.adapter = resultTabAdapter
        resultViewPager.offscreenPageLimit = 1
        resultTabAdapter.notifyDataSetChanged()

        resultTabLayout.setupWithViewPager(resultViewPager)
        resultTabLayout.getTabAt(0)!!.text = "테마"
        resultTabLayout.getTabAt(1)!!.text = "문장"
        resultTabLayout.getTabAt(2)!!.text = "큐레이터"

    }


}