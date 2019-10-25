package ru.sergei.komarov.labs.androidchatbot.services

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*
import android.database.sqlite.SQLiteException
import java.io.FileInputStream
import java.lang.NullPointerException
import java.nio.file.Paths

class DatabaseService(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_VERSION: Int = 1
        const val DB_NAME: String = "chat_bot"
        const val SQL_CREATE_TABLE_MESSAGES: String = "create_messages_table"
    }

    private val ownContext: Context? = context

    override fun onCreate(db: SQLiteDatabase?) {
        //create table "messages"
        val createMessagesTableSqlFile = getSQL(SQL_CREATE_TABLE_MESSAGES)
        db?.execSQL(createMessagesTableSqlFile)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Database upgrade method need be implemented.")
    }

    private fun getSQL(filename:String):String? {
        val stringBuilder = StringBuilder()
        try {
            if(ownContext == null) {
                throw NullPointerException("Nullable context isn't allowed in DatabaseService.")
            }

            val assetManager = ownContext.assets
            val reader = Scanner(assetManager.open("sql/$filename.sql"))
            reader.use {
                while (reader.hasNextLine()) {
                    stringBuilder.append(reader.nextLine())
                }
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}