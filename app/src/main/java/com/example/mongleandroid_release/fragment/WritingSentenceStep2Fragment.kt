package com.example.mongleandroid_release.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.mongleandroid_release.R

class WritingSentenceStep2Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.writing_sentence_step2, container, false)

        // 다음 버튼
        view.findViewById<TextView>(R.id.writing_sentence_step2_btn_next).setOnClickListener {
            it.findNavController().navigate(R.id.action_writing_step2_fg_to_writing_step3_fg)
        }
        view.findViewById<ImageView>(R.id.writing_sentence_step2_btn_back).setOnClickListener {
            it.findNavController().navigate(R.id.action_writing_step2_fg_to_writing_step1_fg)

        }
        return view
    }

}