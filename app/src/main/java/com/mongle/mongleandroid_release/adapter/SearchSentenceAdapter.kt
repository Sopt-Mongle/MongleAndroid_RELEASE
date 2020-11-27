package com.mongle.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.viewholder.SearchSentenceViewHolder
import com.mongle.mongleandroid_release.network.data.response.SearchSentence

class SearchSentenceAdapter(private val context : Context, var datas : List<SearchSentence>) : RecyclerView.Adapter<SearchSentenceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSentenceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_sentence, parent, false)
        return SearchSentenceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchSentenceViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    //클릭 인터페이스 정의
    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListener: ItemClickListener

    //클릭리스너 등록 메소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}