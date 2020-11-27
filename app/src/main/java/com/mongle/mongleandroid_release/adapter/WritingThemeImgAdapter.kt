package com.mongle.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.viewholder.WritingThemeImgViewHolder
import com.mongle.mongleandroid_release.network.data.response.ThemeImg

class WritingThemeImgAdapter(private val context: Context) : RecyclerView.Adapter<WritingThemeImgViewHolder>() {
    var datas = mutableListOf<ThemeImg>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WritingThemeImgViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_writing_theme_img,parent,false )
        return WritingThemeImgViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: WritingThemeImgViewHolder, position: Int) {
        holder.bind(datas[position])
        holder.setIsRecyclable(false)


        //view에 onClickListener를 달고, 그 안에서 직접 만든 itemClickListener를 연결시킨다
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position,datas[position], datas)
            holder.bind(datas[position])
        }
    }

    //클릭 인터페이스 정의
    interface ItemClickListener{
        fun onClick(view: View, position: Int, data: ThemeImg, datas: MutableList<ThemeImg>)
    }

    //클릭리스너 선언
    private lateinit var itemClickListener: ItemClickListener

    //클릭리스너 등록 메소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}