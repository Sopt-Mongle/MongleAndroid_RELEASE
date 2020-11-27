package com.mongle.mongleandroid_release.network.data.request

data class RequestDuplicateData (
    val email : String, // 중복 확인할 이메일 주소
    val name : String // 중복 확인할 닉네임
)