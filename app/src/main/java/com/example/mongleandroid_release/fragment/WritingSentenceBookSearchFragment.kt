package com.example.mongleandroid_release.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.MainActivity.Companion.book_result
import com.example.mongleandroid_release.activity.WritingSentenceActivity
import com.example.mongleandroid_release.adapter.ItemDecoration
import com.example.mongleandroid_release.adapter.WritingSentenceBookSearchAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.data.request.RequestWritingSentenceData
import com.example.mongleandroid_release.network.data.response.BookData
import com.example.mongleandroid_release.network.data.response.ResponseWritingSentenceBookSearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WritingSentenceBookSearchFragment : Fragment() {

    lateinit var writingSentenceBookSearchAdapter: WritingSentenceBookSearchAdapter
    val datas: MutableList<BookData>? = mutableListOf<BookData>()

    private var keyword :String = ""
    private var title :String = ""
    private var author :String = ""
    private var publisher :String = ""


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


        // rv 동작 준비
        val myLayoutManager = LinearLayoutManager(this.context)
        view.findViewById<RecyclerView>(R.id.writing_sentence_book_search_rv).layoutManager = myLayoutManager
        writingSentenceBookSearchAdapter = context?.let { WritingSentenceBookSearchAdapter(it) }!!
        view.findViewById<RecyclerView>(R.id.writing_sentence_book_search_rv).adapter = writingSentenceBookSearchAdapter
        view.findViewById<RecyclerView>(R.id.writing_sentence_book_search_rv).addItemDecoration(
            ItemDecoration()
        )


        // 검색 창
        view.findViewById<EditText>(R.id.writing_sentence_book_search_et_search).addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

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

        // 검색창 비우기
        view.findViewById<ImageView>(R.id.writing_sentence_book_search_btn_delete).setOnClickListener {
            view.findViewById<EditText>(R.id.writing_sentence_book_search_et_search).setText("")
        }

        // 검색 버튼
        view.findViewById<ImageView>(R.id.writing_sentence_book_search_btn_search).setOnClickListener {

            // 서버 통신 및 rv 게시, user reaction
            keyword = view.findViewById<EditText>(R.id.writing_sentence_book_search_et_search).text.toString()
            if(keyword.isNullOrBlank()){

            }else{
                val bookSearchWord = view.findViewById<EditText>(R.id.writing_sentence_book_search_et_search).text.toString()
                book_result = bookSearchWord.trim()

                bookSearch(keyword, view)
            }

        }

    }


    // (/post/bookSearch?query={query}) API 연결
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