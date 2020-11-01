package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.ThemeData

class WritingSentenceThemeSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val item_search_theme_tv_themeTitle = itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_title)

    fun bind(writingSentenceThemeSearchData: ThemeData){
        item_search_theme_tv_themeTitle.text = writingSentenceThemeSearchData.theme
    }
}