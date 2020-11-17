package com.example.mongleandroid_release.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.SettingActivity
import com.example.mongleandroid_release.adapter.*
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.response.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_library.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryFragment : Fragment() {

    val requestToServer = RequestToServer

    lateinit var libraryPagerAdapter: LibraryPagerAdapter

    var data_theme_save_num : Int = 0  // = mutableListOf<LibraryThemeSave>()
    var data_theme_write_num : Int = 0 // = mutableListOf<LibraryThemeWrite>()

    var data_theme_num : Int = 0 // = mutableListOf<LibraryThemeWrite>()
    var data_sentence_num : Int = 0 // = mutableListOf<LibraryThemeWrite>()

    var data_sentence_save_num : Int = 0
    var data_sentence_write_num : Int = 0

    var data_curator_num : Int = 0 //mutableListOf<LibraryCuratorData>()

    lateinit var txtUpper1 : TextView
    lateinit var txtUpper2 : TextView
    lateinit var txtUpper3 : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        var themeNumWrite : Int = data_theme_write_num.size
//        var themeNum : Int = data_theme_num.size

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false)

    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        requestMyProfile()
//    }

    fun requestMyProfile(){
        requestToServer.service.lookLibraryProfile(
            token = SharedPreferenceController.getAccessToken(requireView().context)
//            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHgiOjM2LCJuYW1lIjoi7ZWY7JiBMiIsImlhdCI6MTU5NDkxMTQzNiwiZXhwIjoxNTk1MDg0MjM2LCJpc3MiOiJtb25nbGUifQ.1QUSDWRk_C3bYxrR95qqD4AJNIKVz5P6EbAIhd58jsU"
        )

            .enqueue(
                object : Callback<ResponseMainLibraryData> {
                    override fun onFailure(call: Call<ResponseMainLibraryData>, t: Throwable) {
                        Log.e("내 서재 통신 실패", "${t}")
                    }

                    override fun onResponse(
                        call: Call<ResponseMainLibraryData>,
                        response: Response<ResponseMainLibraryData>
                    ) {
                        if (response.isSuccessful) {
                            Log.e("내 서재 프로필 조회 성공", "${response.body()}")

//                            Glide.with(img_library_profile).load(MainLibrary.) = response.body()!!.data[0].img
                            Glide.with(view!!.context).load(response.body()!!.data[0].img).into(img_library_profile)
                            tx_library_username.text = response.body()!!.data[0].name
                            tx_library_contents.text = response.body()!!.data[0].keyword
                            tx_library_keyword.text = response.body()!!.data[0].introduce



                        }
                    }
                }
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestMyProfile()
        requestLibraryThemeData()
        requestLibrarySentenceData()
        requestLibraryCuratorData()

        //sticky header
        main_scroll_view.run {
            header = titleLayout
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }

        img_library_setting.setOnClickListener{
            activity?.let {
                val intent = Intent(context, SettingActivity::class.java)
                startActivity(intent)
            }
        }


        val libraryTabLayout = view.findViewById(R.id.titleLayout) as TabLayout
        val libraryViewPager = view.findViewById(R.id.vp_library) as ViewPager
        val adapter = LibraryTabAdapter(childFragmentManager)

        val viewFirst : View = getLayoutInflater().inflate(R.layout.custom_library_tab_basic, null)
        val viewSecond : View = getLayoutInflater().inflate(R.layout.custom_library_tab_basic, null)
        val viewThird : View = getLayoutInflater().inflate(R.layout.custom_library_tab_basic, null)
        txtUpper1 = viewFirst.findViewById(R.id.library_tab_num)
        val txtDown1 : TextView = viewFirst.findViewById(R.id.library_tab)
        txtUpper2 = viewSecond.findViewById(R.id.library_tab_num)
        val txtDown2 : TextView = viewSecond.findViewById(R.id.library_tab)
        txtUpper3 = viewThird.findViewById(R.id.library_tab_num)
        val txtDown3 : TextView = viewThird.findViewById(R.id.library_tab)

        //저장된 테마/문장 + 만든 테마/쓴 문장, 큐레이터 수 배열의 길이를 구하면 될 것 같다.


//        var themeNumSave : Int = data_theme_save_num.size
//        var themeNumWrite : Int = data_theme_write_num.size

//        var themeNum : Int = themeNumSave + themeNumWrite

//        Log.d("태그", "내용 : "+themeNumWrite)

//        var sentenceNum : Int = data_the_write_sentence.size
//        var curatorNum : Int = data_the_write_curator.size

//        txtUpper1.setText(themeNum.toString())
        txtDown1.setText("테마")

//        txtUpper2.setText(sentenceNum.toString())
        txtDown2.setText("문장")

//        txtUpper3.setText(curatorNum.toString())
        txtDown3.setText("큐레이터")

        libraryViewPager.setAdapter(adapter)
        libraryTabLayout.setupWithViewPager(libraryViewPager)

        libraryTabLayout.getTabAt(0)!!.customView = viewFirst
        libraryTabLayout.getTabAt(1)!!.customView = viewSecond
        libraryTabLayout.getTabAt(2)!!.customView = viewThird

        /*libraryTabLayout.getTabAt(0)!!.setText("테마")
        libraryTabLayout.getTabAt(1)!!.setText("문장")
        libraryTabLayout.getTabAt(2)!!.setText("큐레이터")
*/


    }



    private fun requestLibraryThemeData() {
        requestToServer.service.lookLibraryThema(
            token = SharedPreferenceController.getAccessToken(requireView().context)
        )
            .enqueue(
                object : Callback<ResponseLibraryThemeData> {
                    override fun onFailure(call: Call<ResponseLibraryThemeData>, t: Throwable) {
                        Log.d("통신실패", "${t}")
                    }

                    override fun onResponse(
                        call: Call<ResponseLibraryThemeData>,
                        response: Response<ResponseLibraryThemeData>
                    ) {
                        if(response.isSuccessful) {
                            Log.d("내서재 테마 조회", "${response.body()}")
                            data_theme_num = response.body()!!.data!!.save.size + response.body()!!.data!!.write.size
                            txtUpper1.setText(data_theme_num.toString())
                        }
                    }
                }
            )
    }

//    private fun requestLibraryThemeClickData() {
//        requestToServer.service.lookLibraryThema(
//            token = SharedPreferenceController.getAccessToken(requireView().context)
//        )
//            .enqueue(
//                object : Callback<ResponseLibraryThemeData> {
//                    override fun onFailure(call: Call<ResponseLibraryThemeData>, t: Throwable) {
//                        Log.d("통신실패", "${t}")
//                    }
//
//                    override fun onResponse(
//                        call: Call<ResponseLibraryThemeData>,
//                        response: Response<ResponseLibraryThemeData>
//                    ) {
//                        if(response.isSuccessful) {
//                            Log.d("내서재 테마 저장 조회", "${response.body()}")
//                            data_theme_write_num = response.body()!!.data!!.write.size + data_theme_save_num
//                            txtUpper1.setText(data_theme_write_num.toString())
//                        }
//                    }
//                }
//            )
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
                            Log.d("내 서재 문장 조회", "${response.body()}")
                            data_sentence_num = response.body()!!.data!!.save.size + response.body()!!.data!!.write.size
                            txtUpper2.setText(data_sentence_num.toString())


                        }

                    }
                }
            )
    }

