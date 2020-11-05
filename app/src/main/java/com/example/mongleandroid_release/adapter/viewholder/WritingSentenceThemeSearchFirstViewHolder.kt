package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.FirstThemeData

class WritingSentenceThemeSearchFirstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(FirstThemeData : FirstThemeData){
        itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_title).text = FirstThemeData.theme
        itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_themeIdx).text = FirstThemeData.themeIdx.toString()
        Glide.with(itemView).load(FirstThemeData.themeImg).into(itemView.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img))

    }
}