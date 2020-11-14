package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.OtherSentence

class DetailSentenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val tv_theme_sentence = itemView.findViewById<TextView>(R.id.tv_theme_sentence)
    val tv_theme_curator = itemView.findViewById<TextView>(R.id.tv_theme_curator)
    val tv_likes_num = itemView.findViewById<TextView>(R.id.tv_likes_num)
    val tv_saves_num = itemView.findViewById<TextView>(R.id.tv_saves_num)

    fun bind(otherSentence: OtherSentence) {
        tv_theme_sentence.text = otherSentence.sentence
        tv_theme_curator.text = otherSentence.writer
        tv_likes_num.text = otherSentence.likes.toString()
        tv_saves_num.text = otherSentence.saves.toString()
    }

}