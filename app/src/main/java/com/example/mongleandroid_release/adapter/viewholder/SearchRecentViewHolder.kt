package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R

class SearchRecentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val datas = itemView.findViewById<TextView>(R.id.tv_recent_keyword)

    fun bind(data: String){
        datas.text = data
    }
}