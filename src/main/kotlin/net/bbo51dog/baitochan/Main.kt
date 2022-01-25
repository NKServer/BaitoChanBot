package net.bbo51dog.baitochan

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jagrosh.jdautilities.command.CommandClientBuilder
import net.bbo51dog.baitochan.command.StatusCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


const val TOKEN_ENV = "BAITO_CHAN_TOKEN"
const val COMMAND_PREFIX = "bc!"
const val OWNER_ID = "525907341950844939"

fun main() {
    val commandClient = CommandClientBuilder()
        .setOwnerId(OWNER_ID)
        .setPrefix(COMMAND_PREFIX)
        .addCommand(StatusCommand())
        .build()

    val jda = JDABuilder.createDefault(System.getenv(TOKEN_ENV))
        .addEventListeners(commandClient)
        .build()

    updateActivity(jda, Executors.newScheduledThreadPool(1))
}

fun updateActivity(jda: JDA, scheduler: ScheduledExecutorService) {
    scheduler.schedule(
        {
            val connection = URL(StatusCommand.URL_BASE + StatusCommand.ADDRESS).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            val data = jacksonObjectMapper().readTree(connection.inputStream)
            jda.presence.activity = Activity.playing(COMMAND_PREFIX + " - " + data.get("players").get("online").asText() + " Online Players")
            updateActivity(jda, scheduler)
        },
        1,
        TimeUnit.MINUTES
    )
}