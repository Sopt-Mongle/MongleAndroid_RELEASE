package com.example.mongleandroid_release.network

import com.example.mongleandroid_release.network.data.response.ResponseLibraryCuratorData
import com.example.mongleandroid_release.network.data.response.ResponseLibrarySentenceData
import com.example.mongleandroid_release.network.data.response.ResponseLibraryThemeData
import com.example.mongleandroid_release.network.data.response.ResponseMainLibraryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RequestInterface {

    //내서재 메인 프로필 조회
    @GET("/my/profile")
    fun lookLibraryProfile(
        @Header("token") token: String?
    ) : Call<ResponseMainLibraryData>

    //내서재 테마 조회
    @GET("/my/theme")
    fun lookLibraryThema(
        @Header("token") token: String?
    ) : Call<ResponseLibraryThemeData>

    //내서재 테마 조회
    @GET("/my/sentence")
    fun lookLibrarySentence(
        @Header("token") token: String?
    ) : Call<ResponseLibrarySentenceData>

    //내서재 구독 조회
    @GET("/my/subscribe")
    fun lookLibraryCurator(
        @Header("token") token: String?
    ) : Call<ResponseLibraryCuratorData>


}