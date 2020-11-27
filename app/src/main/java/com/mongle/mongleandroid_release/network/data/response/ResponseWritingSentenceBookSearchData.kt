package com.mongle.mongleandroid_release.network.data.response


data class ResponseWritingSentenceBookSearchData (
val staus : Int,
val success : Boolean,
val message : String,
val data : MutableList<BookData>

)
// bookSearch에 4가지 사용
data class BookData(
    val authors : MutableList<String>,	//책 저자 배열
    val contents : String,	//책 소개 -> 사용 안함
    val datetime : String,	//책 출간일 -> 사용 안함
    val isbn : String,	//책 고유 isbn 번호 -> 사용 안함
    val publisher : String,	//출판사
    val thumbnail : String,	//책 사진
    val title : String	//책 제목
)
