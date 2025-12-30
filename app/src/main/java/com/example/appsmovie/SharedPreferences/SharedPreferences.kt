package com.example.appsmovie.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences (context: Context) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences("MyAppSession", Context.MODE_PRIVATE)

    companion object {
        const val KEY_USER_ID = "user_id"
        const val IS_LOGGED_IN = "is_logged_in"
        const val KEY_EMAIL = "key_email"
        const val KEY_PASSWORD = "key_password"
    }

    fun createLoginSession(id: Int,email: String, password: String) {
        val editor = prefs.edit()
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putInt(KEY_USER_ID, id)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }

    fun getUserEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    fun logoutUser() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }

    fun saveLoginDetails(email: String, password: String) {
        val editor = prefs.edit()
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }

    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, 0)
    }

    fun getUserDetails(): HashMap<String, String?> {
        val user = HashMap<String, String?>()
        user[KEY_EMAIL] = prefs.getString(KEY_EMAIL, null)
        user[KEY_PASSWORD] = prefs.getString(KEY_PASSWORD, null)
        return user
    }
}