package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.CuratorSentence
import com.example.mongleandroid_release.network.data.response.CuratorTheme

class CuratorInfoSentenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_library_sentence_themename_cu_info =
        itemView.findViewById<TextView>(R.id.search_sentence_tv_themename)
    val tv_library_sentence_sentence_cu_info =
        itemView.findViewById<TextView>(R.id.search_sentence_tv_sentence)
    val tv_item_library_sentence_username_cu_info =
        itemView.findViewById<TextView>(R.id.search_sentence_tv_writer)

    fun bind(curatorSentence: CuratorSentence) {
        tv_library_sentence_themename_cu_info.text = curatorSentence.theme
        tv_library_sentence_sentence_cu_info.text = curatorSentence.sentence
        tv_item_library_sentence_username_cu_info.text =
            curatorSentence.writer
    }
}