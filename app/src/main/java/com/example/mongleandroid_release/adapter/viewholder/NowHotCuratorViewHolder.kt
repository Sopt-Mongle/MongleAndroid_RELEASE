package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.ResponseMainNowHotData

class NowHotCuratorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val img = itemView.findViewById<ImageView>(R.id.img_now_hot_curator)
    val name = itemView.findViewById<TextView>(R.id.tv_curator_name)
    val keyword = itemView.findViewById<TextView>(R.id.tv_curator_keyword)

    fun bind(mainCurator: ResponseMainNowHotData) {
        Glide.with(itemView).load(img).into(img)
        name.text = mainCurator.toString()
        keyword.text = mainCurator.toString()

    }
}