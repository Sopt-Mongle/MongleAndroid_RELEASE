package com.mongle.mongleandroid_release.network.data.response

data class ResponseDeleteSentenceWritten(
    var status : Int,
    var success : Boolean,
    var message : String,
    var data : DeleteSentence?
)

data class DeleteSentence (
    var deleteSentenceIdx : String
)