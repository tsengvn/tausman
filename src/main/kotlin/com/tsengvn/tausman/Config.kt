package com.tsengvn.tausman

import java.util.*
import kotlin.collections.HashMap

fun loadConfig(fileName : String) : HashMap<String, String> {
    val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
    val conf = Properties()
    conf.load(stream)
    val map = hashMapOf<String, String>()
    conf.stringPropertyNames().asSequence().forEach {
        map.put(it, conf.getProperty(it))
    }
    return map
}

fun configOf(name : String) : String = System.getenv(name) ?: getConfigLocal(name)

private fun getConfigLocal(name : String) : String {
    val stream = Thread.currentThread().contextClassLoader.getResourceAsStream("local.conf")
    val conf = Properties()
    conf.load(stream)
    return conf.getProperty(name) ?: ""
}
