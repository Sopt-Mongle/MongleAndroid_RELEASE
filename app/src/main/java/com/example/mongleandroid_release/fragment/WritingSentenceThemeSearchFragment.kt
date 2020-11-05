package com.example.mongleandroid_release.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.MainActivity
import com.example.mongleandroid_release.activity.WritingSentenceActivity
import com.example.mongleandroid_release.adapter.ItemDecoration
import com.example.mongleandroid_release.adapter.WritingSentenceThemeSearchAdapter
import com.example.mongleandroid_release.adapter.WritingSentenceThemeSearchFirstAdapter
import com.example.mongleandroid_release.adapter.viewholder.WritingSentenceThemeSearchFirstViewHolder
import com.example.mongleandroid_release.adapter.viewholder.WritingSentenceThemeSearchViewHolder
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.request.RequestWritingSentenceData
import com.example.mongleandroid_release.network.data.response.ResponseWritingSentenceThemeSearchFirstData
import com.example.mongleandroid_release.network.data.response.FirstThemeData
import com.example.mongleandroid_release.network.data.response.ResponseSearchThemeData
import com.example.mongleandroid_release.network.data.response.SearchTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WritingSentenceThemeSearchFragment : Fragment() {

    lateinit var wrtingSentenceThemeSearchFirstAdapter: WritingSentenceThemeSearchFirstAdapter
    lateinit var wrtingSentenceThemeSearchAdapter: WritingSentenceThemeSearchAdapter
    private var keyword: String = ""
    private var theme: String = ""
    private var img: String = ""

    private var imgChk : Boolean = false
    private var imgChked : Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.writing_sentence_theme_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // rv 동작 준비
        val myLayoutManager = GridLayoutManager(this.context, 2)
        view.findViewById<RecyclerView>(R.id.writing_sentence_theme_search_rv).layoutManager =
            myLayoutManager
        wrtingSentenceThemeSearchFirstAdapter =
            context?.let { WritingSentenceThemeSearchFirstAdapter(it) }!!
        wrtingSentenceThemeSearchFirstAdapter.setHasStableIds(true)
        wrtingSentenceThemeSearchAdapter = context?.let { WritingSentenceThemeSearchAdapter(it) }!!
        wrtingSentenceThemeSearchAdapter.setHasStableIds(true)
        // 디폴트 rv 준비
        themeSearchFirst(view)


        // 검색 창
        view.findViewById<EditText>(R.id.writing_sentence_theme_search_et_search)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    view.findViewById<ImageView>(R.id.writing_sentence_theme_search_btn_delete).visibility =
                        View.VISIBLE

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

        // 나가기 버튼
        view.findViewById<ImageView>(R.id.writing_sentence_theme_search_btn_back)
            .setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_writing_sentence_theme_search_fragment_to_writing_sentence_step3_fragment)
                // 책 제목 넘겨 줌
            }

        // 검색창 비우기
        view.findViewById<ImageView>(R.id.writing_sentence_theme_search_btn_delete)
            .setOnClickListener {
                view.findViewById<EditText>(R.id.writing_sentence_theme_search_et_search)
                    .setText("")
            }

        // 검색 버튼
        view.findViewById<ImageView>(R.id.writing_sentence_theme_search_btn_search)
            .setOnClickListener {

                // 서버 통신 및 rv 게시, user reaction
                keyword =
                    view.findViewById<EditText>(R.id.writing_sentence_theme_search_et_search).text.toString()

//            if(!keyword.isNullOrBlank()) requestData("",keyword, view)
                if (keyword.isNullOrBlank()) {

                } else {
                    themeSearch(keyword, view)
                    MainActivity.search_result = keyword.trim()


                }

            }

        // 테마 선택하기 버튼
        view.findViewById<TextView>(R.id.writing_sentence_theme_search_btn_next)
            .setOnClickListener {

//                if(imgChk){
//                    // user reaction : 테마를 선택해주세요 !
//                    view.findViewById<TextView>(R.id.writing_sentence_theme_search_cl_warning).visibility =
//                        View.GONE
//                    // 아이템을 선택했다면 step3로 이동
                    val action =
                        WritingSentenceThemeSearchFragmentDirections.actionWritingSentenceThemeSearchFragmentToWritingSentenceStep3Fragment(
                            theme
                        )
                    view.findNavController().navigate(action)


//                }else{
//                    // 아니라면
//                    // user reaction : 테마를 선택하세요!
//                    view.findViewById<ConstraintLayout>(R.id.writing_sentence_theme_search_cl_warning).visibility = View.VISIBLE
//                }


            }


    }


    private fun themeSearchFirst(view: View) {

        val call: Call<ResponseWritingSentenceThemeSearchFirstData> =
            RequestToServer.service.RequestWritingSentenceThemeSearch()

        call.enqueue(object : Callback<ResponseWritingSentenceThemeSearchFirstData> {
            @SuppressLint("LongLogTag")
            override fun onFailure(
                call: Call<ResponseWritingSentenceThemeSearchFirstData>,
                t: Throwable
            ) {
                Log.e("ResponseWritingSentenceThemeSearchData 통신실패", t.toString())
            }

            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<ResponseWritingSentenceThemeSearchFirstData>,
                response: Response<ResponseWritingSentenceThemeSearchFirstData>
            ) {
                if (response.isSuccessful) {
                    response.body().let { body ->
                        Log.e(
                            "ResponseWritingSentenceThemeSearchData 통신응답바디",
                            "status: ${body!!.status} data : ${body.message}"
                        )

                        if (body.data.isNullOrEmpty()) {
                            //if 서버 통신 성공 && 결과 없음
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_theme_search_cl_after).visibility =
                                View.GONE
                            view.findViewById<LinearLayout>(R.id.writing_sentence_theme_search_ll_no).visibility =
                                View.VISIBLE

                        } else {
                            // rv 동작 게시
                            view.findViewById<RecyclerView>(R.id.writing_sentence_theme_search_rv).adapter =
                                wrtingSentenceThemeSearchFirstAdapter
                            view.findViewById<RecyclerView>(R.id.writing_sentence_theme_search_rv)
                                .addItemDecoration(ItemDecoration())
                            wrtingSentenceThemeSearchFirstAdapter.datas = body.data
                            wrtingSentenceThemeSearchFirstAdapter.notifyDataSetChanged()
                            //if 서버 통신 성공 && 결과 있음
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_theme_search_cl_after).visibility =
                                View.VISIBLE
                            view.findViewById<LinearLayout>(R.id.writing_sentence_theme_search_ll_no).visibility =
                                View.GONE

                            //리사이클러뷰 아이템 클릭리스너 등록
                            wrtingSentenceThemeSearchFirstAdapter.setItemClickListener(object :
                                WritingSentenceThemeSearchFirstAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    Log.d("SSS", "${position}번 리스트 선택 && imgChked :: ${imgChked}")

                                    // 선택한 테마에 대해 action에 담아줄 테마 이름 넣어줌
                                    theme =
                                        view.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_title).text.toString()

                                    if(imgChked == position){ // user reaction : 테마 선택 해제 (같은 것을 두 번 누름) -> 선택된게 없음
                                        view.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img_chk).visibility = View.GONE
                                        imgChk = false
                                    }else{
                                        if(imgChked != -1){ // user reaction : 테마 선택 해제 (다른 것 선택) -> 선택된게 1개 있음
                                            wrtingSentenceThemeSearchFirstAdapter.onBindViewHolder(
                                                WritingSentenceThemeSearchFirstViewHolder(view), imgChked)
                                            view.findViewById<RecyclerView>(R.id.writing_sentence_theme_search_rv)
                                                .findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img_chk).visibility = View.GONE
                                        }
                                        // user reaction : 테마 선택
                                        view.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img_chk).visibility = View.VISIBLE
                                        imgChked = wrtingSentenceThemeSearchFirstAdapter.getItemId(position)
                                            .toInt()
                                        imgChk = true

                                    }


                                    // (/post/sentence) req data init (6/6):: themeIdx
                                    WritingSentenceActivity.writingSentenceData.themeIdx =
                                        Integer.parseInt(
                                            view.findViewById<TextView>(
                                                R.id.item_writing_sentence_theme_result_tv_themeIdx
                                            ).text.toString()
                                        )

                                }
                            })
                        }
                    }
                } else {
                    //if 서버 통신 실패
                    Log.d("서버 통신", "서버 통신 실패")
                }

            }
        })
    }

    private fun themeSearch(keyword: String, view: View) {

        val call: Call<ResponseSearchThemeData> =
            RequestToServer.service.requestSearchTheme(token = context?.let {
                SharedPreferenceController.getAccessToken(it)
            }, words = keyword)

        call.enqueue(object : Callback<ResponseSearchThemeData> {
            @SuppressLint("LongLogTag")
            override fun onFailure(call: Call<ResponseSearchThemeData>, t: Throwable) {
                Log.e("ResponseSearchThemeData 통신실패", t.toString())
            }

            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<ResponseSearchThemeData>,
                response: Response<ResponseSearchThemeData>
            ) {
                if (response.isSuccessful) {
                    response.body().let { body ->
                        Log.e(
                            "ResponseSearchThemeData 통신응답바디",
                            "status: ${body!!.status} data : ${body.message}"
                        )

                        if (body.data.isNullOrEmpty()) {
                            //if 서버 통신 성공 && 결과 없음
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_theme_search_cl_after).visibility =
                                View.GONE
                            view.findViewById<LinearLayout>(R.id.writing_sentence_theme_search_ll_no).visibility =
                                View.VISIBLE

                        } else {
                            // rv 동작 게시
                            view.findViewById<RecyclerView>(R.id.writing_sentence_theme_search_rv).adapter =
                                wrtingSentenceThemeSearchAdapter
                            view.findViewById<RecyclerView>(R.id.writing_sentence_theme_search_rv)
                                .addItemDecoration(ItemDecoration())
                            wrtingSentenceThemeSearchAdapter.datas = body.data
                            wrtingSentenceThemeSearchAdapter.notifyDataSetChanged()
                            //if 서버 통신 성공 && 결과 있음
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_theme_search_cl_after).visibility =
                                View.VISIBLE
                            view.findViewById<LinearLayout>(R.id.writing_sentence_theme_search_ll_no).visibility =
                                View.GONE
                            // user reaction : 검색 결과 키워드 변경
                            view.findViewById<TextView>(R.id.writing_sentence_theme_search_tv_keyword).text =
                                keyword
                            // user reaction : 검색 결과 건 수
                            view.findViewById<TextView>(R.id.writing_sentence_theme_search_cnt).text =
                                "총 " + body.data.size.toString() + "건"
                            // 위 두 가지 동작을 보이게 함
                            view.findViewById<ConstraintLayout>(R.id.writing_sentence_theme_search_yes).visibility = View.VISIBLE

                            //리사이클러뷰 아이템 클릭리스너 등록
                            wrtingSentenceThemeSearchAdapter.setItemClickListener(object :
                                WritingSentenceThemeSearchAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    Log.d("SSS", "${position}번 리스트 선택")


                                    // 선택한 테마에 대해 action에 담아줄 테마 이름 넣어줌
                                    theme =
                                        view.findViewById<TextView>(R.id.item_writing_sentence_theme_result_tv_title).text.toString()

                                    if(imgChked == position){ // user reaction : 테마 선택 해제 (같은 것을 두 번 누름) -> 선택된게 없음
                                        view.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img_chk).visibility = View.GONE
                                        imgChk = false
                                    }else{
                                        if(imgChked != -1){ // user reaction : 테마 선택 해제 (다른 것 선택) -> 선택된게 1개 있음
                                            wrtingSentenceThemeSearchAdapter.onBindViewHolder(
                                                WritingSentenceThemeSearchViewHolder(view), imgChked)
                                            view.findViewById<RecyclerView>(R.id.writing_sentence_theme_search_rv)
                                                .findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img_chk).visibility = View.GONE
                                        }
                                        // user reaction : 테마 선택
                                        view.findViewById<ImageView>(R.id.item_writing_sentence_theme_result_img_chk).visibility = View.VISIBLE
                                        imgChked = wrtingSentenceThemeSearchAdapter.getItemId(position)
                                            .toInt()
                                        imgChk = true

                                    }


                                    // (/post/sentence) req data init (6/6):: themeIdx
                                    WritingSentenceActivity.writingSentenceData.themeIdx =
                                        Integer.parseInt(
                                            view.findViewById<TextView>(
                                                R.id.item_writing_sentence_theme_result_tv_themeIdx
                                            ).text.toString()
                                        )

                                }
                            })
                        }
                    }
                } else {
                    //if 서버 통신 실패
                    Log.d("서버 통신", "서버 통신 실패")
                }

            }
        })
    }


}