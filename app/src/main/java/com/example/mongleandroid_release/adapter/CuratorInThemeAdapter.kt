package com.example.mongleandroid_release.adapter

import android.content.Context
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.CuratorInThemeViewHolder
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.CuratorList
import kotlinx.android.synthetic.main.item_search_curator.view.*


class CuratorInThemeAdapter(private val context: Context, var datas: List<CuratorList>) : RecyclerView.Adapter<CuratorInThemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuratorInThemeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_curator, parent, false)
        return CuratorInThemeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: CuratorInThemeViewHolder, position: Int) {
        holder.bind(datas[position])

        Log.d("datacheck", "$position ${datas[position].alreadySubscribed}")

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