package com.mongle.mongleandroid_release.activity

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.dialog.DialogGuest
import com.mongle.mongleandroid_release.fragment.CuratorFragment
import com.mongle.mongleandroid_release.fragment.LibraryFragment
import com.mongle.mongleandroid_release.fragment.MainFragment
import com.mongle.mongleandroid_release.fragment.SearchFragment
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_join_step3.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.onboarding_step2.*
import kotlinx.android.synthetic.main.onboarding_step4.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
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
        fab_open = AnimationUtils.loadAnimation(
            this,
            R.anim.fab_open
        );
        fab_close = AnimationUtils.loadAnimation(
            this,
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

        supportFragmentManager.beginTransaction().replace(R.id.main_activity_fg, mainFragment)
            .commit()

        main_activity_bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    if (isFabOpen) toggleFab()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_activity_fg, mainFragment).commit()
                }
                R.id.menu_search -> {
                    if (isFabOpen) toggleFab()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_activity_fg, searchFragment).commit()
                }
                R.id.menu_curator -> {
                    if (isFabOpen) toggleFab()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_activity_fg, curatorFragment).commit()
                }
                R.id.menu_my_page -> {
                    if (isFabOpen) toggleFab()
                    if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest") {

                        val dlg = DialogGuest(view.context)
                        dlg.start()

                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_activity_fg, libraryFragment).commit()
                    }
                }


            }
            true
        }


        // 둘러보기로 넘어왔을 때 mail이 저장되어있지 않는 경우 = 비회원
        if (SharedPreferenceController.getMail(this).isNullOrBlank()) {
            // guest로 넘겨준다.
            SharedPreferenceController.setAccessToken(this, "guest")
        }
    }

    fun goMain() {
        main_activity_bnv.menu.findItem(R.id.menu_main).isChecked = true
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fg, mainFragment).commit()
    }

    fun goSearch() {
        main_activity_bnv.menu.findItem(R.id.menu_search).isChecked = true
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fg, searchFragment).commit()
    }


    private fun toggleFab() {
        if (isFabOpen) {
            main_activity_FAB_tm.startAnimation(fab_close)
            main_activity_FAB_st.startAnimation(fab_close)

            main_activity_FAB_st.startAnimation(
                AnimationUtils.loadAnimation(
                    this@MainActivity,
                    R.anim.mongle_list_out
                )
            )
            main_activity_FAB_tm.startAnimation(
                AnimationUtils.loadAnimation(
                    this@MainActivity,
                    R.anim.mongle_list_out
                )
            )
            main_activity_FAB_st.visibility = View.INVISIBLE
            main_activity_FAB_tm.visibility = View.INVISIBLE

            ObjectAnimator.ofFloat(main_activity_FAB_main, "rotation", 45f, 0f).apply { start() }

            main_activity_blur.visibility = View.GONE


        } else {
            main_activity_FAB_tm.startAnimation(fab_open)
            main_activity_FAB_st.startAnimation(fab_open)

            main_activity_FAB_tm.visibility = View.VISIBLE

            // 블러 뷰에 대한 clickable 을 줘서, 뒤에 있는 프래그먼트에 대한 터치를 막음 !
            main_activity_blur.isClickable = true
            main_activity_FAB_tm.startAnimation(
                AnimationUtils.loadAnimation(
                    this@MainActivity,
                    R.anim.mongle_list_in
                )
            )
            main_activity_FAB_st.visibility = View.VISIBLE
            main_activity_FAB_st.startAnimation(
                AnimationUtils.loadAnimation(
                    this@MainActivity,
                    R.anim.mongle_list_in
                )
            )


            ObjectAnimator.ofFloat(main_activity_FAB_main, "rotation", 0f, 45f).apply { start() }

            main_activity_blur.visibility = View.VISIBLE

            main_activity_FAB_st.setOnClickListener {
                toggleFab()

                if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest") {

                    val dlg = DialogGuest(view.context)
                    dlg.start()

                } else {
                    val intent = Intent(this@MainActivity, WritingSentenceActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fab_fade_in, R.anim.fab_fade_out)
                }
            }

            main_activity_FAB_tm.setOnClickListener {
                toggleFab()

                if (applicationContext?.let { SharedPreferenceController.getAccessToken(it) } == "guest") {

                    val dlg = DialogGuest(view.context)
                    dlg.start()

                } else {
                    val intent = Intent(this@MainActivity, WritingThemeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fab_fade_in, R.anim.fab_fade_out)
                }
            }

        }

        isFabOpen = !isFabOpen
    }

}