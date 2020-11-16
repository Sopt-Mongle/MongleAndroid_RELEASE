package com.example.mongleandroid_release.network

import android.content.Context
import android.net.Uri

object SharedPreferenceController {

    private val TOKEN = "TOKEN"
    private val MY_ACCOUNT = "MY_ACCOUNT"
    private val MY_PROFILE = "MY_PROFILE"
    private val ON_BOARDING = "ON_BOARDING"

    // 토큰
    fun setAccessToken(context: Context, authorization: String?) {
        val pref = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("access_token", authorization)
        editor.commit()
    }

    fun getAccessToken(context: Context): String? {
        val pref = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
        return pref.getString("access_token", "")
    }

    fun clearAccessToken(context: Context) {
        val pref = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }

    // 자동로그인
    fun setMail(context: Context, input: String) {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("MY_MAIL", input)
        editor.apply()
    }

    fun getMail(context: Context) : String? {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_MAIL", "")
    }

    fun setPasswd(context: Context, input: String) {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("MY_PW", input)
        editor.apply()
    }

    fun getPasswd(context: Context) : String? {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PW", "")
    }

    fun clearUser(context: Context) {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    // 프로필 수정
    fun setImage(context: Context, input: Uri) {
        val prefs = context.getSharedPreferences(MY_PROFILE, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("IMAGE", input.toString())
        editor.apply()
    }

    fun getImage(context: Context) : String? {
        val prefs = context.getSharedPreferences(MY_PROFILE, Context.MODE_PRIVATE)
        return prefs.getString("IMAGE", "")
    }

    fun setName(context: Context, input: String) {
        val prefs = context.getSharedPreferences(MY_PROFILE, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("NAME", input)
        editor.apply()
    }

    fun getName(context: Context) : String? {
        val prefs = context.getSharedPreferences(MY_PROFILE, Context.MODE_PRIVATE)
        return prefs.getString("NAME", "")
    }

    fun setKeywordIdx(context: Context, input: String) {
        val prefs = context.getSharedPreferences(MY_PROFILE, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("KEYWORD", input)
        editor.apply()
    }

    fun getKeywordIdx(context: Context) : String? {
        val prefs = context.getSharedPreferences(MY_PROFILE, Context.MODE_PRIVATE)
        return prefs.getString("KEYWORD", "")
    }

    fun setIntroduce(context: Context, input: String) {
        val prefs = context.getSharedPreferences(MY_PROFILE, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("INTRODUCE", input)
        editor.apply()
    }

    fun getIntroduce(context: Context) : String? {
        val prefs = context.getSharedPreferences(MY_PROFILE, Context.MODE_PRIVATE)
        return prefs.getString("INTRODUCE", "")
    }

    // 온보딩 한번 체크
    fun setOnBoarding(context: Context, input: String) {
        val prefs = context.getSharedPreferences(ON_BOARDING, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("ON_BOARDING", input)
        editor.apply()
    }

    fun getOnBoarding(context: Context) : String? {
        val prefs = context.getSharedPreferences(ON_BOARDING, Context.MODE_PRIVATE)
        return prefs.getString("ON_BOARDING", "")
    }


}