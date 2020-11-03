package com.example.mongleandroid_release.network.data.request

data class RequestWritingSentenceData (
    val sentence: String = "", //작성할 문장
    val title:	String = "", //작성할 문장이 속한 책 제목
    val author:	String = "", //작성할 문장이 속한 책의 저자
    val publisher:	String = "", //작성할 문장이 속한 책 출판사
    val thumbnail:	String = "", //작성할 문장이 속한 책 사진
    val themeIdx:	Int	= 0 //선택한 테마의 idx(0 : 테마 없는 테마, 그외 : 해당 테마)
)

//object RequestWritingSentenceData()