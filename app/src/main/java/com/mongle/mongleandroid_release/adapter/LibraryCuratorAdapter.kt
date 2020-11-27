package com.mongle.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.viewholder.LibraryCuratorViewHolder
import com.mongle.mongleandroid_release.network.data.response.LibraryCurator
import kotlinx.android.synthetic.main.item_library_curator.view.*

class LibraryCuratorAdapter(private val context: Context, var data_cu : List<LibraryCurator>) :
//class LibraryCuratorAdapter(private val context: Context) :
    RecyclerView.Adapter<LibraryCuratorViewHolder>() {
    //    var data_cu = mutableListOf<LibraryCuratorData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryCuratorViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_library_curator, parent, false)
        return LibraryCuratorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data_cu.size
    }

    override fun onBindViewHolder(holder: LibraryCuratorViewHolder, position: Int) {
        holder.bind(data_cu[position])

        holder.itemView.library_curator_btn_subscribe_item.isChecked = data_cu[position].alreadySubscribed
        if(data_cu[position].alreadySubscribed) holder.itemView.library_curator_btn_subscribe_item.text = "구독중"


        holder.itemView.library_curator_btn_subscribe_item.setOnCheckedChangeListener { view, isChecked ->
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