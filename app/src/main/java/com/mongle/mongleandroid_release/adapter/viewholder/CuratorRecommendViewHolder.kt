package com.mongle.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.network.data.response.RecommendCurator

class CuratorRecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.img_now_hot_curator)
    val name = itemView.findViewById<TextView>(R.id.tv_curator_name)
    val keyword = itemView.findViewById<TextView>(R.id.tv_curator_keyword)

    fun bind(recommendCurator: RecommendCurator) {
        if(recommendCurator.img == null) {
            Glide.with(itemView).load(R.drawable.detailview_img_profile).into(img)
        } else {
            Glide.with(itemView).load(recommendCurator.img).into(img)
        }
        name.text = recommendCurator.name
        keyword.text = recommendCurator.keyword
    }
}