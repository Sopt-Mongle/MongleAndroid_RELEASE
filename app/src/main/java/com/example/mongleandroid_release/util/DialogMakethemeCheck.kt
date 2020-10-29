package com.example.mongleandroid_release.util

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.MainActivity
import com.example.mongleandroid_release.activity.WritingThemeActivity
import com.example.mongleandroid_release.showKeyboard
import com.example.mongleandroid_release.unshowKeyboard
import kotlinx.android.synthetic.main.writing_theme_finish.*
import kotlinx.android.synthetic.main.writing_theme_step1.*


class DialogMakethemeCheck(context : Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private val act : WritingThemeActivity = context as WritingThemeActivity
    private lateinit var maketheme_popup_title : TextView
    private lateinit var maketheme_popup_yes : TextView
    private lateinit var maketheme_popup_no : TextView
    private lateinit var listener : MyDialogOKClickedListener



    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.setContentView(R.layout.dialog_writing_theme_check)     //다이얼로그에 사용할 xml 파일을 불러옴

        dlg.show()


    }
    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object:
            MyDialogOKClickedListener {

            override fun onOKClicked(content: String) {
                listener(content)
            }
        }

        maketheme_popup_yes = dlg.findViewById(R.id.dialog_writing_theme_check_yes)
        maketheme_popup_yes.setOnClickListener{
            act.setContentView(R.layout.writing_theme_finish)
            dlg.dismiss()
            // 메인으로 돌아가기
            act.writing_theme_finish_btn_main.setOnClickListener {
                val intent = Intent(it.context , MainActivity::class.java)
                it.context.startActivity(intent)
                act.finish()
            }
            // 이 테마에 문장 쓰기
            act.writing_theme_finish_btn_write.setOnClickListener {
                //나중에 themeWriting 만들어지면 수정해야함 !
                val intent = Intent(it.context , MainActivity::class.java)
                it.context.startActivity(intent)
                act.finish()
            }
        }

        maketheme_popup_no = dlg.findViewById(R.id.dialog_writing_theme_check_no)
        maketheme_popup_no.setOnClickListener {
            dlg.dismiss()
        }

    }


    interface MyDialogOKClickedListener {
        fun onOKClicked(content : String)
    }

}
