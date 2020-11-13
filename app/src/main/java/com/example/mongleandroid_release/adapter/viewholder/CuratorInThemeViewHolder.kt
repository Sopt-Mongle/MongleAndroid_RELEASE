package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.CuratorList

class CuratorInThemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val img = itemView.findViewById<ImageView>(R.id.search_curator_img_profile)
    val name = itemView.findViewById<TextView>(R.id.search_curator_tv_name)
    val subscribe = itemView.findViewById<TextView>(R.id.search_curator_tv_subcount)
    val keyword = itemView.findViewById<TextView>(R.id.search_curator_tv_keyword)
    val alreadySubscribed = itemView.findViewById<CheckBox>(R.id.search_curator_btn_subscribe)

    fun bind(curatorList : CuratorList) {
        if(curatorList.img == null) {
            Glide.with(itemView).load(R.drawable.detailview_img_profile).into(img)
        } else {
            Glide.with(itemView).load(curatorList.img).into(img)
        }
        name.text = curatorList.name
        subscribe.text = curatorList.subscribe.toString()
        keyword.text = curatorList.keyword
        alreadySubscribed.setOnClickListener {
            if(alreadySubscribed.isChecked) {
                alreadySubscribed.text = "구독중"
            } else alreadySubscribed.text = "구독"
        }
    }
}