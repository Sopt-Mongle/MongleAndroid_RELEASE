package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.OpenSourceData
import com.example.mongleandroid_release.network.data.response.RecommendCurator

class OpenSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.tv_open_source)

    fun bind(openSourceData: OpenSourceData) {
        title.text = openSourceData.title
    }
}