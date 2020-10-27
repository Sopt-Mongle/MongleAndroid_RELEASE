package com.example.mongleandroid_release.network

import com.example.mongleandroid_release.network.data.request.RequestLoginData
import com.example.mongleandroid_release.network.data.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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

    // 추천 큐레이터
    @GET("/curator/recommend")
    fun getRecommendCurator() : Call<ResponseRecommendCuratorData>

    // 로그인
    @POST("/users/signin")
    fun requestLogin(@Body body: RequestLoginData) : Call<ResponseLoginData>

}