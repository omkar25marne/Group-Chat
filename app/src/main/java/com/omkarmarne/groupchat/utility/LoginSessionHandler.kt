package com.omkarmarne.groupchat.utility

import android.content.Context
import android.content.SharedPreferences

class LoginSessionHandler(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun login(userId: String, picture: String, name: String, mobile: String) {
        sharedPreferences.edit()
            .putString(SHARED_PREF_KEY_USER_ID, userId)
            .putString(SHARED_PREF_KEY_PROFILE_PICTURE, picture)
            .putString(SHARED_PREF_KEY_NAME, name)
            .putString(SHARED_PREF_KEY_MOBILE, mobile)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getString(SHARED_PREF_KEY_MOBILE, null) != null
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(SHARED_PREF_KEY_USER_ID, null)
    }

    fun getProfilePicture(): String? {
        return sharedPreferences.getString(SHARED_PREF_KEY_PROFILE_PICTURE, null)
    }

    fun getName(): String? {
        return sharedPreferences.getString(SHARED_PREF_KEY_NAME, null)
    }

    fun getMobile(): String? {
        return sharedPreferences.getString(SHARED_PREF_KEY_MOBILE, null)
    }

    companion object {
        private const val SHARED_PREF_NAME = "group_chat"
        private const val SHARED_PREF_KEY_USER_ID = "user_id"
        private const val SHARED_PREF_KEY_PROFILE_PICTURE = "profile_picture"
        private const val SHARED_PREF_KEY_NAME = "name"
        private const val SHARED_PREF_KEY_MOBILE = "mobile"
    }
}