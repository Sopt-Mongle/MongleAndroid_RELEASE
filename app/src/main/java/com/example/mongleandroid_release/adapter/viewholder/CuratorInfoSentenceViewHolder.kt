package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.CuratorInfoSentenceData

class CuratorInfoSentenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_library_sentence_themename_cu_info =
        itemView.findViewById<TextView>(R.id.tv_library_sentence_themename_cu_info)
    val tv_library_sentence_sentence_cu_info =
        itemView.findViewById<TextView>(R.id.tv_library_sentence_sentence_cu_info)
    val tv_item_library_sentence_username_cu_info =
        itemView.findViewById<TextView>(R.id.tv_item_library_sentence_username_cu_info)

    fun bind(curatorInfoSentenceData: CuratorInfoSentenceData) {
        tv_library_sentence_themename_cu_info.text = curatorInfoSentenceData.tv_library_sentence_themename_cu_info
        tv_library_sentence_sentence_cu_info.text = curatorInfoSentenceData.tv_library_sentence_sentence_cu_info
        tv_item_library_sentence_username_cu_info.text =
            curatorInfoSentenceData.tv_item_library_sentence_username_cu_info
    }
}