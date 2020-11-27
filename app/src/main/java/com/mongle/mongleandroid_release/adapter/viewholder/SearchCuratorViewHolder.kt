package com.mongle.mongleandroid_release.adapter.viewholder

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.MainActivity
import com.mongle.mongleandroid_release.network.data.response.SearchCurator

class SearchCuratorViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

    val img = itemView.findViewById<ImageView>(R.id.search_curator_img_profile)
    val name = itemView.findViewById<TextView>(R.id.search_curator_tv_name)
    val subscribe = itemView.findViewById<TextView>(R.id.search_curator_tv_subcount)
    val keyword = itemView.findViewById<TextView>(R.id.search_curator_tv_keyword)
    val alreadySubscribed = itemView.findViewById<CheckBox>(R.id.search_curator_btn_subscribe)

    fun bind(searchCurator: SearchCurator) {
        if(searchCurator.img == null) {
            Glide.with(itemView).load(R.drawable.detailview_img_profile).into(img)
        } else {
            Glide.with(itemView).load(searchCurator.img).into(img)
        }
        subscribe.text = searchCurator.subscribe.toString()
        keyword.text = searchCurator.keyword
        alreadySubscribed.setOnClickListener {
            if(alreadySubscribed.isChecked) {
                alreadySubscribed.text = "구독중"
            } else alreadySubscribed.text = "구독"
        }

        val inputString = MainActivity.search_result

        val sb = StringBuilder(inputString)
        val startWord = sb[0]
        var startIndex = searchCurator.name.indexOf(startWord)
        val spannable = SpannableStringBuilder(searchCurator.name)

        var index = 0
        while (index < searchCurator.name.length && startIndex != -1) {
            startIndex = searchCurator.name.indexOf(inputString, index)
            if (startIndex == -1) break else {
                spannable.setSpan(
                    ForegroundColorSpan(Color.rgb(115, 192, 136)),
                    startIndex,
                    startIndex + inputString.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                name.text = spannable
            }
            index = startIndex + 1
        }

        name.text = spannable
    }
}