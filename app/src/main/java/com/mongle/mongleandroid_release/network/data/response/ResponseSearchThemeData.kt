package com.mongle.mongleandroid_release.network.data.response

data class ResponseSearchThemeData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MutableList<SearchTheme>
)
// themeSearch에 3가지 사용 :: themeIdx, theme, themeImg
data class SearchTheme(
    val themeIdx : Int, // 테마 고유 idx
    val theme : String, // 테마 이름
    val themeImg : String, // img url
    val themeImgIdx : Int, // 이미지 인덱스
    val saves : Int, // 테마 북마크 수
    val sentenceNum	: Int, // 테마 안에 속한 문장 개수

    // single selection impl :: #90 구현을 위해 추가함
    var themeChked: Boolean = false // 테마 체크 표현
)
