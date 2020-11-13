package com.example.mongleandroid_release.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.VISIBLE
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import kotlinx.android.synthetic.main.activity_privacy_terms.*
import kotlinx.android.synthetic.main.activity_service_terms.*


class PrivacyTermsActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_terms)

        activity_privacy_terms_btn_back.setOnClickListener {
            finish()
        }

        activity_privacy_terms_sv.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            change_visible(activity_privacy_terms_top_blur)
            change_visible(activity_privacy_terms_bottom_blur)
            if(scrollY == 0) {
                change_gone(activity_privacy_terms_top_blur)
                change_gone(activity_privacy_terms_bottom_blur)
            }
        }

    }
}
