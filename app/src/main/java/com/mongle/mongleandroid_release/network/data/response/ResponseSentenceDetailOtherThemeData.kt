package com.mongle.mongleandroid_release.network.data.response

data class ResponseSentenceDetailOtherThemeData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<OtherSentence>
)

data class OtherSentence(
    val sentenceIdx : Int,
    val sentence : String,
    val likes : Int,
    val saves : Int,
    val writer : String,
    val writerImg : String,
    val title : String,
    val author : String,
    val publisher : String,
    val timestamp : String
)