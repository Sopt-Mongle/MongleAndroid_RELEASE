package com.example.mongleandroid_release.network.data.response

data class ResponseCuratorInformationData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : CuratorInformation?
)

data class CuratorInformation(
    val profile : List<CuratorProfile>,
    val theme : List<CuratorTheme>,
    val sentence : List<CuratorSentence>
)

data class CuratorProfile(
    val curatorIdx : Int,
    val name : String,
    val img : String?,
    val introduce : String,
    val keyword : String,
    val keywordIdx : Int,
    val subscribe : Int,
    val alreadySubscribed : Boolean
)

data class CuratorTheme(
    val themeIdx : Int,
    val theme : String,
    val themeImage : String,
    val themeImgIdx : Int,
    val saves : Int,
    val writer : String,
    val writerImg : String,
    val alreadyBookmarked : Boolean,
    val sentenceNum : Int
)

data class CuratorSentence(
    val sentenceIdx : Int,
    val sentence : String,
    val themeIdx : Int,
    val theme : String,
    val likes : Int,
    val saves : Int,
    val writer : String,
    val writerImg : String,
    val title : String,
    val author : String,
    val publisher : String,
    val thumbnail : String?,
    val alreadyLiked : Boolean,
    val alreadyBookmarked : Boolean
)