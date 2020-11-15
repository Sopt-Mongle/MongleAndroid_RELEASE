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

class DialogChangePassword(context : Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var dialog_change_password : TextView

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.setContentView(R.layout.dialog_change_password)     //다이얼로그에 사용할 xml 파일을 불러옴

        dialog_change_password = dlg.findViewById(R.id.tv_change_password_relogin_button)
        dialog_change_password.setOnClickListener {

            dlg.dismiss()
        }

        dlg.show()
    }


}