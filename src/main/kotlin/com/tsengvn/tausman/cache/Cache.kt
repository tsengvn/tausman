package com.tsengvn.tausman.cache

interface Cache {
    fun set(id : String, value : String)
    fun get(id : String) : String?
}