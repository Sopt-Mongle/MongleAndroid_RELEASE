package com.example.mongleandroid_release.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.SearchThemeAdapter
import com.example.mongleandroid_release.network.data.response.SearchTheme
import kotlinx.android.synthetic.main.fragment_search_theme.*

class SearchThemeFragment : Fragment() {

    lateinit var searchThemeAdapter: SearchThemeAdapter
    var datas = mutableListOf<SearchTheme>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_theme, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchThemeAdapter = SearchThemeAdapter(view.context)
        rv_search_theme.adapter = searchThemeAdapter
        datas.clear()
        loadDatas()

    }

    private fun loadDatas() {
        datas.apply {
            add(
                SearchTheme(
                    themeIdx = 1,
                    theme = "삶에 지쳐 위태롭고 비틀거릴 때 문장, 누군가에게 기대고만 싶을 때 보면 좋은 문장 ",
                    saves = 14,
                    sentenceNum = 16
                )
            )
            add(
                SearchTheme(
                    themeIdx = 2,
                    theme = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해지는 문장",
                    saves = 14,
                    sentenceNum = 16
                )
            )
            searchThemeAdapter.datas = datas
            searchThemeAdapter.notifyDataSetChanged()
        }
    }
}