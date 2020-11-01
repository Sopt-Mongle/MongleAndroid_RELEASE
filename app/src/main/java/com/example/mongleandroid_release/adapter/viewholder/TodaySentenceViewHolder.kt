package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.ResponseTodaySentenceData

class TodaySentenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_today_sentence = itemView.findViewById<TextView>(R.id.tv_today_sentence)
    val tv_today_sentence_title = itemView.findViewById<TextView>(R.id.tv_today_sentence_title)


    fun bind(todaySentence: ResponseTodaySentenceData){
        tv_today_sentence.text = todaySentence.toString()
        tv_today_sentence_title.text = todaySentence.toString()
    }
}