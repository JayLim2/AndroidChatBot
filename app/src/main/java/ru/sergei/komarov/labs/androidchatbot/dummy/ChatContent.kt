package ru.sergei.komarov.labs.androidchatbot.dummy

import java.util.*

/**
 * Helper class for providing chat content for UI
 *
 */
object ChatContent {

    val ITEMS: MutableList<ChatItem> = ArrayList()

    fun addItem(item: ChatItem) {
        ITEMS.add(item)
    }

    fun createChatItemByData(isSystemMessage: Boolean, message: String): ChatItem {
        return ChatItem(isSystemMessage, message, "")
    }

    /**
     * Object assigned with message on UI
     */
    data class ChatItem(
        var isSystemMessage: Boolean,
        var content: String,
        var details: String
    ) {

        override fun toString(): String = content
    }
}
