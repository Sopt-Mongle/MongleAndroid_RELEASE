package com.mongle.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.ModifyLibraryWrittenSentenceActivity
import com.mongle.mongleandroid_release.activity.SentenceDetailViewActivity
import com.mongle.mongleandroid_release.adapter.LibrarySentenceAdapter
import com.mongle.mongleandroid_release.adapter.LibrarySentenceClickAdapter
import com.mongle.mongleandroid_release.change_gone
import com.mongle.mongleandroid_release.change_visible
import com.mongle.mongleandroid_release.dialog.DialogDeleteSentence
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.data.LibrarySentenceData
import com.mongle.mongleandroid_release.network.data.response.ResponseDeleteSentenceWritten
import com.mongle.mongleandroid_release.network.data.response.ResponseLibrarySentenceData
import kotlinx.android.synthetic.main.fragment_library_sentence.*
import kotlinx.android.synthetic.main.item_library_sentence_click.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibrarySentenceFragment : Fragment() {

    val requestToServer = RequestToServer

    lateinit var librarySentenceAdapter: LibrarySentenceAdapter
    lateinit var librarySentenceClickAdapter: LibrarySentenceClickAdapter
//    private lateinit var listener : MoreBtnOnClickedListener

    val librarySentenceData = mutableListOf<LibrarySentenceData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library_sentence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        librarySentenceAdapter = LibrarySentenceAdapter(view.context)
//        rv_library_sentence.adapter = librarySentenceAdapter
        //        loadDatas()
//        librarySentenceClickAdapter = LibrarySentenceClickAdapter(view.context)
        //아래 코드까지 쓰면 최종적으로 리사이클러뷰에 불러들어오는 어댑터가 클릭 시 어댑터이기 때문에 초기값을 하나만 주기 위해 아래처럼 했다.
//        rv_library_sentence.adapter = librarySentenceClickAdapter


        requestLibrarySentenceData()

        //클릭 시에 어댑터의 값만 바꿔주는 걸로 item 바꿀 수 있음.
        rdbtn_saved_sentence.setOnClickListener {
            if (rdbtn_saved_sentence.isChecked) {
//                rv_library_sentence.adapter = librarySentenceAdapter
//                loadDatas()

                requestLibrarySentenceData()
            } else {

            }
        }


        rdbtn_making_sentence.setOnClickListener {
            if (rdbtn_making_sentence.isChecked) {
//                rv_library_sentence.adapter = librarySentenceClickAdapter
                requestLibrarySentenceClickData()


            } else {


            }

        }

        //수정, 삭제 버튼 눌렀을 때
//        library_sentence_more.setOnClickListener {
//
//        }

        //fragment 안에 있는 recyclerView안에 있는 item의 특정 ...버튼 누르면 EditSentenceActicity로 넘어가는
//        library_sentence_more.setOnClickListener {
//
//            activity?.let {
//                val intent = Intent(context, EditSentenceActivity::class.java)
//                startActivity(intent) }
//
//        }


    }


    //SearchFragment로 이동
//    private fun replaceFragment(fragment: Fragment) {
//        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
//        transaction.replace(R.id.main_activity_fg, fragment)
//        transaction.commit()
//
//    }






