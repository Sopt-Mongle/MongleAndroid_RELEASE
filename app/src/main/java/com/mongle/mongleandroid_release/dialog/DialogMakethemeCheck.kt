package com.mongle.mongleandroid_release.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.activity.MainActivity
import com.mongle.mongleandroid_release.activity.ThemeWritingSentenceBookActivity
import com.mongle.mongleandroid_release.activity.WritingThemeActivity
import com.mongle.mongleandroid_release.network.RequestToServer
import com.mongle.mongleandroid_release.network.SharedPreferenceController
import com.mongle.mongleandroid_release.network.data.request.RequestWritingThemeData
import com.mongle.mongleandroid_release.network.data.response.ResponseWritingThemeData
import kotlinx.android.synthetic.main.writing_theme_finish.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DialogMakethemeCheck(context : Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private val act : WritingThemeActivity = context as WritingThemeActivity

    private var requestWritingThemeData =  RequestWritingThemeData("", -1)
    private lateinit var maketheme_popup_yes : TextView
    private lateinit var maketheme_popup_no : TextView
    private lateinit var listener : MyDialogOKClickedListener



    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.setContentView(R.layout.dialog_writing_theme_check)     //다이얼로그에 사용할 xml 파일을 불러옴

        Glide.with(dlg.context).load(act.writingThemeImg).into(dlg.findViewById<ImageView>(R.id.dialog_writing_theme_check_img_title))
        dlg.findViewById<TextView>(R.id.dialog_writing_theme_check_tv_title).text = act.writingThemeData.theme


            dlg.show()


    }
    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object:
            MyDialogOKClickedListener {

            override fun onOKClicked(content: String) {
                listener(content)
            }
        }

        requestWritingThemeData = act.writingThemeData
        maketheme_popup_yes = dlg.findViewById(R.id.dialog_writing_theme_check_yes)
        maketheme_popup_yes.setOnClickListener{
            Log.d("requestWritingThemeData", "${requestWritingThemeData.themeImgIdx} && ${requestWritingThemeData.theme}")

            themePost(requestWritingThemeData)
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
                val intent = Intent(it.context , ThemeWritingSentenceBookActivity::class.java)
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


    private fun themePost(body : RequestWritingThemeData){
        val call: Call<ResponseWritingThemeData> = RequestToServer.service.RequestWritingTheme(token = this.let{
            SharedPreferenceController.getAccessToken(context = this.act)}, body = body)
        call.enqueue(object : Callback<ResponseWritingThemeData> {
            @SuppressLint("LongLogTag")
            override fun onFailure(call: Call<ResponseWritingThemeData>, t: Throwable) {
                Log.e("ResponseWritingThemeData 통신실패",t.toString())
            }
            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<ResponseWritingThemeData>,
                response: Response<ResponseWritingThemeData>
            ) {
                if (response.isSuccessful){
                    response.body().let { body ->
                        Log.e("ResponseWritingThemeData 통신응답바디", "status: ${body!!.status} message : ${body.message} ")
                    }
                }

            }

        })

    }

}
