package com.example.mongleandroid_release

import android.animation.ObjectAnimator
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_join_step2.*
import java.util.logging.Handler

// 프로그래스바 애니메이션 확장함수
fun android.os.Handler.ProgressBarAnim(ProgressBar: ProgressBar){
    android.os.Handler().postDelayed({
        val progressAnimator = ObjectAnimator.ofInt(ProgressBar, "progress", 0, 50)
        progressAnimator.setDuration(500)
        progressAnimator.start()
    }, 200)
}

// 관련해서 이것도 해당 액티비티에 넣어주어야 합니다.
//override fun onPause() {
//    super.onPause()
//    // 화면 전환 시 애니메이션 없애는 코드
//    overridePendingTransition(0, 0)
//}