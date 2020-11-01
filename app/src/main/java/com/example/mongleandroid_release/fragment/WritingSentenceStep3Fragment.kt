package com.example.mongleandroid_release.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.forProgressBar
import com.example.mongleandroid_release.forProgressOn

class WritingSentenceStep3Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.writing_sentence_step3, container, false)

        // forProgressBar
        Handler().forProgressBar(view.findViewById(R.id.writing_sentence_step3_pgb),50)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // forProgressOn
        view.findViewById<TextView>(R.id.writing_sentence_step3_tv_theme).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                view.findViewById<TextView>(R.id.writing_sentence_step3_tv_theme).
                forProgressOn(view.findViewById(R.id.writing_sentence_step3_pgb_out3),
                    view.findViewById(R.id.writing_sentence_step3_pgb_in3))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // user reaction
            }

        })


        // 다음 버튼
        view.findViewById<TextView>(R.id.writing_sentence_step3_btn_next).setOnClickListener {
            it.findNavController().navigate(R.id.action_writing_sentence_step3_fragment_to_writing_sentence_finish_fragment)
        }
        view.findViewById<ImageView>(R.id.writing_sentence_step3_btn_back).setOnClickListener {
            it.findNavController().navigate(R.id.action_writing_step3_fg_to_writing_step2_fg)
        }
    }


}