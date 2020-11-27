package com.mongle.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.network.data.OpenSourceData

class OpenSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.tv_open_source)

    fun bind(openSourceData: OpenSourceData) {
        title.text = openSourceData.title
    }
}