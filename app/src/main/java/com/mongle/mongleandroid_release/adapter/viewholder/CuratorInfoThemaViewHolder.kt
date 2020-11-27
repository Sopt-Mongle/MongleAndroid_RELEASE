package com.mongle.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.network.data.response.CuratorTheme

class CuratorInfoThemaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_library_thema_cu_info = itemView.findViewById<TextView>(R.id.search_theme_tv_theme)
    val tv_thema_num_library_cu_info = itemView.findViewById<TextView>(R.id.search_theme_tv_bookmark)
    val tv_sentence_count_library_item_cu_info =
        itemView.findViewById<TextView>(R.id.search_theme_tv_sentence_count)

    fun bind(curatorTheme: CuratorTheme) {
        tv_library_thema_cu_info.text = curatorTheme.theme
        tv_thema_num_library_cu_info.text = curatorTheme.saves.toString()
        tv_sentence_count_library_item_cu_info.text = curatorTheme.sentenceNum.toString()
    }
}