package com.mongle.mongleandroid_release.network.data.response

data class ResponseSentenceBookmarkNumData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : SentenceBookmark?
)
data class SentenceBookmark(
    val isSave : Boolean,
    val saves : Int
)