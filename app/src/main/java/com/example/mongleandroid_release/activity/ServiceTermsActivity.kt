package com.example.mongleandroid_release.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import kotlinx.android.synthetic.main.activity_service_terms.*

class ServiceTermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_terms)

        activity_service_terms_btn_back.setOnClickListener {
            finish()
        }

        activity_service_terms_sv.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            change_visible(activity_service_terms_top_blur)
            if(scrollY == 0) {
                change_gone(activity_service_terms_top_blur)
            }
        }
    }
}