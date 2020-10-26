package com.example.mongleandroid_release.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.SearchCuratorAdapter
import com.example.mongleandroid_release.network.data.response.SearchCurator
import kotlinx.android.synthetic.main.fragment_search_curator.*


class SearchCuratorFragment : Fragment() {

    lateinit var searchCuratorAdapter: SearchCuratorAdapter
    var datas = mutableListOf<SearchCurator>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_curator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchCuratorAdapter = SearchCuratorAdapter(view.context)
        rv_search_curator.adapter = searchCuratorAdapter
        datas.clear()
        loadDatas()

    }

    private fun loadDatas() {
        datas.apply {
            add(
                SearchCurator(
                    name = "이름1",
                    keyword = "키워드",
                    subscribe = 53,
                    alreadySubscribed = false
                )
            )
            add(
                SearchCurator(
                    name = "이름2",
                    keyword = "키워드22",
                    subscribe = 43,
                    alreadySubscribed = true
                )
            )
            searchCuratorAdapter.datas = datas
            searchCuratorAdapter.notifyDataSetChanged()
        }
    }
}