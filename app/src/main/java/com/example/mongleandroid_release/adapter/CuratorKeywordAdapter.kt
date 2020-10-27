package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.CuratorKeywordViewHolder
import com.example.mongleandroid_release.network.data.response.CuratorKeyword

class CuratorKeywordAdapter(private val context : Context, var datas: List<CuratorKeyword>) : RecyclerView.Adapter<CuratorKeywordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuratorKeywordViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_curator, parent,false)
        return CuratorKeywordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: CuratorKeywordViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}