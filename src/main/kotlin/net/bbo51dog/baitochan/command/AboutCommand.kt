package net.bbo51dog.baitochan.command

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder

class AboutCommand : Command() {

    init {
        name = "about"
        help = "バイトちゃんについての情報を表示します"
    }

    override fun execute(event: CommandEvent?) {
        val builder = EmbedBuilder()
            .setTitle("バイトちゃんBotについて")
            .setDescription(">> バイトちゃんBotはなまけものサーバーDiscord用サポートBotです。当Botはオープンソースで開発されています。")
            .addField("GitHub", GITHUB_URL, false)
        event?.reply(builder.build())
    }

    companion object {
        const val GITHUB_URL = "https://github.com/NKServer/BaitoChanBot"
    }
}