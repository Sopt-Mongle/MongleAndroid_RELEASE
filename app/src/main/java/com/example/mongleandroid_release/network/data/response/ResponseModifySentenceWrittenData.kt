package com.example.mongleandroid_release.network.data.response

import androidx.test.runner.intent.IntentMonitor

data class ResponseModifySentenceWrittenData (
    var status : Int,
    var success : Boolean,
    var message : String,
    var data : SomeSentence?
)

data class SomeSentence(
    var sentence : String
)
