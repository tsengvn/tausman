package com.tsengvn.tausman.cache

import com.tsengvn.tausman.configOf
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.net.URI

class RedisCache : Cache {
    private var jedisPool: JedisPool? = null
    private val jedis: Jedis?
        get() = jedisPool?.resource

    init {
        jedisPool = JedisPool(toJedisPoolConfig(), URI.create(configOf("REDIS_URL")))
    }

    override fun set(id: String, value: String) {
        jedis?.set(id, value)
    }

    override fun get(id: String) = jedis?.get(id)

    private fun toJedisPoolConfig(): JedisPoolConfig {
        return JedisPoolConfig().apply {
            maxWaitMillis = 500
            maxTotal = 30
            maxIdle = 5
            minIdle = 1
            testOnBorrow = true
            testOnReturn = true
            testWhileIdle = true
        }
    }


}