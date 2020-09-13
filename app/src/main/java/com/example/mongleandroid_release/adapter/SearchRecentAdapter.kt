package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.SearchRecentViewHolder

class SearchRecentAdapter(private val context: Context) : RecyclerView.Adapter<SearchRecentViewHolder>(){
    var datas = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recent_keyword, parent, false)
        return SearchRecentViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: SearchRecentViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}