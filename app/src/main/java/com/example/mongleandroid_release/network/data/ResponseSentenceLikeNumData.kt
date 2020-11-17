package com.example.mongleandroid_release.network.data

data class ResponseSentenceLikeNumData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : SentenceLike?
)
data class SentenceLike(
    val isLike : Boolean,
    val likes : Int
)