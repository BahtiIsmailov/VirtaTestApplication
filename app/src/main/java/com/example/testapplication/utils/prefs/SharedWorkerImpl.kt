package com.example.testapplication.utils.prefs

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedWorkerImpl(context: Context) : SharedWorker {

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    override fun load(key: String, defValue: String): String {
        return preferences.getString(key, defValue)!!
    }

    override fun load(key: String, defValue: Long): Long {
        return preferences.getLong(key, defValue)
    }

    override fun load(key: String, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    override fun saveMediate(key: String, value: String): Boolean {
        return preferences.edit().putString(key, value).commit()
    }

    override fun saveMediate(key: String, value: Int): Boolean {
        return preferences.edit().putInt(key, value).commit()
    }

    override fun saveMediate(key: String, value: Boolean): Boolean {
        return preferences.edit().putBoolean(key, value).commit()
    }

    override fun save(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    override fun save(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    override fun save(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    override fun load(key: String, defValue: Boolean): Boolean {
        return preferences.getBoolean(key, defValue)
    }

    override fun save(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    override fun isAllExists(vararg keys: String): Boolean {
        var result = true
        for (key in keys) {
            if (!preferences.contains(key)) {
                result = false
                break
            }
        }
        return result
    }

    override fun delete(vararg keys: String) {
        if (keys.isNotEmpty()) {
            for (keyElement in keys) {
                preferences.edit().remove(keyElement).apply()
            }
        }
    }

    companion object {
        private const val DEFAULT = ""
    }

}