package com.ersiver.test_krunal.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "login_prefs"
        private const val EMAIL_KEY = "email"
        private const val PASSWORD_KEY = "password"
        private const val IS_LOGIN = "is_login"
    }

    fun saveLoginDetails(email: String, password: String) {
        with(prefs.edit()) {
            putString(EMAIL_KEY, email)
            putString(PASSWORD_KEY, password)
            apply()
        }
    }

    fun setLogin(isLogin: Boolean) {
        with(prefs.edit()) {
            putBoolean(IS_LOGIN, isLogin)
            apply()
        }
    }

    fun getLogin(): Boolean {
        return prefs.getBoolean(IS_LOGIN, false)
    }

    fun getEmail(): String? {
        return prefs.getString(EMAIL_KEY, null)
    }

    fun getPassword(): String? {
        return prefs.getString(PASSWORD_KEY, null)
    }

    fun clearLoginDetails() {
        with(prefs.edit()) {
            remove(EMAIL_KEY)
            remove(PASSWORD_KEY)
            remove(IS_LOGIN)
            apply()
        }
    }
}
