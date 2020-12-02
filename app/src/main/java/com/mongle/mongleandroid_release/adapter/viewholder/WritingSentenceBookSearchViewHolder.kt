package com.mongle.mongleandroid_release.adapter.viewholder

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mongle.mongleandroid_release.network.data.response.BookData
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.MainActivity


class WritingSentenceBookSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val item_search_book_tv_htitle = itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_title)
    private val item_search_book_tv_author = itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_author)
    private val item_search_book_tv_publisher = itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_publisher)
    private val item_writing_sentence_book_result_tv_thumbnail = itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_thumbnail)


    fun bind(bookData: BookData){
        Glide.with(itemView).load(bookData.thumbnail).into(itemView.findViewById<ImageView>(R.id.item_writing_sentence_book_result_img))
        if(bookData.authors.size == 0) {
            item_search_book_tv_author.text = " "
        } else {
            item_search_book_tv_author.text = bookData.authors[0]
        }
        item_search_book_tv_publisher.text = bookData.publisher
        item_writing_sentence_book_result_tv_thumbnail.text = bookData.thumbnail

        val inputString = MainActivity.book_result

        val sb = StringBuilder(inputString)
        val startWord = sb[0]
        var startIndex = bookData.title.indexOf(startWord)
        val spannable = SpannableStringBuilder(bookData.title)

        var index = 0
        while(index < bookData.title.length && startIndex != -1) {
            startIndex = bookData.title.indexOf(inputString, index)
            if(startIndex == -1) break else {
                spannable.setSpan(
                    ForegroundColorSpan(Color.rgb(115, 192, 136)),
                    startIndex,
                    startIndex + inputString.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                item_search_book_tv_htitle.text = spannable
            }
            index = startIndex + 1
        }
        item_search_book_tv_htitle.text = spannable

    }


}