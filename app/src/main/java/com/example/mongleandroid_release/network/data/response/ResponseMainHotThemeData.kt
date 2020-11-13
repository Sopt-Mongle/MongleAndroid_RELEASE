package com.example.mongleandroid_release.network.data.response

data class ResponseMainHotThemeData(
    val status: Int,
    val success : Boolean,
    val message : String,
    val data: List<MainThemes>
)
data class MainThemes(
    val themeIdx : Int,
    val theme : String,
    val themeImg : String,
    val themeImgIdx : Int,
    val saves : Int,
    val alreadyBookmarked : Boolean,
    val sentenceNum : Int
)