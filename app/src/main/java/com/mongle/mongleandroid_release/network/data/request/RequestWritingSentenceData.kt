package com.mongle.mongleandroid_release.network.data.request

data class RequestWritingSentenceData (
    var sentence: String = "", //작성할 문장
    var title:	String = "", //작성할 문장이 속한 책 제목
    var author:	String = "", //작성할 문장이 속한 책의 저자
    var publisher:	String = "", //작성할 문장이 속한 책 출판사
    var thumbnail:	String = "", //작성할 문장이 속한 책 사진
    var themeIdx:	Int	= 0 //선택한 테마의 idx(0 : 테마 없는 테마, 그외 : 해당 테마)

)
