package com.example.mongleandroid_release

import android.animation.ObjectAnimator
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_join_step2.*
import java.util.logging.Handler

// 프로그래스바 애니메이션 확장함수
fun android.os.Handler.forProgressBar(ProgressBar: ProgressBar){
    android.os.Handler().postDelayed({
        val progressAnimator = ObjectAnimator.ofInt(ProgressBar, "progress", 0, 50)
        progressAnimator.setDuration(500)
        progressAnimator.start()
    }, 200)
}

fun EditText.forProgressOn(outView: View, inView: View){
    if(this.text.isNotEmpty()){
        outView.setBackgroundResource(R.drawable.dot_circle_progresson_out)
        inView.setBackgroundResource(R.drawable.dot_circle_progresson_in)
    }else{
        outView.setBackgroundResource(R.drawable.dot_circle_progressoff_out)
        inView.setBackgroundResource(R.drawable.dot_circle_progressoff_in)
    }
}

// 액티비티 전환 시 안드로이드 자체에서 뷰 전환 애니메이션 발생하므로,
// ProgressBarAnim가 우리가 원하는 대로 동작하려 대한 추가적인 처리가 필요합니다.
// 슬랙 채널에 "액티비티 전환 시 안드로이드 자체에서 뷰 전환 애니메이션 발생" 로 검색하시면 참고 자료가 있습니다.