package com.mongle.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.network.data.response.CuratorKeyword

class CuratorKeywordViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.search_curator_img_profile)
    val name = itemView.findViewById<TextView>(R.id.search_curator_tv_name)
    val keyword = itemView.findViewById<TextView>(R.id.search_curator_tv_keyword)
    val subscribe = itemView.findViewById<TextView>(R.id.search_curator_tv_subcount)
    val alreadySubscribed = itemView.findViewById<CheckBox>(R.id.search_curator_btn_subscribe)

    fun bind(curatorKeyword : CuratorKeyword) {
        if(curatorKeyword.img == null) {
            Glide.with(itemView).load(R.drawable.detailview_img_profile).into(img)
        } else {
            Glide.with(itemView).load(curatorKeyword.img).into(img)
        }
        name.text = curatorKeyword.name
        keyword.text = curatorKeyword.keyword
        subscribe.text = curatorKeyword.subscribe.toString()
        alreadySubscribed.setOnClickListener {
            if(alreadySubscribed.isChecked) {
                alreadySubscribed.text = "구독중"
            } else alreadySubscribed.text = "구독"
        }
    }
}
