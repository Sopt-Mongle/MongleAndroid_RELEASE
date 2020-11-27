package com.mongle.mongleandroid_release.network.data.response

data class ResponseCodeData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : AuthCode
)

data class AuthCode(
    val authNum : String
)