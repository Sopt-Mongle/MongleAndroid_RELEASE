package com.example.mongleandroid_release.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.WritingSentenceActivity
import com.example.mongleandroid_release.adapter.viewholder.WritingSentenceThemeSearchFirstViewHolder
import com.example.mongleandroid_release.network.data.response.FirstThemeData

class WritingSentenceThemeSearchFirstAdapter(private val context : Context) : RecyclerView.Adapter<WritingSentenceThemeSearchFirstViewHolder>() {
    var datas = mutableListOf<FirstThemeData>()
    private val act by lazy { context as WritingSentenceActivity }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WritingSentenceThemeSearchFirstViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_writing_sentence_theme_result,parent,false )
        return WritingSentenceThemeSearchFirstViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holderFirst: WritingSentenceThemeSearchFirstViewHolder, position: Int) {
        holderFirst.bind(datas[position])


        val displayMetrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(displayMetrics)

        //recycler view item의 크기를 동적으로
        val deviceWidth = (displayMetrics.widthPixels / 2.3).toInt()
//        val deviceHeight = deviceWidth

        holderFirst.itemView.layoutParams.width = deviceWidth
        holderFirst.itemView.layoutParams.height = holderFirst.itemView.layoutParams.width
        holderFirst.itemView.requestLayout() // 변경 사항 적용

        //view에 onClickListener를 달고, 그 안에서 직접 만든 itemClickListener를 연결시킨다
        holderFirst.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }



    //클릭 인터페이스 정의
    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListener: ItemClickListener

    //클릭리스너 등록 메소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}