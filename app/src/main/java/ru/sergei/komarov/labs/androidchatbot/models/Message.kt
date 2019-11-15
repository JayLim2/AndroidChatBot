package ru.sergei.komarov.labs.androidchatbot.models

import java.time.LocalDateTime

class Message {
    var id: Int? = null
    var message: String
    var userId: String
    var date: LocalDateTime

    constructor(message: String, userId: String, date: LocalDateTime) {
        this.message = message
        this.userId = userId
        this.date = date
    }

    constructor(id: Int, message: String, userId: String, date: LocalDateTime) {
        this.id = id
        this.message = message
        this.userId = userId
        this.date = date
    }
}