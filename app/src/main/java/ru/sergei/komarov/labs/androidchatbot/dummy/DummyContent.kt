package ru.sergei.komarov.labs.androidchatbot.dummy

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    fun addItem(item: DummyItem) {
        ITEMS.add(item)
    }

    fun createDummyItemByData(isSystemMessage: Boolean, message: String): DummyItem {
        return DummyItem(isSystemMessage, message, "")
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(var isSystemMessage: Boolean,
                         var content: String,
                         var details: String) {

        override fun toString(): String = content
    }
}
