package ru.sergei.komarov.labs.androidchatbot.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import ru.sergei.komarov.labs.androidchatbot.ChatActivity
import ru.sergei.komarov.labs.androidchatbot.R
import ru.sergei.komarov.labs.androidchatbot.SettingsActivity
import ru.sergei.komarov.labs.androidchatbot.dao.MessagesDAOImpl
import ru.sergei.komarov.labs.androidchatbot.dummy.DummyContent
import ru.sergei.komarov.labs.androidchatbot.models.Message
import ru.sergei.komarov.labs.androidchatbot.rest.Client
import ru.sergei.komarov.labs.androidchatbot.services.DatabaseService
import java.util.*

class CommonUtils {
    companion object {
        var DEFAULT_LOCALE: Locale? = Locale("ru")
        var IS_SYSTEM_MESSAGE: Boolean = false

        fun revertIsSystemMessage() {
            IS_SYSTEM_MESSAGE = !IS_SYSTEM_MESSAGE
        }

        fun isVerticalOrientation(resources: Resources): Boolean {
            return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        }

        fun changeLocale(locale: Locale) {
            DEFAULT_LOCALE = locale
        }

        fun <T : AppCompatActivity> updateActivity(activity: T, baseContext: Context) {
            //locale configuring
            val config = Configuration()
            config.setLocale(DEFAULT_LOCALE)
            baseContext.resources.updateConfiguration(
                config,
                baseContext.resources.displayMetrics
            )

            //title configuring
            val titleId: Int =
                when (activity) {
                    is SettingsActivity -> R.string.settings_name
                    is ChatActivity -> R.string.chat_name
                    else -> R.string.app_name
                }
            activity.setTitle(titleId)
        }

        fun getDatabaseInstance(context: Context?): SQLiteDatabase {
            val dbHelper = DatabaseService(context)

            val db =
                try {
                    dbHelper.writableDatabase
                } catch (ex: SQLiteException) {
                    ex.printStackTrace()
                    dbHelper.readableDatabase
                }
            return db
        }

        fun saveMessageToSystem(
            messageObj: Message,
            messagesDAOImpl: MessagesDAOImpl
        ) {
            Thread {
                //save to server
                val savedMessageObj: Message = Client.saveMessage(messageObj)

                //save to local
                messagesDAOImpl.insert(savedMessageObj)

                //save to dummy content
                DummyContent.addItem(
                    DummyContent.createDummyItemByData(
                        savedMessageObj.userId == "SYSTEM",
                        savedMessageObj.message
                    )
                )
            }.start()
        }
    }
}