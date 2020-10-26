package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.MainActivity.Companion.search_result
import com.example.mongleandroid_release.adapter.SearchRecentAdapter
import com.example.mongleandroid_release.adapter.SearchTabAdapter
import com.example.mongleandroid_release.showKeyboard
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    lateinit var searchRecentAdapter: SearchRecentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 검색창에 초점 맞추고, 키보드 올리는 부분
        fragment_search_et_search.requestFocus()
        fragment_search_et_search.showKeyboard() // 확장함수 showKeyboard.kt

        // 엔터 누르면 프레그먼트 이동
        fragment_search_et_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {

                return@OnKeyListener true
            }
            false
        })



        // 검색 버튼
        fragment_search_btn_search.setOnClickListener {

            fragment_search_cl_before.visibility = GONE
            fragment_search_cl_after.visibility = VISIBLE

            // tablayout 배치
            val ResultTabLayout = view.findViewById(R.id.search_result_tab) as TabLayout
            val ResultViewPager = view.findViewById(R.id.search_result_viewpager) as ViewPager
            val tab_adapter = SearchTabAdapter(childFragmentManager)
            ResultViewPager.adapter = tab_adapter
            ResultTabLayout.setupWithViewPager(ResultViewPager)
            ResultTabLayout.getTabAt(0)!!.text = "테마"
            ResultTabLayout.getTabAt(1)!!.text = "문장"
            ResultTabLayout.getTabAt(2)!!.text = "큐레이터"

            // 검색어 뷰홀더로 보내주는 부분 (Fragment - MainActivity - ViewHolder)
            val searchword = fragment_search_et_search.text.toString()
            search_result = searchword.trim()

        }

        // 최근 키워드 전체 삭제
        fragment_search_tv_delete.setOnClickListener {
            fragment_search_rv_recent_keyword.visibility = GONE

            // 네트워크 부분

            fragment_search_tv_no_keyword.visibility = VISIBLE
        }

    }



}