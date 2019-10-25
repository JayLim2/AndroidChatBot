package ru.sergei.komarov.labs.androidchatbot.models

import java.time.LocalDateTime

class Message {
    var message: String
    var userId: Int
    var date: LocalDateTime

    constructor(message: String, userId: Int, date: LocalDateTime) {
        this.message = message
        this.userId = userId
        this.date = date
    }
}