package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.SearchRecentAdapter
import com.example.mongleandroid_release.showKeyboard
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        search_fragment_et_search.requestFocus()
        search_fragment_et_search.showKeyboard() // 확장함수 showKeyboard.kt

        // 엔터 누르면 프레그먼트 이동
        search_fragment_et_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //replaceFragment()
                return@OnKeyListener true
            }
            false
        })

        // 검색 버튼 누르면 프레그먼트 이동
        search_fragment_btn_search.setOnClickListener {
            //search_fragment_tv_no_keyword.visibility = GONE // ?? 이따 실행시켜보기
            //replaceFragment()
        }

        // 최근 키워드 전체 삭제
        search_fragment_tv_delete.setOnClickListener {
            search_fragment_rv_recent_keyword.visibility = GONE

            // 네트워크 부분

            search_fragment_tv_no_keyword.visibility = VISIBLE
        }

    }



}