package com.mongle.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.viewholder.SearchCuratorViewHolder
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.data.response.SearchCurator
import kotlinx.android.synthetic.main.item_search_curator.view.*

class SearchCuratorAdapter(private val context : Context, var datas : List<SearchCurator>) : RecyclerView.Adapter<SearchCuratorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCuratorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_curator, parent, false)
        return SearchCuratorViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchCuratorViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.itemView.search_curator_btn_subscribe.isChecked = datas[position].alreadySubscribed
        if(datas[position].alreadySubscribed) holder.itemView.search_curator_btn_subscribe.text = "구독중"

        holder.itemView.search_curator_btn_subscribe.setOnCheckedChangeListener { view, isChecked ->
            if (view.context.let { SharedPreferenceController.getAccessToken(it) } == "guest") {
                view.isChecked = false
                view.text = "구독"
            }
            itemClickListener.onClickSubscribe(view, position)
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onClickItem(it, position)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    //클릭 인터페이스 정의
    interface ItemClickListener {
        fun onClickItem(view: View, position: Int)
        fun onClickSubscribe(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListener: ItemClickListener

    //클릭리스너 등록 메소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}