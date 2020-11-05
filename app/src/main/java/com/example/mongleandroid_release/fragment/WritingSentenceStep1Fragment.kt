package com.example.mongleandroid_release.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.WritingSentenceActivity
import com.example.mongleandroid_release.forProgressOn
import com.example.mongleandroid_release.network.data.request.RequestWritingSentenceData
import kotlinx.android.synthetic.main.activity_join_step2.*

class WritingSentenceStep1Fragment : Fragment() {

    private val act by lazy { context as WritingSentenceActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.writing_sentence_step1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // forProgressOn
        view.findViewById<EditText>(R.id.writing_sentence_step1_et_sentence).addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                // 경고 해제
                view.findViewById<LinearLayout>(R.id.writing_sentence_step1_ll_warning).visibility = View.GONE
                view.findViewById<EditText>(R.id.writing_sentence_step1_et_sentence)
                    .setBackgroundResource(R.drawable.et_area)

                // 초록 불 켜지기
                view.findViewById<EditText>(R.id.writing_sentence_step1_et_sentence).
                forProgressOn(view.findViewById(R.id.writing_sentence_step1_pgb_out),
                    view.findViewById(R.id.writing_sentence_step1_pgb_in))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // user reaction
                //실시간 글자 수 세기
                view.findViewById<TextView>(R.id.writing_sentence_step1_tv_cnt).text =
                    view.findViewById<EditText>(R.id.writing_sentence_step1_et_sentence).text.toString().length.toString()
            }

        })

        // 다음 버튼
        view.findViewById<TextView>(R.id.writing_sentence_step1_btn_next).setOnClickListener {
            // 빈칸 경고
            if(view.findViewById<EditText>(R.id.writing_sentence_step1_et_sentence).text.isEmpty()) {
                view.findViewById<LinearLayout>(R.id.writing_sentence_step1_ll_warning).visibility = View.VISIBLE

                view.findViewById<EditText>(R.id.writing_sentence_step1_et_sentence)
                    .setBackgroundResource(R.drawable.et_area_red)
            }else{ //빈칸 없으면 다음으로
                // (/post/sentence) req data init (1/6) :: sentence
                WritingSentenceActivity.writingSentenceData.sentence = view.findViewById<EditText>(R.id.writing_sentence_step1_et_sentence).text.toString()
                it.findNavController().navigate(R.id.action_writing_step1_fg_to_writing_step2_fg)
            }

        }
        // 나가기 버튼
        view.findViewById<ImageView>(R.id.writing_sentence_step1_btn_out).setOnClickListener {
            act.finish()
        }

    }
}