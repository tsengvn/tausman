package com.tsengvn.tausman.data

import com.tsengvn.tausman.command.BotCommand
import com.tsengvn.tausman.command.BotCommandFactory

data class CommandConfig (var command : String = "", var factory: BotCommandFactory<out BotCommand>? = null)