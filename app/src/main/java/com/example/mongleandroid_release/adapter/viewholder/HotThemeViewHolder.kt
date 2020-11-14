package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.MainThemes

class HotThemeViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){
    val theme = itemView.findViewById<TextView>(R.id.tv_main_hot_theme)
    val sentenceNum = itemView.findViewById<TextView>(R.id.tv_main_hot_theme_count)
    val themeImg = itemView.findViewById<ImageView>(R.id.img_theme_background)
    val saves = itemView.findViewById<TextView>(R.id.themeSaves)

    fun bind(mainThemes: MainThemes) {
        theme.text = mainThemes.theme // 테마 제목
        sentenceNum.text = mainThemes.sentenceNum.toString() // 문장 수
        Glide.with(itemView).load(mainThemes.themeImg).into(themeImg) // 테마 배경이미지
        saves.text = mainThemes.saves.toString() // 테마 북마크 수
    }
}