package com.mongle.mongleandroid_release.adapter.viewholder

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.MainActivity
import com.mongle.mongleandroid_release.network.data.response.SearchTheme

class WritingSentenceThemeSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val theme = itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_title)
    val themeIdx =
        itemView.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_themeIdx)
    val themeImg = itemView.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img)
    var themeChked = itemView.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img_chk)

    fun bind(searchTheme: SearchTheme) {
        themeIdx.text = searchTheme.themeIdx.toString()
        Glide.with(itemView).load(searchTheme.themeImg).into(themeImg)
        themeChked.visibility = if (searchTheme.themeChked) View.VISIBLE  else View.GONE

        val inputString = MainActivity.search_result

        val sb = StringBuilder(inputString)
        val startWord = sb[0]
        var startIndex = searchTheme.theme.indexOf(startWord)
        val spannable = SpannableStringBuilder(searchTheme.theme)

        var index = 0
        while (index < searchTheme.theme.length && startIndex != -1) {
            startIndex = searchTheme.theme.indexOf(inputString, index)
            if (startIndex == -1) break else {
                spannable.setSpan(
                    ForegroundColorSpan(Color.rgb(115, 192, 136)),
                    startIndex,
                    startIndex + inputString.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                theme.text = spannable
            }
            index = startIndex + 1
        }

        theme.text = spannable


    }
}