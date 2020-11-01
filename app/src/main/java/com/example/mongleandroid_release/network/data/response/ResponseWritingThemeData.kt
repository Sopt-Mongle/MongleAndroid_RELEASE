package com.example.mongleandroid_release.network.data.response

data class ResponseWritingThemeData (
    var staus : Int,
    var success : Boolean,
    var message : String
) {
    lateinit var status: String
}