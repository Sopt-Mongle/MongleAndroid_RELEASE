package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.network.data.response.BookData
import com.example.mongleandroid_release.R


class WritingSentenceBookSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val item_search_book_tv_htitle = itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_title)
    private val item_search_book_tv_author = itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_author)
    private val item_search_book_tv_publisher = itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_publisher)

    fun bind(bookData: BookData){
        item_search_book_tv_author.text = bookData.authors[0]
        item_search_book_tv_htitle.text = bookData.title
        item_search_book_tv_publisher.text = bookData.publisher


    }


}