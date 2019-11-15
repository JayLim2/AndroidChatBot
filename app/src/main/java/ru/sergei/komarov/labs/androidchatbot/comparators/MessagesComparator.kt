package ru.sergei.komarov.labs.androidchatbot.comparators

import ru.sergei.komarov.labs.androidchatbot.models.Message

class MessagesComparator : Comparator<Message> {
    override fun compare(o1: Message?, o2: Message?): Int {
        if (o1 != null && o2 != null) {
            return o1.date.compareTo(o2.date)
        }
        throw NullPointerException("Null dates in messages.")
    }
}