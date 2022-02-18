package com.example.composedemo.utils

import com.example.composedemo.Message
import com.example.composedemo.WidgetType

object ResourceUtil {

    fun getMessageList(): List<Message> {
        val title = "Jetpack Compose 博物馆"

        val messageList =  listOf(
            libraryMessage("我们开始更新啦"),
            libraryMessage("为了给广大的读者一个更好的体验，从今天起，我们公众号决定陆续发一些其他作者的高质量文章"),
            libraryMessage("每逢佳节倍思亲，从今天起，参加我们公众号活动的同学可以获得精美礼品一份！！"),
            libraryMessage("荣华梦一场，功名纸半张，是非海波千丈，马蹄踏碎禁街霜，听几度头鸡唱"),
            libraryMessage("唤归来，西湖山上野猿哀。二十年多少风流怪，花落花开。望云霄拜将台，袖星斗安邦策，破烟月迷魂寨。酸斋笑我，我笑酸斋"),
            libraryMessage("伤心尽处露笑颜，醉里孤单写狂欢。两路殊途情何奈，三千弱水忧忘川。花开彼岸朦胧色，月过长空爽朗天。青鸟思飞无侧羽，重山万水亦徒然"),
            libraryMessage("又到绿杨曾折处，不语垂鞭，踏遍清秋路。衰草连天无意绪，雁声远向萧关去。恨天涯行役苦，只恨西风，吹梦成今古。明日客程还几许，沾衣况是新寒雨"),
            libraryMessage("莫笑农家腊酒浑，丰年留客足鸡豚。山重水复疑无路，柳暗花明又一村。箫鼓追随春社近，衣冠简朴古风存。从今若许闲乘月，拄杖无时夜叩门")
        )

        val repeatMessageList = arrayListOf<Message>()
        repeat(3) {
            repeatMessageList += messageList
        }

        return repeatMessageList
    }

    fun getAnimationList(): List<Message> {
        return listOf(
            animationMessage("淡入淡出动画", WidgetType.ANIM_FADE),
            animationMessage("淡入淡出动画", WidgetType.ANIM_FADE),
            animationMessage("淡入淡出动画", WidgetType.ANIM_FADE),
            animationMessage("淡入淡出动画", WidgetType.ANIM_FADE),
            animationMessage("淡入淡出动画", WidgetType.ANIM_FADE),
        )
    }

    private fun libraryMessage(body: String, title: String = "Jetpack Compose 博物馆") = Message(title, body)

    private fun animationMessage(body: String, type: WidgetType, title: String = "Compose动画") = Message(title, body)

}