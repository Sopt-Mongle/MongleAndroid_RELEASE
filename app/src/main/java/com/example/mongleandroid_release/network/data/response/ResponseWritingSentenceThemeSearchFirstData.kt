package com.example.mongleandroid_release.network.data.response

data class ResponseWritingSentenceThemeSearchFirstData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MutableList<FirstThemeData>
)

// themeSearchFirst에 3가지 사용
data class FirstThemeData(
    val themeIdx : Int,
    val theme : String,
    val themeImg : String,
    val themeImgIdx : Int, // 필요없음
    val saves : Int, // 필요없음
    val writer: String, // 필요없음
    val writerImg : String, // 필요없음
    val alreadyBookmarked : Boolean, // 필요없음

    // 추가함
    var themeChked: Boolean = false // 테마 체크 표현
)
