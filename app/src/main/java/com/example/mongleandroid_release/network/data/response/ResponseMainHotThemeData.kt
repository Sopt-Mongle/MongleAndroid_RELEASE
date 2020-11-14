package com.example.mongleandroid_release.network.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ResponseMainHotThemeData(
    val status: Int,
    val success : Boolean,
    val message : String,
    val data: List<MainThemes>
)
@Parcelize
data class MainThemes(
    val themeIdx : Int ,
    val theme : String ,
    val themeImg : String ,
    val themeImgIdx : Int,
    val saves : Int ,
    val alreadyBookmarked : Boolean,
    val sentenceNum : Int
) : Parcelable { }