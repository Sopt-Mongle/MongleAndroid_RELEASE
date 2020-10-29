package com.example.mongleandroid_release.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.WritingSentenceActivity

class WritingSentenceStep1Fragment : Fragment() {

    private val act: WritingSentenceActivity = context as WritingSentenceActivity

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

        // 다음 버튼
        view.findViewById<Button>(R.id.writing_sentence_step1_btn_next).setOnClickListener {
            it.findNavController().navigate(R.id.action_writing_step1_fg_to_writing_step2_fg)
        }
        view.findViewById<Button>(R.id.writing_sentence_step1_btn_out).setOnClickListener {
            act.finish()
        }


    }
}