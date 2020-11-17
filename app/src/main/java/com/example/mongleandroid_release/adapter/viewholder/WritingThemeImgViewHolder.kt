package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.ResponseWritingThemeImgData
import com.example.mongleandroid_release.network.data.response.ThemeImg

class WritingThemeImgViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val item_writing_theme_img = itemView.findViewById<ImageView>(R.id.item_writing_theme_img)
    val item_writing_theme_tv_imgIdx = itemView.findViewById<TextView>(R.id.item_writing_theme_tv_imgIdx)
    var imgChked = itemView.findViewById<ImageView>(R.id.item_writing_theme_img_chk)

    fun bind(themeImg: ThemeImg){
        Glide.with(itemView).load(themeImg.img).into(item_writing_theme_img)
        item_writing_theme_tv_imgIdx.text = themeImg.themeImgIdx.toString()
        imgChked.visibility = if (themeImg.imgChked) View.VISIBLE  else View.GONE

    }
}