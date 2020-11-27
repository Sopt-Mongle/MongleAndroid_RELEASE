package com.mongle.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.viewholder.LibraryThemaClickViewHolder
import com.mongle.mongleandroid_release.network.data.response.LibraryThemeWrite

class LibraryThemaClickAdapter(private val context: Context, var data_the_save : List<LibraryThemeWrite>) :
    RecyclerView.Adapter<LibraryThemaClickViewHolder>() {

    private lateinit var itemClickListener : ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryThemaClickViewHolder {
        val view_click_thema = LayoutInflater.from(context).inflate(R.layout.item_library_thema, parent, false)
        return LibraryThemaClickViewHolder(view_click_thema)
    }

    override fun getItemCount(): Int {
        return data_the_save.size
    }

    override fun onBindViewHolder(holder: LibraryThemaClickViewHolder, position: Int) {
        holder.bind(data_the_save[position])

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