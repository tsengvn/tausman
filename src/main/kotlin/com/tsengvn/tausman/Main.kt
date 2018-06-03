package com.tsengvn.tausman

import com.tsengvn.tausman.command.TaskCommand
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.error
import org.slf4j.LoggerFactory

fun main(args : Array<String>) {
    val port = Integer.valueOf(configOf("PORT"))
//    embeddedServer(Netty, port, reloadPackages = listOf("heroku"), module = Application::module).start()

    val server = embeddedServer(Netty, port) {
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
        }
        runBot()
    }
    server.start(wait = true)
}

fun runBot() {
    Thread.UncaughtExceptionHandler { t, e -> LoggerFactory.getLogger("Main").error(e) }
    botEngine {
        config {
            botName = configOf("botName")
            authToken = configOf("authToken")
        }

        jiraConfig {
            email = configOf("jiraEmail")
            password = configOf("jiraPassword")
            uri = configOf("jiraUri")
            project = configOf("jiraProject")
        }

        command {
            command = "task"
            factory = TaskCommand.Factory()
        }

    }.start()
}