package net.bbo51dog.baitochan

import com.jagrosh.jdautilities.command.CommandClientBuilder
import net.bbo51dog.baitochan.command.StatusCommand
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

const val TOKEN_ENV = "BAITO_CHAN_TOKEN"
const val COMMAND_PREFIX = "bc!"

fun main() {
    val commandClient = CommandClientBuilder()
        .setOwnerId("")
        .setPrefix(COMMAND_PREFIX)
        .setActivity(Activity.playing("nkserver.net"))
        .addCommand(StatusCommand())
        .build()

    JDABuilder.createDefault(System.getenv(TOKEN_ENV))
        .addEventListeners(commandClient)
        .build()
}