package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.MainActivity
import com.example.mongleandroid_release.activity.SentenceDetailViewActivity
import com.example.mongleandroid_release.activity.WritingSentenceActivity

class WritingSentenceFinishFragment : Fragment() {

    private val args: WritingSentenceFinishFragmentArgs by navArgs()

    private val act by lazy { context as WritingSentenceActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.writing_sentence_finish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.writing_sentence_finish_btn_sentence).setOnClickListener {
            val intent = Intent(activity , SentenceDetailViewActivity::class.java)
//            Log.d("args.sentenceIdx", "${args.sentenceIdx}")
            Log.d("args.sentenceIdx", "${args.sentenceIdx}")
            args.sentenceIdx
            intent.putExtra("param", args.sentenceIdx)
            startActivity(intent)
            act.finish()
        }
        view.findViewById<TextView>(R.id.writing_sentence_finish_btn_main).setOnClickListener {
            val intent = Intent(activity , MainActivity::class.java)
            startActivity(intent)
            act.finish()
        }
    }

}