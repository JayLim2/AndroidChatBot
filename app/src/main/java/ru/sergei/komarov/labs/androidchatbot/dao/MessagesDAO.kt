package ru.sergei.komarov.labs.androidchatbot.dao

interface DAO<T> {
    fun insert(message: T)
    fun update(id: Int, message: T)
    fun delete(id: Int)
    fun getById(id: Int)
}