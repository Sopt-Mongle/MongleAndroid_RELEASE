package com.mongle.mongleandroid_release.fragment

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
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.SettingActivity
import com.mongle.mongleandroid_release.adapter.*
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.data.response.*
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false)

    }


    override fun onResume() {
        super.onResume()
        requestMyProfile()
        requestLibraryCuratorData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestMyProfile()
        requestLibraryCuratorData()

        //sticky header
        main_scroll_view.run {
            header = titleLayout
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
                activity?.runOnUiThread {
                    tv_library_top_name.text = tx_library_username.text
                }
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
                activity?.runOnUiThread {
                    tv_library_top_name.text = ""
                }
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

        txtDown1.setText("테마")
        txtDown2.setText("문장")
        txtDown3.setText("큐레이터")

        libraryViewPager.setAdapter(adapter)
        libraryTabLayout.setupWithViewPager(libraryViewPager)

        libraryTabLayout.getTabAt(0)!!.customView = viewFirst
        libraryTabLayout.getTabAt(1)!!.customView = viewSecond
        libraryTabLayout.getTabAt(2)!!.customView = viewThird


    }


    fun requestMyProfile(){
        requestToServer.service.lookLibraryProfile(
            token = SharedPreferenceController.getAccessToken(requireView().context)
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
                            if(response.body()!!.data[0].img == null) {
                                Glide.with(view!!.context).load(R.drawable.my_settings_profile_img_profile).into(img_library_profile)
                            } else {
                                Glide.with(view!!.context).load(response.body()!!.data[0].img).into(img_library_profile)
                            }
                            tx_library_username.text = response.body()!!.data[0].name
                            tx_library_contents.text = response.body()!!.data[0].keyword
                            tx_library_keyword.text = response.body()!!.data[0].introduce

                            txtUpper1.text = response.body()!!.data[0].themeCount.toString()
                            txtUpper2.text = response.body()!!.data[0].sentenceCount.toString()


                        }
                    }
                }
            )
    }


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
                            if(response.body()!!.data.isNullOrEmpty()){
                                Log.d("내 서재 큐레이터 조회 null", "${response.body()}")

                            }
                            else {
                                data_curator_num = response.body()!!.data.size
                                txtUpper3.setText(data_curator_num.toString())

                            }
                        }

                    }
                }
            )
    }


}
