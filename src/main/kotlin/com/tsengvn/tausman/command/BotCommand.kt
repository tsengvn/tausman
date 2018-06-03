package com.tsengvn.tausman.command

import com.tsengvn.tausman.cache.Cache
import com.tsengvn.tausman.data.JiraConfig
import com.tsengvn.tausman.data.SlackResponse
import com.ullink.slack.simpleslackapi.SlackSession
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted

interface BotCommand {
    suspend fun handle(event: SlackMessagePosted, session: SlackSession)
}

interface BotCommandFactory<out BotCommand> {
    fun create(cache: Cache, jiraConfig: JiraConfig) : BotCommand
}