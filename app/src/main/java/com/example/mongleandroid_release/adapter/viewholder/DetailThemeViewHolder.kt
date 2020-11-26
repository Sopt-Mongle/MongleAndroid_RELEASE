package com.example.mongleandroid_release.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.response.DataSentence

class DetailThemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val tv_theme_sentence = itemView.findViewById<TextView>(R.id.tv_theme_sentence)
    val tv_theme_curator = itemView.findViewById<TextView>(R.id.tv_theme_curator)
    val tv_likes_num = itemView.findViewById<TextView>(R.id.tv_likes_num)
    val tv_saves_num = itemView.findViewById<TextView>(R.id.tv_saves_num)

   fun bind(dataSentence: DataSentence) {
       tv_theme_sentence.text = dataSentence.sentence
       tv_theme_curator.text = dataSentence.title // 여기 ㅎㅎㅎ 책 제목인듯,,
       tv_likes_num.text = dataSentence.likes.toString()
       tv_saves_num.text = dataSentence.saves.toString()
   }

}