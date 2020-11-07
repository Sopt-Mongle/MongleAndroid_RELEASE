package com.example.mongleandroid_release.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.VISIBLE
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_privacy_terms.*


class PrivacyTermsActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_terms)

        activity_privacy_terms_btn_back.setOnClickListener {
            finish()
        }

        activity_privacy_terms_sv.viewTreeObserver.addOnScrollChangedListener {
            activity_privacy_terms_top_blur.visibility = VISIBLE
            activity_privacy_terms_bottom_blur.visibility = VISIBLE
        }

    }
}
