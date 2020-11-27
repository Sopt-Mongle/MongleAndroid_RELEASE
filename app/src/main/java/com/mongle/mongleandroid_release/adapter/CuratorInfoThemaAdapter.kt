package com.mongle.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.viewholder.CuratorInfoThemaViewHolder
import com.mongle.mongleandroid_release.network.data.response.CuratorTheme

class CuratorInfoThemaAdapter(private val context: Context, var data_the_cu_info : List<CuratorTheme>) :
    RecyclerView.Adapter<CuratorInfoThemaViewHolder>() {

    private lateinit var itemClickListener : ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuratorInfoThemaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_theme, parent, false)
        return CuratorInfoThemaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data_the_cu_info.size
    }

    override fun onBindViewHolder(holder: CuratorInfoThemaViewHolder, position: Int) {
        holder.bind(data_the_cu_info[position])


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


    //클릭 리스너 등록 메소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}