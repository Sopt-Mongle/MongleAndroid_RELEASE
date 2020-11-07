package com.example.mongleandroid_release.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.network.data.request.RequestWritingSentenceData


class WritingSentenceActivity : AppCompatActivity() {
    companion object{
        var writingSentenceData: RequestWritingSentenceData = RequestWritingSentenceData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_sentence)

    }

}