package com.mongle.mongleandroid_release.network.data.response

data class ResponseSearchRecommendData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<Any>
)