package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.SearchSentenceViewHolder
import com.example.mongleandroid_release.network.data.response.SearchSentence

class SearchSentenceAdapter(private val context : Context) : RecyclerView.Adapter<SearchSentenceViewHolder>() {

    var datas = mutableListOf<SearchSentence>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSentenceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_sentence, parent, false)
        return SearchSentenceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchSentenceViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}