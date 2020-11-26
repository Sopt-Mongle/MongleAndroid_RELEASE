package com.example.mongleandroid_release.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.JoinStep1Activity
import com.example.mongleandroid_release.activity.LoginActivity

class DialogQuitService(context : Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var dialog_quit_yes : TextView
    private lateinit var dialog_quit_no : TextView
    private lateinit var listener : MyDialogOKClickedListener



    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.setContentView(R.layout.dialog_quit)     //다이얼로그에 사용할 xml 파일을 불러옴

        dialog_quit_yes = dlg.findViewById(R.id.tv_quit_yes)
        dialog_quit_yes.setOnClickListener {
            listener.onOKClicked("탈퇴하기")
            dlg.dismiss()
        }

        dialog_quit_no = dlg.findViewById(R.id.tv_quit_no)
        dialog_quit_no.setOnClickListener {

            dlg.dismiss()
        }
        dlg.show()
    }

    fun setOnClickListener(listener: (String) -> Unit) {
        this.listener = object:
            MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }


    interface MyDialogOKClickedListener {
        fun onOKClicked(content : String)
    }


}