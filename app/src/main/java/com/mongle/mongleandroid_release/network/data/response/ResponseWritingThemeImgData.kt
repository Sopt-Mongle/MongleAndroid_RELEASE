package com.mongle.mongleandroid_release.network.data.response

data class ResponseWritingThemeImgData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MutableList<ThemeImg>
)

data class ThemeImg(
    val themeImgIdx: Int, // 이미지 인덱스
    val img: String, // 이미지 url

    // single selection impl :: #90 구현을 위해 추가함
    var imgChked: Boolean = false // 테마 체크 표현

)