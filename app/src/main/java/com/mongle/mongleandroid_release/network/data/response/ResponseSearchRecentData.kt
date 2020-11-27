package com.mongle.mongleandroid_release.network.data.response

data class ResponseSearchRecentData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<String>
)