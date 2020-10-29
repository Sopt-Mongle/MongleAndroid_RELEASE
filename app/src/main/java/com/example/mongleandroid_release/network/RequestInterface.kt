package com.example.mongleandroid_release.network

import com.example.mongleandroid_release.network.data.request.RequestLoginData
import com.example.mongleandroid_release.network.data.response.*
import retrofit2.Call
import retrofit2.http.*

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

    // 검색 - 최근 키워드
    @GET("/search/recent")
    fun requestSearchRecent(
        @Header("token") token: String?
    ) : Call<ResponseSearchRecentData>

    // 검색 - 최근 키워드 전체 삭제
    @DELETE("/search/recent")
    fun requestSearchRecentDelete(
        @Header("token") token: String?
    ) : Call<ResponseSearchRecentDeleteData>

    // 검색 - 추천 키워드
    @GET("/search/recommend")
    fun getRecommendKeyword() : Call<ResponseSearchRecommendData>

    // 테마 검색
    @GET("/search/theme")
    fun requestSearchTheme(
        @Header("token") token: String?,
        @Query("words") words: String
    ) : Call<ResponseSearchThemeData>

    // 문장 검색 - 성공
    @GET("/search/sentence")
    fun requestResultSentenceData(
        @Query("words") words: String
    ) : Call<ResponseSearchSentenceData>

    // 큐레이터 검색 - 성공
    @GET("/search/curator")
    fun requestResultCuratorData(
        @Header("token") token: String?,
        @Query("words") words: String
    ) : Call<ResponseSearchCuratorData>

}