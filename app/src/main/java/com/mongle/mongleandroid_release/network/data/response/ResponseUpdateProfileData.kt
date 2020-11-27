package com.mongle.mongleandroid_release.network.data.response

data class ResponseUpdateProfileData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<Profile>
)

data class Profile(
    val name : String, // 수정된 이름
    val img : String, // 수정된 프사
    val introduce : String, // 수정된 소개
    val keyword : String, // 수정된 키워드
    val keywordIdx : Int // 수정된 키워드 Idx
)