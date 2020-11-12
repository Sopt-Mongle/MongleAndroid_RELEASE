package com.example.mongleandroid_release.network

import android.content.Context

object SharedPreferenceController {

    private val TOKEN = "TOKEN"
    private val MY_ACCOUNT = "MY_ACCOUNT"
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

    fun getMail(context: Context) : String {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_MAIL", "").toString()
    }

    fun setPasswd(context: Context, input: String) {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("MY_PW", input)
        editor.apply()
    }

    fun getPasswd(context: Context) : String {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PW", "").toString()
    }

    fun clearUser(context: Context) {
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    // 프로필 수정






}