package net.bbo51dog.baitochan

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EventListener : ListenerAdapter() {

    val mentionedMessages = listOf(
        "バイトちゃんだよ〜",
    )

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return
        if (event.message.mentionedUsers.contains(event.jda.selfUser)) {
            event.channel.sendMessage(mentionedMessages.random()).queue()
        }
    }
}