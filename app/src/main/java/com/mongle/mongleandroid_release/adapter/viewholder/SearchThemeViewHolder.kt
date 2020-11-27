package com.mongle.mongleandroid_release.adapter.viewholder

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.MainActivity
import com.mongle.mongleandroid_release.network.data.response.SearchTheme

class SearchThemeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val theme = itemView.findViewById<TextView>(R.id.search_theme_tv_theme)
    val saves = itemView.findViewById<TextView>(R.id.search_theme_tv_bookmark)
    val sentenceNum = itemView.findViewById<TextView>(R.id.search_theme_tv_sentence_count)

    fun bind(searchTheme : SearchTheme) {
        saves.text = searchTheme.saves.toString()
        sentenceNum.text = searchTheme.sentenceNum.toString()

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