package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.ThemeData

class WritingSentenceThemeSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(writingSentenceThemeSearchData: ThemeData){
        itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_title).text = writingSentenceThemeSearchData.theme
        itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_themeIdx).text = writingSentenceThemeSearchData.themeIdx.toString()

    }
}