//    private fun requestLibrarySentenceClickData() {
//        requestToServer.service.lookLibrarySentence(
//            token = SharedPreferenceController.getAccessToken(requireView().context)
//        )
//            .enqueue(
//                object : Callback<ResponseLibrarySentenceData> {
//                    override fun onFailure(call: Call<ResponseLibrarySentenceData>, t: Throwable) {
//                        Log.d("통신실패", "${t}")
//                    }
//
//                    override fun onResponse(
//                        call: Call<ResponseLibrarySentenceData>,
//                        response: Response<ResponseLibrarySentenceData>
//                    ) {
//                        if (response.isSuccessful) {
//                            Log.d("내 서재 문장 클릭 조회", "${response.body()}")
//                            data_sentence_write_num = response.body()!!.data!!.write.size + data_sentence_save_num
//                            txtUpper2.setText(data_sentence_write_num.toString())
//
//                        }
//
//                    }
//                }
//            )
//    }

    private fun requestLibraryCuratorData() {
        requestToServer.service.lookLibraryCurator(
            token = SharedPreferenceController.getAccessToken(requireView().context)
        )
            .enqueue(
                object : Callback<ResponseLibraryCuratorData> {
                    override fun onFailure(call: Call<ResponseLibraryCuratorData>, t: Throwable) {
                        Log.d("통신실패", "${t}")
                    }

                    override fun onResponse(
                        call: Call<ResponseLibraryCuratorData>,
                        response: Response<ResponseLibraryCuratorData>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("내 서재 큐레이터 숫자", "${response.body()}")
                            data_curator_num = response.body()!!.data.size
                            txtUpper3.setText(data_curator_num.toString())


                        }

                    }
                }
            )
    }


}
