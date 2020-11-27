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
import com.mongle.mongleandroid_release.network.data.response.SearchSentence


class SearchSentenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val theme = itemView.findViewById<TextView>(R.id.search_sentence_tv_themename)
    val sentence = itemView.findViewById<TextView>(R.id.search_sentence_tv_sentence)
    val writer = itemView.findViewById<TextView>(R.id.search_sentence_tv_writer)

    fun bind(searchSentence: SearchSentence) {
        theme.text = searchSentence.theme
        writer.text = searchSentence.writer

        val inputString = MainActivity.search_result

        val sb = StringBuilder(inputString)
        val startWord = sb[0]
        var startIndex = searchSentence.sentence.indexOf(startWord)
        val spannable = SpannableStringBuilder(searchSentence.sentence)

        var index = 0
        while (index < searchSentence.sentence.length && startIndex != -1) {
            startIndex = searchSentence.sentence.indexOf(inputString, index)
            if (startIndex == -1) break else {
                spannable.setSpan(
                    ForegroundColorSpan(Color.rgb(115, 192, 136)),
                    startIndex,
                    startIndex + inputString.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                sentence.text = spannable
            }
            index = startIndex + 1
        }

        sentence.text = spannable

    }
}