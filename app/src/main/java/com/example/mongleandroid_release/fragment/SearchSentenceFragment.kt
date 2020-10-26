package com.example.mongleandroid_release.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.SearchSentenceAdapter
import com.example.mongleandroid_release.network.data.response.SearchSentence
import kotlinx.android.synthetic.main.fragment_search_sentence.*

class SearchSentenceFragment : Fragment() {

    lateinit var searchSentenceAdapter: SearchSentenceAdapter
    var datas = mutableListOf<SearchSentence>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_sentence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchSentenceAdapter = SearchSentenceAdapter(view.context)
        rv_search_sentence.adapter = searchSentenceAdapter
        datas.clear()
        loadDatas()

    }

    private fun loadDatas() {
        datas.apply {
            add(
                SearchSentence(
                    theme = "테마이름",
                    sentence = "그대 떠나가는 그 순간도 그대 나를 걱정했었나요 무엇도 해줄 수 없는 내 맘 앞에서",
                    writer = "현진",
                    saves = "5",
                    likes = "2"
                )
            )
            add(
                SearchSentence(
                    theme = "테마이름",
                    sentence = "그댄 나를 떠나간다해도 난 그댈 보낸 적 없죠",
                    writer = "현진",
                    saves = "5",
                    likes = "2"
                )
            )
            searchSentenceAdapter.datas = datas
            searchSentenceAdapter.notifyDataSetChanged()
        }
    }

}