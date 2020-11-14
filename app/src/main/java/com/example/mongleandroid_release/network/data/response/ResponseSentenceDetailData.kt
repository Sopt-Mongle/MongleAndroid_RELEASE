package com.example.mongleandroid_release.network.data.response

data class ResponseSentenceDetailData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ArrayList<SentenceDetailData>
)

data class SentenceDetailData(
    val sentenceIdx : Int,
    val sentence : String,
    val themeIdx : Int,
    val theme : String,
    val likes : Int,
    val saves : Int,
    val writer : String,
    val writerImg : String,
    val title : String,
    val author : String,
    val publisher : String,
    val thumbnail : String,
    val timestamp : String,
    val alreadyLiked : Boolean,
    val alreadyBookmarked : Boolean
)