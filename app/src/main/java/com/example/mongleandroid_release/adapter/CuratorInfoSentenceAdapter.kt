package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.CuratorInfoSentenceViewHolder
import com.example.mongleandroid_release.network.data.response.CuratorSentence

class CuratorInfoSentenceAdapter(private val context: Context, var data_sen_cu_info : List<CuratorSentence>) :
    RecyclerView.Adapter<CuratorInfoSentenceViewHolder>() {

    private lateinit var itemClickListener : ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuratorInfoSentenceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_sentence, parent, false)
        return CuratorInfoSentenceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data_sen_cu_info.size
    }

    override fun onBindViewHolder(holder: CuratorInfoSentenceViewHolder, position: Int) {
        holder.bind(data_sen_cu_info[position])

        //view에 onClickListener를 달고, 그 안에서 직접 만든 itemClickListener를 연결시킨다
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
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
