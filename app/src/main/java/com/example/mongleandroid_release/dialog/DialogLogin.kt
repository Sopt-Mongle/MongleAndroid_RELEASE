package com.example.mongleandroid_release.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.activity.JoinStep1Activity

class DialogLogin(context : Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var dialog_login_yes : TextView
    private lateinit var dialog_login_no : TextView

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.setContentView(R.layout.dialog_login)     //다이얼로그에 사용할 xml 파일을 불러옴

        dialog_login_yes = dlg.findViewById(R.id.dialog_login_yes)
        dialog_login_yes.setOnClickListener {
            dlg.dismiss()
        }

        dialog_login_no = dlg.findViewById(R.id.dialog_login_no)
        dialog_login_no.setOnClickListener {
            val intent = Intent(it.context, JoinStep1Activity::class.java)
            it.context.startActivity(intent)
            dlg.dismiss()
        }

        dlg.show()
    }


}
