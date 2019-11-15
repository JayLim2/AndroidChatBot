package ru.sergei.komarov.labs.androidchatbot.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.sergei.komarov.labs.androidchatbot.models.Message
import ru.sergei.komarov.labs.androidchatbot.rest.Client
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessagesDAOImpl : DAO<Message> {

    private val TABLE_NAME = "messages"

    private val dbInstance:SQLiteDatabase

    constructor(dbInstance: SQLiteDatabase) {
        this.dbInstance = dbInstance
    }

    override fun insert(message: Message) {
        val query = "SELECT _id FROM " + TABLE_NAME + " WHERE _id = " + message.id
        val cursor = dbInstance.rawQuery(query, null)
        val isExists = cursor.moveToFirst()
        cursor.close()

        if (!isExists) {
            println("NOT EXISTS")
            val contentValues = ContentValues()
            contentValues.put("_id", message.id)
            contentValues.put("message", message.message)
            contentValues.put("userId", message.userId)
            contentValues.put("date", DateTimeFormatter.ISO_DATE_TIME.format(message.date))
            val returnedId = dbInstance.insert(
                TABLE_NAME,
                null,
                contentValues
            )

            println("message inserted with id = $returnedId")
        } else {
            println("message with id = ${message.id} already exists")
        }
    }

    override fun update(id: Int, message: Message) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(id: Int): Message? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun getAll(): List<Message> {
        val messages: MutableList<Message> = ArrayList()

        val cursor = dbInstance.query(
            TABLE_NAME, null, null, null, null, null, null, null
        )

        for (columnName in cursor.columnNames) {
            println("COLUMN: " + columnName)
        }

        println(cursor.count)
        println(cursor.columnCount)

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getInt(cursor.getColumnIndex("_id"))
                val message = cursor.getString(cursor.getColumnIndex("message"))
                val userId = cursor.getString(cursor.getColumnIndex("userId"))
                val date = cursor.getString(cursor.getColumnIndex("date"))

                val messageItem = Message(
                    id,
                    message,
                    userId,
                    LocalDateTime.parse(date, Client.DATE_TIME_FORMATTER)
                )
                messages.add(messageItem)

                cursor.moveToNext()
            }
            cursor.close()
        }

        return messages
    }
}