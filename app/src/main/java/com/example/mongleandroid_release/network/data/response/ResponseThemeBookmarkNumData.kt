package com.example.mongleandroid_release.network.data.response

data class ResponseThemeBookmarkNumData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ThemeBookmark?
)
data class ThemeBookmark(
    val isSave : Boolean,
    val saves : Int
)