package net.bbo51dog.baitochan

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jagrosh.jdautilities.command.CommandClientBuilder
import net.bbo51dog.baitochan.command.AboutCommand
import net.bbo51dog.baitochan.command.StatusCommand
import net.dv8tion.jda.api.EmbedBuilder
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

fun main() {
    val commandClient = CommandClientBuilder()
        .setOwnerId("")
        .setPrefix(COMMAND_PREFIX)
        .addCommands(
            AboutCommand(),
            StatusCommand(),
        )
        .setHelpConsumer { event ->
            val builder = EmbedBuilder()
                .setTitle("バイトちゃんBot Help")
                .setDescription("メンションされるとお話するよ")
            event.client.commands.forEach {
                builder.addField("__" + event.client.textualPrefix + it.name + "__", ">> " + it.help, false)
            }
            event.reply(builder.build())
        }
        .build()

    val jda = JDABuilder.createDefault(System.getenv(TOKEN_ENV))
        .addEventListeners(
            commandClient,
            EventListener()
        )
        .build()

    updateActivity(jda, Executors.newScheduledThreadPool(1))
}

fun updateActivity(jda: JDA, scheduler: ScheduledExecutorService) {
    scheduler.scheduleAtFixedRate(
        {
            val connection = URL(StatusCommand.URL_BASE + StatusCommand.ADDRESS).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            val data = jacksonObjectMapper().readTree(connection.inputStream)
            jda.presence.activity = Activity.playing(
                COMMAND_PREFIX + " - " + data.get("players").get("online").asText() + " Online Players"
            )
        },
        0,
        1,
        TimeUnit.MINUTES
    )
}