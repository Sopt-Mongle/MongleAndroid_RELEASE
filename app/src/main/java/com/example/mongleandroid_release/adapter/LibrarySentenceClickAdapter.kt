package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.LibrarySentenceClickViewHolder
import com.example.mongleandroid_release.network.data.response.LibrarySentenceWrite

class LibrarySentenceClickAdapter(private val context: Context, var data_sen_click: List<LibrarySentenceWrite>) :
    RecyclerView.Adapter<LibrarySentenceClickViewHolder>() {

    private lateinit var itemClickListener : ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibrarySentenceClickViewHolder {
        val view_click_sen =
            LayoutInflater.from(context).inflate(R.layout.item_library_sentence_click, parent, false)
        return LibrarySentenceClickViewHolder(
            view_click_sen
        )
    }

    override fun getItemCount(): Int {
        return data_sen_click.size
    }


    override fun onBindViewHolder(holder: LibrarySentenceClickViewHolder, position: Int) {
        holder.bind(data_sen_click[position])

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