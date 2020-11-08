package com.example.mongleandroid_release.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.viewholder.CuratorInfoSentenceViewHolder
import com.example.mongleandroid_release.network.data.CuratorInfoSentenceData

class CuratorInfoSentenceAdapter(private val context: Context) :
    RecyclerView.Adapter<CuratorInfoSentenceViewHolder>() {
    var data_sen_cu_info = mutableListOf<CuratorInfoSentenceData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuratorInfoSentenceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cu_info_sentence, parent, false)
        return CuratorInfoSentenceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data_sen_cu_info.size
    }

    override fun onBindViewHolder(holder: CuratorInfoSentenceViewHolder, position: Int) {
        holder.bind(data_sen_cu_info[position])
    }

}
