package com.example.mongleandroid_release.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.WritingSentenceBookSearchAdapter
import com.example.mongleandroid_release.fragment.WritingSentenceBookSearchFragmentDirections
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.data.response.ResponseWritingSentenceBookSearchData
import com.example.mongleandroid_release.showKeyboard
import kotlinx.android.synthetic.main.activity_theme_writing_sentence_book_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThemeWritingSentenceBookSearchActivity : AppCompatActivity() {

    lateinit var writingSentenceBookSearchAdapter: WritingSentenceBookSearchAdapter

    private var keyword :String = ""
    private var title :String = ""
    private var author :String = ""
    private var publisher :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_writing_sentence_book_search)

        // X 버튼 눌렀을 때 나가기
        theme_writing_sentence_book_search_btn_out.setOnClickListener {
            finish()
        }

        //검색창 비우기
        theme_writing_sentence_book_search_btn_delete.setOnClickListener {
            theme_writing_sentence_book_search_et_search.setText("")
        }

        //포커스는 검색창에
        theme_writing_sentence_book_search_et_search.requestFocus()

        // 키보드 등장
        theme_writing_sentence_book_search_et_search.showKeyboard()

        // 글자수 카운팅 및 경고 박스
        theme_writing_sentence_book_search_et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 글자수 세기
                theme_writing_sentence_book_search_tv_cnt.text= theme_writing_sentence_book_search_et_search.text.toString().length.toString()
            }

        })

        // 검색 버튼
        theme_writing_sentence_book_search_btn_search.setOnClickListener {

            keyword = theme_writing_sentence_book_search_et_search.text.toString()
            if (keyword.isNullOrBlank()) {

            } else {
                val bookSearchWord = theme_writing_sentence_book_search_et_search.text.toString()

            }
        }

    }

    private fun bookSearch(keyword: String, view: View) {
        val call: Call<ResponseWritingSentenceBookSearchData> = RequestToServer.service.RequestWritingSentenceBookSearch(keyword = keyword)
        call.enqueue(object : Callback<ResponseWritingSentenceBookSearchData> {
            @SuppressLint("LongLogTag")
            override fun onFailure(call: Call<ResponseWritingSentenceBookSearchData>, t: Throwable) {
                Log.e("ResponseWritingSentenceBookSearchData 통신실패",t.toString())
            }
            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<ResponseWritingSentenceBookSearchData>, response: Response<ResponseWritingSentenceBookSearchData>) {
                if (response.isSuccessful) {
                    response.body().let { body ->
                        Log.e(
                                "ResponseWritingSentenceBookSearchData 통신응답바디",
                                "status: ${body!!.staus} data : ${body.message}"
                        )

                        if(body.data.isNullOrEmpty()){
                            //if 서버 통신 성공 && 결과 없음
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_book_search_cl_before).visibility = View.GONE
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_book_search_cl_after).visibility = View.GONE
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_book_search_cl_no).visibility = View.VISIBLE

                        }else{
                            // rv 동작 게시
                            writingSentenceBookSearchAdapter.datas = body.data
                            writingSentenceBookSearchAdapter.notifyDataSetChanged()
                            //if 서버 통신 성공 && 결과 있음
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_book_search_cl_before).visibility = View.GONE
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_book_search_cl_after).visibility = View.VISIBLE
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_book_search_cl_no).visibility = View.GONE
                            // user reaction : 검색 결과 키워드 변경
                            view.findViewById<TextView>(R.id.writing_sentence_book_search_tv_keyword).text = keyword
                            // user reaction : 검색 결과 건 수
                            view.findViewById<TextView>(R.id.writing_sentence_book_search_cnt).text = "총 " + body.data.size.toString() + "건"

                            //리사이클러뷰 아이템 클릭리스너 등록
                            writingSentenceBookSearchAdapter.setItemClickListener(object : WritingSentenceBookSearchAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    Log.d("SSS","${position}번 리스트 선택")
                                    title = view.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_title).text.toString()
                                    author = view.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_author).text.toString()
                                    publisher = view.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_publisher).text.toString()

                                    // (/post/sentence) req data init (2/6):: thumbnail
                                    WritingSentenceActivity.writingSentenceData.thumbnail = view.findViewById<TextView>(R.id.item_writing_sentence_book_result_tv_thumbnail).text.toString()


                                    // 아이템을 선택했다면 step2로 이동
                                    val action = WritingSentenceBookSearchFragmentDirections.
                                    actionWritingSentenceBookSearchFragmentToWritingSentenceStep2Fragment(title, author, publisher)
                                    view.findNavController().navigate(action)
                                }
                            })
                        }
                    }
                }else{
                    //if 서버 통신 실패
                    Log.d("서버 통신", "서버 통신 실패")
                }

            }
        })
    }

}