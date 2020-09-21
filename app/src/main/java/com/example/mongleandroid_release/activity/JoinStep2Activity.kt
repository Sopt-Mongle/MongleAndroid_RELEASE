package com.example.mongleandroid_release.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_join_step2.*

class JoinStep2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_step2)

        Handler().postDelayed({
            val progressAnimator = ObjectAnimator.ofInt(activity_join_step2_pgb, "progress", 0, 50)
            progressAnimator.setDuration(500)
            progressAnimator.start()
        }, 200)
    }


}