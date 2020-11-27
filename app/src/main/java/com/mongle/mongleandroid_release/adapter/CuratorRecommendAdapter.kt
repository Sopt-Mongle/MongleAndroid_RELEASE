package com.mongle.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.viewholder.CuratorRecommendViewHolder
import com.mongle.mongleandroid_release.network.data.response.RecommendCurator

class CuratorRecommendAdapter(private val context : Context, val datas : List<RecommendCurator>) : RecyclerView.Adapter<CuratorRecommendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuratorRecommendViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main_now_hot_curator, parent, false)
        return CuratorRecommendViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: CuratorRecommendViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    //클릭 인터페이스 정의
    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListener: ItemClickListener

    //클릭리스너 등록 메소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}