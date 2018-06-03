package com.tsengvn.tausman.cache

import sun.misc.LRUCache

class MemCache : Cache {
    val innerCache : LinkedHashMap<String, String>  = LinkedHashMap(20)

    override fun set(id: String, value: String) {
        innerCache[id] = value
    }

    override fun get(id: String): String? = innerCache[id]
}