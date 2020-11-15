package com.example.mongleandroid_release.network.data.response

data class ResponseEditorsPickData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<EditorsPick>
)

data class EditorsPick(
    val editorpickIdx: Int, // 에디터's pick 인덱스
    val illust: String, // 컨텐츠 이미지
    val themeIdx: Int, // 컨텐츠에 해당하는 테마 idx
    val sentenceNum: Int // 테마 안에 속해있는 문장 개수
)