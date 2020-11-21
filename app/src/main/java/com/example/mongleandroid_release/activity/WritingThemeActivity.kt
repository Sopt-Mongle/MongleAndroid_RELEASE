package com.example.mongleandroid_release.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.adapter.WritingThemeImgAdapter
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.request.RequestWritingThemeData
import com.example.mongleandroid_release.network.data.response.ResponseWritingThemeImgData
import com.example.mongleandroid_release.network.data.response.ThemeImg
import com.example.mongleandroid_release.util.DialogMakethemeCheck
import kotlinx.android.synthetic.main.item_writing_theme_img.*
import kotlinx.android.synthetic.main.writing_theme_step1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WritingThemeActivity : AppCompatActivity() {

    lateinit var writingThemeImgAdapter: WritingThemeImgAdapter
    var writingThemeData =  RequestWritingThemeData("", -1)
    var writingThemeImg =  ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.writing_theme_step1)

        // MARK :: PROPERTIES

        // MARK :: XML
        // 뒤로가기 버튼
        writing_theme_step1_btn_out.setOnClickListener{
            Toast.makeText(this, "메인화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show()
            finish() //onDestroy() 호출되어 아예 메모리에 액티비티 삭제!
        }
        // 테마 이름 입력창
        // 텍스트 변화 감지를 위해서 addTextChangedListener에 TextWatcher 객체 생성해서 넣어줌
        writing_theme_step1_et_sentence.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                writingThemeData.theme = writing_theme_step1_et_sentence.text.toString()
                writing_theme_step1_et_sentence
                    .setBackgroundResource(R.drawable.et_state_focused)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // writing_theme_step1_et_sentence 빨간 박스 해제
                // 테마 이름 글자 수 카운팅
                writing_theme_step1_tv_cnt.text = writing_theme_step1_et_sentence.text.toString().length.toString()
                writing_theme_step1_ll_warning.visibility = View.GONE

            }

        })


        // 테마 사진 리스트
//        //rv 동작
        writingThemeImgAdapter = WritingThemeImgAdapter(this)
        writing_theme_rv.adapter = writingThemeImgAdapter
        themeImgSearch()

        // 테마 등록 버튼
        writing_theme_step1_next.setOnClickListener {
            // 빈칸 경고
            if(writing_theme_step1_et_sentence.text.isEmpty()) {
                writing_theme_step1_ll_warning.visibility = View.VISIBLE

                writing_theme_step1_et_sentence
                    .setBackgroundResource(R.drawable.et_area_red)
            }else if(writingThemeData.themeImgIdx == -1){
                writing_theme_step1_ll_warning2.visibility = View.VISIBLE
            }else { //빈칸 없으면 다음으로
                if(writingThemeData.theme.isEmpty() || writingThemeData.themeImgIdx <=0 ){

                }else{
                    Toast.makeText(this, "등록 팝업 등장.", Toast.LENGTH_SHORT).show()

                    val dlg = DialogMakethemeCheck(this)
                    dlg.start()
                    dlg.setOnOKClickedListener{
                    }
                }

            }
        }






        // MARK :: METHOD


    }
    private fun themeImgSearch(){
        val call: Call<ResponseWritingThemeImgData> = RequestToServer.service.RequestWritingThemeImg(token = this.let{
            SharedPreferenceController.getAccessToken(this)
        })

        call.enqueue(object : Callback<ResponseWritingThemeImgData>{
            @SuppressLint("LongLogTag")
            override fun onFailure(call: Call<ResponseWritingThemeImgData>, t: Throwable) {
                Log.e("ResponseWritingThemeImgData 통신실패", t.toString())
            }

            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<ResponseWritingThemeImgData>,
                response: Response<ResponseWritingThemeImgData>
            ) {
                if (response.isSuccessful){
                    response.body().let {body ->
                        Log.e(
                            "ResponseWritingThemeImgData 통신응답바디",
                            "status: ${body!!.status} data : ${body.message}"
                        )

                        if (body.data.isNullOrEmpty()){
                            // 에러 처리 뷰 없음
                        } else{
                            writingThemeImgAdapter.datas = body.data
                            writingThemeImgAdapter.notifyDataSetChanged()

                            //리사이클러뷰 아이템 클릭리스너 등록
                            writingThemeImgAdapter.setItemClickListener(object :
                                WritingThemeImgAdapter.ItemClickListener {
                                override fun onClick(
                                    view: View,
                                    position: Int,
                                    data: ThemeImg,
                                    datas: MutableList<ThemeImg>
                                ) {
                                    Log.d("SSS", "${position}번 리스트 선택")

                                    writing_theme_step1_et_sentence.clearFocus()
                                    writingThemeData.themeImgIdx = Integer.parseInt(item_writing_theme_tv_imgIdx.text.toString())
                                    writing_theme_step1_ll_warning2.visibility = View.GONE

                                    writingThemeImg = data.img



                                    // single selection impl
                                    for(data in datas){
                                        data.imgChked = false
                                    }
                                    datas[position].imgChked = true
                                    writingThemeImgAdapter.datas = datas
                                    writingThemeImgAdapter.notifyDataSetChanged()

                                }
                            })


                        }
                    }
                } else {
                    //if 서버 통신 실패
                    Log.d("서버 통신", "서버 통신 실패")
                }
            }

        })
    }




}