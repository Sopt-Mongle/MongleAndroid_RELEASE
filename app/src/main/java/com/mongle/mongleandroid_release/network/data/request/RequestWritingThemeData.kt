package com.mongle.mongleandroid_release.network.data.request

data class RequestWritingThemeData (
    var theme: String,	//생성할 테마 이름
    var themeImgIdx: Int //생성할 테마의 배경 사진 idx
)