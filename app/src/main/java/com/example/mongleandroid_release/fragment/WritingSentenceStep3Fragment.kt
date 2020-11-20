package com.example.mongleandroid_release.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.WritingSentenceActivity
import com.example.mongleandroid_release.forProgressBar
import com.example.mongleandroid_release.forProgressOn
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.ResponseWritingSentenceData
import kotlinx.android.synthetic.main.onboarding_step1.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WritingSentenceStep3Fragment : Fragment() {

    private val args: WritingSentenceStep3FragmentArgs by navArgs()
    private var sentenceIdx = 0

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


    @SuppressLint("UseValueOf")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // booksearch에서 받아온 값 뿌리기
        if (!args.theme.isNullOrEmpty()){
            view.findViewById<TextView>(R.id.writing_sentence_step3_tv_theme).text = args.theme
        }



        if(!view.findViewById<TextView>(R.id.writing_sentence_step3_tv_theme).text.isNullOrEmpty()){
            // 초록불 켜기
            view.findViewById<TextView>(R.id.writing_sentence_step3_tv_theme).
            forProgressOn(view.findViewById(R.id.writing_sentence_step3_pgb_out3),
                view.findViewById(R.id.writing_sentence_step3_pgb_in3))

            // 경고 해제
            view.findViewById<LinearLayout>(R.id.writing_sentence_step3_ll_warning).visibility = View.GONE
            view.findViewById<TextView>(R.id.writing_sentence_step3_btn_theme)
                .setBackgroundResource(R.drawable.et_area)
        }


        // 다음 버튼
        view.findViewById<TextView>(R.id.writing_sentence_step3_btn_next).setOnClickListener {
            // 빈칸 경고
            if(view.findViewById<TextView>(R.id.writing_sentence_step3_tv_theme).text.isEmpty()) {
                view.findViewById<LinearLayout>(R.id.writing_sentence_step3_ll_warning).visibility = View.VISIBLE

                view.findViewById<TextView>(R.id.writing_sentence_step3_btn_theme)
                    .setBackgroundResource(R.drawable.et_area_red)
            }else{ //빈칸 없으면 다음으로
                // 문장 올리기

//                if(sentencePost() == 0){
//                    Log.d("writingSentenceData :: ", "${WritingSentenceActivity.writingSentenceData.sentence} \n" +
//                            " ${WritingSentenceActivity.writingSentenceData.author} \n" +
//                            " ${WritingSentenceActivity.writingSentenceData.publisher} \n" +
//                            " ${WritingSentenceActivity.writingSentenceData.themeIdx} \n" +
//                            " ${WritingSentenceActivity.writingSentenceData.title} \n"+
//                            " ${sentenceIdx} \n"
//                    )
//                    // step3로 이동
//                    val action = WritingSentenceStep3FragmentDirections.actionWritingSentenceStep3FragmentToWritingSentenceFinishFragment(sentenceIdx)
//                    view.findNavController().navigate(action)
//                }

                GlobalScope.launch {
                    sentencePost()
                    delay(100L)
                    Log.d("writingSentenceData :: ", "${WritingSentenceActivity.writingSentenceData.sentence} \n" +
                            " ${WritingSentenceActivity.writingSentenceData.author} \n" +
                            " ${WritingSentenceActivity.writingSentenceData.publisher} \n" +
                            " ${WritingSentenceActivity.writingSentenceData.themeIdx} \n" +
                            " ${WritingSentenceActivity.writingSentenceData.title} \n"+
                            " ${sentenceIdx} \n"
                    )
                    // step3로 이동
                    val action = WritingSentenceStep3FragmentDirections.actionWritingSentenceStep3FragmentToWritingSentenceFinishFragment(sentenceIdx)
                    view.findNavController().navigate(action)
                }


            }

        }

        // 뒤로가기 버튼
        view.findViewById<ImageView>(R.id.writing_sentence_step3_btn_back).setOnClickListener {
            it.findNavController().navigate(R.id.action_writing_step3_fg_to_writing_step2_fg)
        }

        // 테마 검색 버튼
        view.findViewById<TextView>(R.id.writing_sentence_step3_btn_theme).setOnClickListener{
            it.findNavController().navigate(R.id.action_writing_sentence_step3_fragment_to_writing_sentence_theme_search_fragment)

        }
    }

    private fun sentencePost(): Int{
        val call: Call<ResponseWritingSentenceData> = RequestToServer.service.RequestWritingSentence(token = context?.let {
            SharedPreferenceController.getAccessToken(it)
        }, body = WritingSentenceActivity.writingSentenceData)
        call.enqueue(object : Callback<ResponseWritingSentenceData> {
            @SuppressLint("LongLogTag")
            override fun onFailure(call: Call<ResponseWritingSentenceData>, t: Throwable) {
                Log.e("ResponseWritingSentenceData 통신실패",t.toString())
            }
            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<ResponseWritingSentenceData>,
                response: Response<ResponseWritingSentenceData>
            ) {
                if (response.isSuccessful){
                    response.body().let { body ->
                        sentenceIdx = body!!.data
                        Log.e("ResponseWritingSentenceData 통신응답바디", "status: ${body.status} message : ${body.message} data : ${sentenceIdx}\"")
                    }
                }

            }

        })
        return 0
    }

}