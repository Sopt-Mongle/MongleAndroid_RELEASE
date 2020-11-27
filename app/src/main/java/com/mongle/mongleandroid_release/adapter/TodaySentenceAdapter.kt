package com.mongle.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.viewholder.TodaySentenceViewHolder
import com.mongle.mongleandroid_release.network.data.response.TodaySentence

class TodaySentenceAdapter(var datas: List<TodaySentence>, val context: Context) : RecyclerView.Adapter<TodaySentenceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodaySentenceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_today_sentence, parent, false)
        return TodaySentenceViewHolder(view)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: TodaySentenceViewHolder, position: Int) {
        holder.bind(datas[position])

        //view에 onClickListener를 달고, 그 안에서 직접 만든 itemClickListener를 연결시킨다
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
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