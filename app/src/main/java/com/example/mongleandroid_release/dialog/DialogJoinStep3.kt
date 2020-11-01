package com.example.mongleandroid_release.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.example.mongleandroid_release.R

class DialogJoinStep3(context : Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var dialog_join_step3_yes : TextView
    private lateinit var dialog_join_step3_no : TextView

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.setContentView(R.layout.dialog_join_step3)     //다이얼로그에 사용할 xml 파일을 불러옴

        dialog_join_step3_yes = dlg.findViewById(R.id.dialog_join_step3_yes)
        dialog_join_step3_yes.setOnClickListener {
            dlg.dismiss()
        }

        dialog_join_step3_no = dlg.findViewById(R.id.dialog_join_step3_no)
        dialog_join_step3_no.setOnClickListener {
            // 재전송 하는 코드
            dlg.dismiss()
        }

        dlg.show()
    }

}
