package com.example.phonecinemaapp.data.local

import android.content.Context

object UserPrefs {

    private const val PREFS = "user_prefs"
    private const val FOTO_KEY = "foto_perfil"

    fun saveFoto(context: Context, uri: String) {
        val sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        sp.edit().putString(FOTO_KEY, uri).apply()
    }

    fun getFoto(context: Context): String {
        val sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return sp.getString(FOTO_KEY, "") ?: ""
    }
}
