package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mongleandroid_release.dialog.DialogLogin
import com.example.mongleandroid_release.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        activity_login_tv_join.setOnClickListener {
            val intent = Intent(this, JoinStep1Activity::class.java)
            startActivity(intent)
        }

        activity_login_btn_login.setOnClickListener {
            val dlg = DialogLogin(this)
            dlg.setOnOKClickedListener{ content ->

            }
            dlg.start("")
        }

    }
}