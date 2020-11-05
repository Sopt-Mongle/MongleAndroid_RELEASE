package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.WritingSentenceActivity
import com.example.mongleandroid_release.forProgressBar
import com.example.mongleandroid_release.forProgressOn
import com.example.mongleandroid_release.network.data.request.RequestWritingSentenceData


class WritingSentenceStep2Fragment : Fragment() {
    private val args: WritingSentenceStep2FragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.writing_sentence_step2, container, false)

        // forProgressBar
        Handler().forProgressBar(view.findViewById(R.id.writing_sentence_step2_pgb),0)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // booksearch에서 받아온 값 뿌리기
        if (!args.title.isNullOrEmpty()){
            view.findViewById<TextView>(R.id.writing_sentence_step2_tv_title).text = args.title
            view.findViewById<TextView>(R.id.writing_sentence_step2_tv_author).text = args.author
            view.findViewById<TextView>(R.id.writing_sentence_step2_tv_publisher).text = args.publisher
        }



        if(!view.findViewById<TextView>(R.id.writing_sentence_step2_tv_title).text.isNullOrEmpty()){
            // 초록불 켜기
            view.findViewById<TextView>(R.id.writing_sentence_step2_tv_title).
            forProgressOn(view.findViewById(R.id.writing_sentence_step2_pgb_out2),
                view.findViewById(R.id.writing_sentence_step2_pgb_in2))

            // 경고 해제
            view.findViewById<LinearLayout>(R.id.writing_sentence_step2_ll_warning).visibility = View.GONE
            view.findViewById<TextView>(R.id.writing_sentence_step2_tv_title)
                .setBackgroundResource(R.drawable.et_area)
        }



        // 다음 버튼
        view.findViewById<TextView>(R.id.writing_sentence_step2_btn_next).setOnClickListener {
            // 빈칸 경고
            if(view.findViewById<TextView>(R.id.writing_sentence_step2_tv_title).text.isEmpty()) {
                view.findViewById<LinearLayout>(R.id.writing_sentence_step2_ll_warning).visibility = View.VISIBLE

                view.findViewById<TextView>(R.id.writing_sentence_step2_tv_title)
                    .setBackgroundResource(R.drawable.et_area_red)
            }else{ //빈칸 없으면 다음으로
                // (/post/sentence) req data init (5/6):: title, author, publisher
                WritingSentenceActivity.writingSentenceData.title = view.findViewById<TextView>(R.id.writing_sentence_step2_tv_title).text.toString()
                WritingSentenceActivity.writingSentenceData.author = view.findViewById<TextView>(R.id.writing_sentence_step2_tv_author).text.toString()
                WritingSentenceActivity.writingSentenceData.publisher = view.findViewById<TextView>(R.id.writing_sentence_step2_tv_publisher).text.toString()
                //thumbnail은 bookSearch에서
                it.findNavController().navigate(R.id.action_writing_step2_fg_to_writing_step3_fg)
            }

        }

        // 뒤로가기 버튼
        view.findViewById<ImageView>(R.id.writing_sentence_step2_btn_back).setOnClickListener {
            it.findNavController().navigate(R.id.action_writing_step2_fg_to_writing_step1_fg)
        }


        // 책 검색 버튼
        view.findViewById<ImageView>(R.id.writing_sentence_step2_btn_book_search).setOnClickListener{
            it.findNavController().navigate(R.id.action_writing_sentence_step2_fragment_to_writing_sentence_book_search_fragment)

        }

    }

}