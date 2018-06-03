package com.tsengvn.tausman

import com.tsengvn.tausman.cache.MemCache
import com.tsengvn.tausman.cache.RedisCache
import com.tsengvn.tausman.command.BotCommand
import com.tsengvn.tausman.command.BotCommandFactory
import com.tsengvn.tausman.command.TaskCommand
import com.tsengvn.tausman.data.BotConfig
import com.tsengvn.tausman.data.CommandConfig
import com.tsengvn.tausman.data.JiraConfig
import com.ullink.slack.simpleslackapi.SlackPreparedMessage
import com.ullink.slack.simpleslackapi.SlackSession
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener
import kotlinx.coroutines.experimental.launch

class BotEngine(var config: BotConfig = BotConfig(), var jiraConfig: JiraConfig = JiraConfig()) {
    private val cache = RedisCache()
    private val commandRegistry  = hashMapOf<String, BotCommandFactory<BotCommand>>()
    private var botId : String? = null


    private val messagePostedListener = SlackMessagePostedListener { event, session ->
        handleIncomingEvent(event, session)
    }

    fun start() {
        SlackSessionFactory.createWebSocketSlackSession(config.authToken).apply {
            connect()
            addMessagePostedListener(messagePostedListener)
            botId = findUserByUserName(config.botName).id
            println("${config.botName} is started with id $botId")
        }

    }


    private fun handleIncomingEvent(event: SlackMessagePosted, session: SlackSession) {
        println(event.toString())

        if (event.isNotMe()) {
            launch {
                val key = event.getCommandKey()
                val command = commandRegistry[key]

                command?.create(cache = cache, jiraConfig = jiraConfig)?.apply {
                    handle(event, session)
                }
            }
        }
    }


    fun config(block: BotConfig.() -> Unit) {
        config = BotConfig().apply(block)
    }

    fun jiraConfig(block : JiraConfig.() -> Unit) {
        jiraConfig = JiraConfig().apply(block)
    }

    fun command(block : CommandConfig.() -> Unit) {
        val config = CommandConfig().apply(block)
        register(config.command, config.factory)
    }

    fun register(key : String, factory : BotCommandFactory<out BotCommand>?) {
        factory?.let {
            if (!commandRegistry.containsKey(key)) {
                commandRegistry.put(key, it)
            }
        }

    }

    private fun SlackMessagePosted.getCommandKey() : String{
        return messageContent.split(regex = Regex("\\s+"))[0]
    }

    private fun SlackMessagePosted.isNotMe() : Boolean = botId != user.id

}

fun botEngine(block : BotEngine.() -> Unit) : BotEngine {
    return BotEngine().apply(block)
}