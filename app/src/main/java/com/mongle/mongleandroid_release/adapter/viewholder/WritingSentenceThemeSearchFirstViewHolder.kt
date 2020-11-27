package com.mongle.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.network.data.response.FirstThemeData

class WritingSentenceThemeSearchFirstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var theme = itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_title)
    var themeIdx = itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_themeIdx)
    var themeImg = itemView.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img)
    var themeChked = itemView.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img_chk)


    fun bind(firstThemeData : FirstThemeData){
        theme.text = firstThemeData.theme
        themeIdx.text = firstThemeData.themeIdx.toString()
        Glide.with(itemView).load(firstThemeData.themeImg).into(themeImg)
        themeChked.visibility = if (firstThemeData.themeChked) View.VISIBLE  else View.GONE
    }


}