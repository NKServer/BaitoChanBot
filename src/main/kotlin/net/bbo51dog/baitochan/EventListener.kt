package net.bbo51dog.baitochan

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EventListener : ListenerAdapter() {

    val mentionedMessages = listOf(
        "バイトちゃんだよ〜",
        "おやすみなさい",
        "おやすみ",
        "こんにちは！",
        "おはようございます！",
        "おはこんばんにちは！",
        "私って猫が好きなんです！",
        "私の好きなものはスイーツです！",
        "なまさばの開発コードはFolivoraっていうんです！",
        "なんでか私のタスクって減らしても減らしても減らないんです。。。",
        "bc!statusでサーバーのステータス情報が見れるんです！",
        "Discordと鯖がつながってるの私くらいしかないんですよね。。。",
        "いつもバイトが忙しいけど私は頑張ります！！",
        "今度友達と遊びに行くんです！また、話したいな！",
        "私のバイト先は本屋なんです！こう見えても本は結構読むんですよ！",
        "やだぁ～むり～って誰かがいってました。。。！",
        "さいあくなんだけど～って誰かがいってました。。。！",
        "なまけものって高校生なのに彼女いたことないらしいですよ？",
        "びーぼは最近失恋したみたいです。。。",
        "なまけものの年齢は2022年2月現在では16歳のようです！",
        "私の趣味は本を読むことです！本屋に働いてるのも本が好きだからです！",
        "さwいｗさwなwwwって古の文化があるみたいです、、、私は知らないですけど、、、",
        "なまけもののツイッターは@lazyperson0710です！",
        "びーぼのツイッターは@bbo51dogです！",
        "眠いです。。。最近あまり寝れてなくて。。。",
        "びーぼってスキンでは前髪剥げてるらしいよ！",
        "ねこってかわいいですよね！ねこねこ！",
        "にゃん♪にゃん♪",
        "わん♪わん♪",
        "ぴえん♪ぴえん♪",
    )

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return
        if (event.message.mentionedUsers.contains(event.jda.selfUser)) {
            event.channel.sendMessage(mentionedMessages.random()).queue()
        }
    }
}