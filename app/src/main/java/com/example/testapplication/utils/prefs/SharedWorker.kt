package com.example.testapplication.utils.prefs


interface SharedWorker {
    fun load(key: String, defValue: Boolean): Boolean
    fun save(key: String, value: Boolean)
    fun load(key: String, defValue: String): String
    fun save(key: String, value: String)
    fun saveMediate(key: String,value: String):Boolean
    fun saveMediate(key: String,value: Int):Boolean
    fun saveMediate(key: String,value: Boolean):Boolean
    fun load(key: String, defValue: Long): Long
    fun save(key: String, value: Long)
    fun load(key: String, defValue: Int): Int
    fun save(key: String, value: Int)
    fun isAllExists(vararg keys: String): Boolean
    fun delete(vararg keys: String)

}