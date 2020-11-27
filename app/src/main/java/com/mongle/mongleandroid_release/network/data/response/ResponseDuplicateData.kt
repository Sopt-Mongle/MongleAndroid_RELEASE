package com.mongle.mongleandroid_release.network.data.response

data class ResponseDuplicateData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : Duplicate
)

data class Duplicate(
    val duplicate : String
)