//    private fun loadDatas() {
//        librarySentenceData.apply {
//            add(
//                LibrarySentenceData(
//                    tv_library_sentence_themename = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다. ",
//                    tv_item_library_sentence_username = "예스리"
//                )
//            )
//            add(
//                LibrarySentenceData(
//                    tv_library_sentence_themename = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다. ",
//                    tv_item_library_sentence_username = "예스리"
//                )
//            )
//            add(
//                LibrarySentenceData(
//                    tv_library_sentence_themename = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다. ",
//                    tv_item_library_sentence_username = "예스리"
//                )
//            )
//            add(
//                LibrarySentenceData(
//                    tv_library_sentence_themename = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다. ",
//                    tv_item_library_sentence_username = "예스리"
//                )
//            )
//            add(
//                LibrarySentenceData(
//                    tv_library_sentence_themename = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다. ",
//                    tv_item_library_sentence_username = "예스리"
//                )
//            )
//            add(
//                LibrarySentenceData(
//                    tv_library_sentence_themename = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다. ",
//                    tv_item_library_sentence_username = "예스리"
//                )
//            )
//            add(
//                LibrarySentenceData(
//                    tv_library_sentence_themename = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다. ",
//                    tv_item_library_sentence_username = "예스리"
//                )
//            )
//            add(
//                LibrarySentenceData(
//                    tv_library_sentence_themename = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다. ",
//                    tv_item_library_sentence_username = "예스리"
//                )
//            )
//            add(
//                LibrarySentenceData(
//                    tv_library_sentence_themename = "번아웃을 극복하고 싶을 때 봐야하는 문장",
//                    tv_library_sentence_sentence = "결국 봄이 언제나 찾아왔지만, 하마터면 오지 않을 뻔 했던 봄을 생각하면 마음이 섬찟해진다. ",
//                    tv_item_library_sentence_username = "예스리"
//                )
//            )
//
//
//        }
//
//
//
//        librarySentenceAdapter.data_sen = librarySentenceData
//        librarySentenceAdapter.notifyDataSetChanged()
//    }

    private fun requestLibrarySentenceData() {
        requestToServer.service.lookLibrarySentence(
            token = SharedPreferenceController.getAccessToken(requireView().context)
        )
            .enqueue(
                object : Callback<ResponseLibrarySentenceData> {
                    override fun onFailure(call: Call<ResponseLibrarySentenceData>, t: Throwable) {
                        Log.d("통신실패", "${t}")
                    }

                    override fun onResponse(
                        call: Call<ResponseLibrarySentenceData>,
                        response: Response<ResponseLibrarySentenceData>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("내 서재 문장 조회 성공", "${response.body()}")
//                            if (response.body()!!.data!!.save.isNullOrEmpty()) {
//                            Log.d("내 서재 문장 조회 null", "${response.body()}")
//                            } else {
                                librarySentenceAdapter = LibrarySentenceAdapter(
                                    view!!.context,
                                    response.body()!!.data!!.save
                                )
                                rv_library_sentence.adapter = librarySentenceAdapter
                                librarySentenceAdapter.notifyDataSetChanged()

                                librarySentenceAdapter.setItemClickListener(object :
                                    LibrarySentenceAdapter.ItemClickListener {

                                    override fun onClick(view: View, position: Int) {
                                        activity?.let {
                                            val intent =
                                                Intent(
                                                    context,
                                                    SentenceDetailViewActivity::class.java
                                                )
                                            intent.putExtra(
                                                "param",
                                                response.body()!!.data!!.save[position].sentenceIdx
                                            )
                                            startActivity(intent)
                                        }
                                    }
                                })
//                            }
                        }

                    }
                }
            )
    }

    private fun requestLibrarySentenceClickData() {
        requestToServer.service.lookLibrarySentence(
            token = SharedPreferenceController.getAccessToken(requireView().context)
        )
            .enqueue(
                object : Callback<ResponseLibrarySentenceData> {
                    override fun onFailure(call: Call<ResponseLibrarySentenceData>, t: Throwable) {
                        Log.d("통신실패", "${t}")
                    }

                    override fun onResponse(
                        call: Call<ResponseLibrarySentenceData>,
                        response: Response<ResponseLibrarySentenceData>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("내 서재 문장 클릭 조회 성공", "${response.body()}")
//                            if (response.body()!!.data!!.write.isNullOrEmpty()) {
//                                Log.d("내 서재 문장 클릭 조회 null", "${response.body()}")
//                            } else {
                                librarySentenceClickAdapter = LibrarySentenceClickAdapter(
                                    view!!.context,
                                    response.body()!!.data!!.write
                                )
                                rv_library_sentence.adapter = librarySentenceClickAdapter
                                librarySentenceClickAdapter.notifyDataSetChanged()

                                librarySentenceClickAdapter.setItemClickListener(object :
                                    LibrarySentenceClickAdapter.ItemClickListener {

                                    override fun onClick(view: View, position: Int) {
                                        activity?.let {
                                            val intent = Intent(
                                                context,
                                                SentenceDetailViewActivity::class.java
                                            )
                                            intent.putExtra(
                                                "param",
                                                response.body()!!.data!!.write[position].sentenceIdx
                                            )
                                            startActivity(intent)
                                        }
                                        change_gone(library_sentence_more_box)
                                    }

                                    override fun onClickMore(view: View, position: Int) {
                                        if (cb_library_sentence_more.isChecked) {
                                            change_visible(library_sentence_more_box)
                                        } else change_gone(library_sentence_more_box)
                                    }

                                    override fun onClickModify(view: View, position: Int) {
                                        val intent = Intent(
                                            context,
                                            ModifyLibraryWrittenSentenceActivity::class.java
                                        )
                                        intent.putExtra(
                                            "param",
                                            response.body()!!.data!!.write[position].sentenceIdx
                                        )
                                        intent.putExtra(
                                            "sentence",
                                            response.body()!!.data!!.write[position].sentence
                                        )
                                        //LibraryFragment 새로고침
//                                        var frg: Fragment? = null
//                                        frg =
//                                            supportFragmentManager().findFragmentByTag("Your_Fragment_TAG")
//                                        val ft: FragmentTransaction =
//                                            supportFragmentManager().beginTransaction()
//                                        ft.detach(frg)
//                                        ft.attach(frg)
//                                        ft.commit()


                                        startActivity(intent)

                                        change_gone(library_sentence_more_box)
                                        //정보 같이 넘겨주기
                                    }

                                    override fun onClickDelete(view: View, position: Int) {
                                        change_gone(library_sentence_more_box)

                                        //삭제 버튼 눌렀을 때 통신하는 부분
                                        val dlg = DialogDeleteSentence(view.context)
                                        dlg.start()
                                        dlg.setOnClickListener { content ->
                                            if (content == "삭제") {
                                                requestToServer.service.DeleteSentenceWritten(
                                                    token = SharedPreferenceController.getAccessToken(
                                                        view.context
                                                    ),
                                                    params = response.body()!!.data!!.write[position].sentenceIdx

                                                ).enqueue(
                                                    object :
                                                        Callback<ResponseDeleteSentenceWritten> {
                                                        override fun onResponse(
                                                            call: Call<ResponseDeleteSentenceWritten>,
                                                            response: Response<ResponseDeleteSentenceWritten>
                                                        ) {
                                                            if (response.isSuccessful) {
                                                                //프래그먼트 새로고침

                                                            }
                                                        }

                                                        override fun onFailure(
                                                            call: Call<ResponseDeleteSentenceWritten>,
                                                            t: Throwable
                                                        ) {
                                                            Log.d("내 서재 쓴 문장 삭제 통신 실패", "$t")

                                                        }

                                                    }
                                                )
                                            }

                                        }
                                    }
                                })

//                            }

                        }

                    }
                }
            )
    }


//    fun setOnClickListener(listener: (String) -> Unit) {
//        this.listener = object:
//            MoreBtnOnClickedListener {
//            override fun onOKClicked(content: String) {
//                listener(content)
//            }
//        }
//    }
//
//
//    interface MoreBtnOnClickedListener {
//        fun onOKClicked(content : String)
//    }

    //프래그먼트 새로 고침
//    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
//        var ft: FragmentTransaction = fragmentManager.beginTransaction()
//        ft.detach(fragment).attach(fragment).commit()
//    }

}

