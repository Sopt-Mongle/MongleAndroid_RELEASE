package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.SearchThemeViewHolder
import com.example.mongleandroid_release.network.data.response.SearchTheme

class WritingSentenceThemeSearchAdapter (private val context: Context, var datas : List<SearchTheme>) : RecyclerView.Adapter<SearchThemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchThemeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_theme, parent, false)
        return SearchThemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchThemeViewHolder, position: Int) {
        return holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}