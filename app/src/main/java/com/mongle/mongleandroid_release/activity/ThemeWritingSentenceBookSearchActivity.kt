package com.mongle.mongleandroid_release.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.adapter.ItemDecoration
import com.mongle.mongleandroid_release.adapter.ThemeSentenceBookSearchAdapter
import com.mongle.mongleandroid_release.goNextPage
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.data.response.ResponseWritingSentenceBookSearchData
import com.mongle.mongleandroid_release.showKeyboard
import com.mongle.mongleandroid_release.unshowKeyboard
import kotlinx.android.synthetic.main.activity_theme_writing_sentence_book_search.*
import kotlinx.android.synthetic.main.item_writing_sentence_book_result.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThemeWritingSentenceBookSearchActivity : AppCompatActivity() {

    lateinit var themeSentenceBookSearchAdapter : ThemeSentenceBookSearchAdapter

    private var keyword :String = ""
    private var title :String = ""
    private var author :String = ""
    private var publisher :String = ""

    companion object {
        var theme_book_result = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_writing_sentence_book_search)

        writing_sentence_book_search_cl_before.visibility = View.VISIBLE
        writing_sentence_book_search_cl_after.visibility = View.GONE
        writing_sentence_book_search_rv.visibility = View.GONE
        writing_sentence_book_search_cl_no.visibility = View.GONE

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

        //rv 동작
        themeSentenceBookSearchAdapter = ThemeSentenceBookSearchAdapter(this)
        writing_sentence_book_search_rv.adapter = themeSentenceBookSearchAdapter
        writing_sentence_book_search_rv.addItemDecoration(ItemDecoration())

        // 검색 버튼 눌렀을 때
        theme_writing_sentence_book_search_btn_search.setOnClickListener {

            //키보드 제어
            theme_writing_sentence_book_search_et_search.unshowKeyboard()

            //검색 결과가 있으면
            goNextPage(writing_sentence_book_search_cl_before, writing_sentence_book_search_cl_after)
            writing_sentence_book_search_rv.visibility = View.VISIBLE

            //서버 데이타를 넣어줌
            val bookSearchWord = theme_writing_sentence_book_search_et_search.text.toString()
            theme_book_result = bookSearchWord.trim()
            requestData(bookSearchWord)

        }


    }

    private fun requestData(keyword: String) {
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
                                "status: ${body!!.staus} data : ${body!!.message}"
                        )

                        if(body.data.isNullOrEmpty()){
                            //if 서버 통신 성공 && 결과 없음
                            writing_sentence_book_search_cl_before.visibility = View.GONE
                            writing_sentence_book_search_cl_after.visibility = View.GONE
                            writing_sentence_book_search_cl_no.visibility = View.VISIBLE

                        }else{
                            // rv 동작 게시
                            themeSentenceBookSearchAdapter.datas = body.data
                            themeSentenceBookSearchAdapter.notifyDataSetChanged()
                            //if 서버 통신 성공 && 결과 있음
//                            empty_tv1.visibility = View.GONE
//                            empty_tv2.visibility = View.GONE
                            writing_sentence_book_search_cl_before.visibility = View.GONE
                            writing_sentence_book_search_cl_after.visibility = View.VISIBLE
                            writing_sentence_book_search_cl_no.visibility = View.GONE
                            writing_sentence_book_search_tv_keyword.text = keyword
                            writing_sentence_book_search_cnt.text = "총 " + body.data.size.toString() + "건"

                            //리사이클러뷰 아이템 클릭리스너 등록
                            themeSentenceBookSearchAdapter.setItemClickListener(object : ThemeSentenceBookSearchAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    Log.d("SSS","${position}번 리스트 선택")

                                    val intent = Intent(this@ThemeWritingSentenceBookSearchActivity, ThemeWritingSentenceBookActivity::class.java)
                                    intent.putExtra("title",response.body()!!.data[position].title)
                                    intent.putExtra("author", response.body()!!.data[position].authors[0])
                                    intent.putExtra("publisher", response.body()!!.data[position].publisher)
                                    intent.putExtra("thumbnail", response.body()!!.data[position].thumbnail)
                                    DetailThemeActivity.writingSentenceInThemeData.thumbnail = item_writing_sentence_book_result_img.toString()
                                    setResult(1, intent)
                                    finish()
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