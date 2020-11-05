package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.network.data.response.BookData
import com.example.mongleandroid_release.R


class WritingSentenceBookSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(bookData: BookData){
        itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_author).text = bookData.authors[0]
        itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_title).text = bookData.title
        itemView.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_publisher).text = bookData.publisher
        Glide.with(itemView).load(bookData.thumbnail).into(itemView.findViewById<ImageView>(R.id.item_writing_sentence_book_result_img))
    }


}