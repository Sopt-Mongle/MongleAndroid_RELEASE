package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.SearchCuratorViewHolder
import com.example.mongleandroid_release.network.data.response.SearchCurator

class SearchCuratorAdapter(private val context : Context) : RecyclerView.Adapter<SearchCuratorViewHolder>() {

    var datas = mutableListOf<SearchCurator>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCuratorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_curator, parent, false)
        return SearchCuratorViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchCuratorViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}