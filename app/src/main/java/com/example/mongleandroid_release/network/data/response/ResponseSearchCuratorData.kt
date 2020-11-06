package com.example.mongleandroid_release.network.data.response

data class ResponseSearchCuratorData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MutableList<SearchCurator>
)

data class SearchCurator (
    val curatorIdx : Int,// 검색된 큐레이터 idx
    val name : String, // 검색된 큐레이터 이름
    val img	: String, // 검색된 큐레이터 프로필 이미지
    val keyword	: String, // 검색된 큐레이터 키워드 배열
    val subscribe : Int, // 검색된 큐레이터 구독자 수
    val alreadySubscribed : Boolean
)