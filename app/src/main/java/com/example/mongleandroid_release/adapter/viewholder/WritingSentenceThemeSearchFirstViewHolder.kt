package com.example.mongleandroid_release.adapter.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.FirstThemeData

class WritingSentenceThemeSearchFirstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var theme = itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_title)
    var themeIdx = itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_themeIdx)
    var themeImg = itemView.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img)
    var themeChked = itemView.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img_chk)


    fun bind(FirstThemeData : FirstThemeData){
        theme.text = FirstThemeData.theme
        themeIdx.text = FirstThemeData.themeIdx.toString()

        Glide.with(itemView).load(FirstThemeData.themeImg).into(themeImg)
//        if(FirstThemeData.themeChked){
        themeChked.visibility = if (FirstThemeData.themeChked) View.VISIBLE  else View.GONE
        Log.d("SSS", "업데이트 됨! ${theme.text} ${themeChked.visibility}}")
//        }


    }


}