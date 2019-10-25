package ru.sergei.komarov.labs.androidchatbot.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.sergei.komarov.labs.androidchatbot.models.Message
import java.time.format.DateTimeFormatter

class MessagesDAOImpl : DAO<Message> {

    private val TABLE_NAME = "messages"

    private val dbInstance:SQLiteDatabase

    constructor(dbInstance: SQLiteDatabase) {
        this.dbInstance = dbInstance
    }

    override fun insert(message: Message) {
        val contentValues = ContentValues()
        contentValues.put("message", message.message)
        contentValues.put("userId", message.userId)
        contentValues.put("date", DateTimeFormatter.ISO_DATE_TIME.format(message.date))
        val returnedId = dbInstance.insert(
            TABLE_NAME,
            null,
            contentValues
        )

        println("message inserted with id = $returnedId")
    }

    override fun update(id: Int, message: Message) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}