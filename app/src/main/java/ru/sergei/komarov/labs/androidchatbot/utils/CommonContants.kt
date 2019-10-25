package ru.sergei.komarov.labs.androidchatbot.utils

import ru.sergei.komarov.labs.androidchatbot.services.DatabaseService

class CommonContants {
    companion object {
        val DB_VERSION: Int = DatabaseService.DB_VERSION
        val DB_NAME: String = DatabaseService.DB_NAME
    }
}