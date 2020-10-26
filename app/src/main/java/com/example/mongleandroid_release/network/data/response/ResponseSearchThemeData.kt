package com.example.mongleandroid_release.network.data.response

data class ResponseSearchThemeData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MutableList<SearchTheme>
)

data class SearchTheme(
    val themeIdx : Int, // 테마 고유 idx
    val theme : String, // 테마 이름
    val saves : Int, // 테마 북마크 수
    val sentenceNum	: Int // 테마 안에 속한 문장 개수
)