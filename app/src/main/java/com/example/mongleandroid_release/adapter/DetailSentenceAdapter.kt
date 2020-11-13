package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.DetailSentenceViewHolder
import com.example.mongleandroid_release.network.data.response.SentenceDatailData

//class DetailSentenceAdapter(var datas: SentenceDatailData?, val context: Context) :
//    RecyclerView.Adapter<DetailSentenceViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailSentenceViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.item_detail_theme, parent,false)
//        return DetailSentenceViewHolder(view)
//    }
//
//    //override fun getItemCount() = datas.size
//
////    override fun onBindViewHolder(holder: DetailSentenceViewHolder, position: Int) {
////        holder.bind(datas[position])
//
////        holder.itemView.setOnClickListener {
////            itemClickListener.onClick(it,position)
////        }
////    }
//
//    //클릭 인터페이스 정의
//    interface ItemClickListener {
//        fun onClick(view: View, position: Int)
//    }
//
//    //클릭리스너 선언
//    private lateinit var itemClickListener: ItemClickListener
//
//    //클릭리스너 등록 메소드
//    fun setItemClickListener(itemClickListener: ItemClickListener) {
//        this.itemClickListener = itemClickListener
//    }
//}