package ru.sergei.komarov.labs.androidchatbot.dao

import ru.sergei.komarov.labs.androidchatbot.models.Message

interface DAO<T> {
    fun insert(message: T)
    fun update(id: Int, message: T)
    fun delete(id: Int)
    fun getById(id: Int): Message?
    fun getAll(): List<Message>
}