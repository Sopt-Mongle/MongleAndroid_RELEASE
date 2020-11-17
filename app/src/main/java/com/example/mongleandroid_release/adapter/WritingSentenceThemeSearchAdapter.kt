package com.example.mongleandroid_release.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.WritingSentenceActivity
import com.example.mongleandroid_release.adapter.viewholder.WritingSentenceThemeSearchViewHolder
import com.example.mongleandroid_release.network.data.response.FirstThemeData
import com.example.mongleandroid_release.network.data.response.SearchTheme

class WritingSentenceThemeSearchAdapter(private val context : Context) : RecyclerView.Adapter<WritingSentenceThemeSearchViewHolder>() {
    var datas = mutableListOf<SearchTheme>()
    private val act by lazy { context as WritingSentenceActivity }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WritingSentenceThemeSearchViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_writing_sentence_theme_result,parent,false )
        return WritingSentenceThemeSearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: WritingSentenceThemeSearchViewHolder, position: Int) {
        holder.bind(datas[position])
        holder.setIsRecyclable(false)


        val displayMetrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(displayMetrics)
        //recycler view item의 크기를 동적으로
        val deviceWidth = (displayMetrics.widthPixels / 2.3).toInt()
//        val deviceHeight = deviceWidth

        holder.itemView.layoutParams.width = deviceWidth
        holder.itemView.layoutParams.height = holder.itemView.layoutParams.width
        holder.itemView.requestLayout() // 변경 사항 적용


        //view에 onClickListener를 달고, 그 안에서 직접 만든 itemClickListener를 연결시킨다
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position,datas[position], datas)
        }
    }


    //클릭 인터페이스 정의
    interface ItemClickListener{
        fun onClick(view: View, position: Int, data: SearchTheme, datas: MutableList<SearchTheme>)
    }

    //클릭리스너 선언
    private lateinit var itemClickListener: ItemClickListener

    //클릭리스너 등록 메소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}