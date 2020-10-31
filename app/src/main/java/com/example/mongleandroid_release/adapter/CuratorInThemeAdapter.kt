package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.CuratorInThemeViewHolder
import com.example.mongleandroid_release.network.data.response.CuratorList

class CuratorInThemeAdapter(private val context : Context, var datas:List<CuratorList>) : RecyclerView.Adapter<CuratorInThemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuratorInThemeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_curator, parent, false)
        return CuratorInThemeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: CuratorInThemeViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}