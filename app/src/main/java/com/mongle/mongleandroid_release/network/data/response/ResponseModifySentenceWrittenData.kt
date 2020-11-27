package com.mongle.mongleandroid_release.network.data.response

data class ResponseModifySentenceWrittenData (
    var status : Int,
    var success : Boolean,
    var message : String,
    var data : SomeSentence?
)

data class SomeSentence(
    var sentence : String
)
