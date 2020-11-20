package com.example.mongleandroid_release.network.data.request

import kotlinx.serialization.PrimitiveKind

data class RequestReportSentence(
    var sort : String = "",
    var idx : Int = 0,
    var content : String = ""
)