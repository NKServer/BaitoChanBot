package net.bbo51dog.baitochan.command

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.managers.ChannelManager
import java.awt.Color
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.temporal.TemporalAccessor
import java.util.*

class StatusCommand : Command() {

    init {
        name = "status"
        help = "サーバーのステータスを表示"
    }

    override fun execute(event: CommandEvent?) {
        val connection = URL(Companion.URL_BASE + Companion.ADDRESS).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()
        val data = jacksonObjectMapper().readTree(connection.inputStream)
        val cacheTime = data.get("debug").get("cachetime").asLong()
        val builder = EmbedBuilder()
            .setTitle("NKServer Status", "https://www.nkserver.net/")
        if (!cacheTime.equals(0)) {
            builder.setTimestamp(LocalDateTime.ofEpochSecond(cacheTime, 0, ZoneOffset.UTC))
        }
        if (data.get("online").asBoolean()) {
            val playerListNode = data.get("players").get("list")
            if (playerListNode !== null) {
                var players = ""
                data.get("players").get("list").forEach {
                    players += it.asText() + ", "
                }
                builder.addField("Players", players, false)
            }
            builder
                .setDescription("サーバーはOnlineだよ〜")
                .setColor(Color.GREEN)
                .addField("PlayerCount", data.get("players").get("online").asText() + " / " + data.get("players").get("max").asText(), true)
                .addField("Version", data.get("version").asText(), true)
        } else {
            builder
                .setDescription("サーバーはOfflineだよ〜")
                .setColor(Color.RED)
                .addField("鯖落ち情報の提供はこちら", "#バグ報告所", false)
        }
        event?.reply(builder.build())
    }

    companion object {
        const val URL_BASE = "https://api.mcsrvstat.us/2/"
        const val ADDRESS = "nkserver.net:19132"
    }
}