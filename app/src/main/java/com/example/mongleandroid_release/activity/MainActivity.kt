package com.example.mongleandroid_release.activity

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.fragment.CuratorFragment
import com.example.mongleandroid_release.fragment.LibraryFragment
import com.example.mongleandroid_release.fragment.MainFragment
import com.example.mongleandroid_release.fragment.SearchFragment
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.customEnqueue
import com.example.mongleandroid_release.network.data.request.RequestLoginData
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
        var search_result = ""
        var book_result = ""
    }

    // floating btn 관련
    private var isFabOpen = false
    private lateinit var fab_open: Animation
    private lateinit var fab_close: Animation


    //fragment 처리 객체
    lateinit var mainFragment: MainFragment
    lateinit var searchFragment: SearchFragment
    lateinit var curatorFragment: CuratorFragment
    lateinit var libraryFragment: LibraryFragment

    private val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_activity_FAB_st.visibility = View.GONE
        main_activity_FAB_tm.visibility = View.GONE
        fab_open = AnimationUtils.loadAnimation(this,
            R.anim.fab_open
        );
        fab_close = AnimationUtils.loadAnimation(this,
            R.anim.fab_close
        );

        main_activity_bnv.bringToFront()
        main_activity_FAB_main.bringToFront()


        main_activity_FAB_main.setOnClickListener {
            toggleFab()
        }


        //fragment 객체 초기화
        mainFragment = MainFragment()
        searchFragment = SearchFragment()
        curatorFragment = CuratorFragment()
        libraryFragment = LibraryFragment()

        supportFragmentManager.beginTransaction().replace(R.id.main_activity_fg, mainFragment).commit()

        main_activity_bnv.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.menu_main -> supportFragmentManager.beginTransaction().replace(R.id.main_activity_fg, mainFragment).commit()
                R.id.menu_search -> supportFragmentManager.beginTransaction().replace(R.id.main_activity_fg, searchFragment).commit()
                R.id.menu_curator -> supportFragmentManager.beginTransaction().replace(R.id.main_activity_fg, curatorFragment).commit()
                R.id.menu_my_page -> supportFragmentManager.beginTransaction().replace(R.id.main_activity_fg, libraryFragment).commit()
            }
            true
        }

        val now = System.currentTimeMillis()
        val mDate = Date(now)
        val simpleData = SimpleDateFormat("hhmmss")
        val nowTime = simpleData.format(mDate).toInt()

        Log.d("시간테스트 현재시간", nowTime.toString())

        // 자동로그인 && 토큰갱신
        // 비회원 / 회원 구분
        if(SharedPreferenceController.getMail(this)!!.isBlank() ||
                SharedPreferenceController.getPasswd(this)!!.isBlank())
        {
            // 아이디, 비밀번호가 저장되어있지 않는 경우 = 비회원
            SharedPreferenceController.setAccessToken(this, "guest")
        } else {
            // 아이디, 비밀번호가 저장되어 있는 경우 = 자동로그인
            // 현재시간 - pref 시간 = 3시간이상 이면 토큰 갱신
            if(SharedPreferenceController.getCurrentTime(this) == "") {
                // 로그인
                login()
                SharedPreferenceController.setCurrentTime(this, nowTime.toString())
            } else {
                val lastTime = SharedPreferenceController.getCurrentTime(this)!!.toInt()
                if(nowTime - lastTime > 30000) {
                    // 갱신
                    SharedPreferenceController.setCurrentTime(this, nowTime.toString())
                    login()
                }
            }


        }

    }

    private fun login() {
        requestToServer.service.requestLogin(
            RequestLoginData(
                email = SharedPreferenceController.getMail(this).toString(),
                password = SharedPreferenceController.getPasswd(this).toString()
            )
        ).customEnqueue(
            onError = {
                Log.d("error", "에러")
            },
            onSuccess = {
                if(it.status == 200) {
                    SharedPreferenceController.setAccessToken(this, it.data.accessToken)
                }
            }
        )
    }

    private fun toggleFab() {
        if (isFabOpen) {
            main_activity_FAB_tm.startAnimation(fab_close)
            main_activity_FAB_st.startAnimation(fab_close)
            ObjectAnimator.ofFloat(main_activity_FAB_tm, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(main_activity_FAB_st, "translationY", 0f).apply { start() }
            main_activity_FAB_main.setImageResource(R.drawable.ic_add)

            main_activity_blur.visibility = View.GONE


        } else {
            main_activity_FAB_tm.startAnimation(fab_open)
            main_activity_FAB_st.startAnimation(fab_open)
            main_activity_FAB_st.visibility = View.VISIBLE
            main_activity_FAB_tm.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(main_activity_FAB_tm, "translationY", -250f).apply { start() }
            ObjectAnimator.ofFloat(main_activity_FAB_st, "translationY", -400f).apply { start() }
            main_activity_FAB_main.setImageResource(R.drawable.ic_close)

            main_activity_blur.visibility = View.VISIBLE

            main_activity_FAB_st.setOnClickListener {
                val intent = Intent(this@MainActivity,WritingSentenceActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fab_fade_in, R.anim.fab_fade_out)
            }

            main_activity_FAB_tm.setOnClickListener {
                val intent = Intent(this@MainActivity,WritingThemeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fab_fade_in, R.anim.fab_fade_out)
            }

        }

        isFabOpen = !isFabOpen
    }


}