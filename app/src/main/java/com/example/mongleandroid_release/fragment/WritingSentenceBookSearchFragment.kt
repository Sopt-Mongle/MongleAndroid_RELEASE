package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mongleandroid_release.R



class WritingSentenceBookSearchFragment : Fragment() {
    var title :String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.writing_sentence_book_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 검색 창
        view.findViewById<EditText>(R.id.writing_sentence_book_search_et_search).addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                title = view.findViewById<EditText>(R.id.writing_sentence_book_search_et_search).text.toString()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //실시간 글자 수 세기
                view.findViewById<TextView>(R.id.writing_sentence_book_search_tv_cnt).text =
                    view.findViewById<EditText>(R.id.writing_sentence_book_search_et_search).text.toString().length.toString()

            }
        })

        // 나가기 버튼
        view.findViewById<ImageView>(R.id.writing_sentence_book_search_btn_out).setOnClickListener{
            it.findNavController().navigate(R.id.action_writing_sentence_book_search_fragment_to_writing_sentence_step2_fragment)
            // 책 제목 넘겨 줌
        }

        // 검색 버튼
        view.findViewById<ImageView>(R.id.writing_sentence_book_search_btn_search).setOnClickListener {

        }


    }


}