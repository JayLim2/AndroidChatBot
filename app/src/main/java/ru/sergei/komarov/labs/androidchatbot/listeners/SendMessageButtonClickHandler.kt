package ru.sergei.komarov.labs.androidchatbot.listeners

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ru.sergei.komarov.labs.androidchatbot.ChatActivity
import ru.sergei.komarov.labs.androidchatbot.dao.MessagesDAOImpl
import ru.sergei.komarov.labs.androidchatbot.dummy.ChatContent
import ru.sergei.komarov.labs.androidchatbot.models.Message
import ru.sergei.komarov.labs.androidchatbot.utils.BotResponseGenerator
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils
import java.time.LocalDateTime

class SendMessageButtonClickHandler : View.OnClickListener {
    private val chatActivityContext: ChatActivity
    private val messageInputField: TextInputEditText
    private val recyclerView: RecyclerView
    private val hintText: String

    private val messagesDAOImpl: MessagesDAOImpl

    constructor(
        chatActivityContext: ChatActivity,
        messageInputField: TextInputEditText,
        recyclerView: RecyclerView,
        hintText: String
    ) {
        this.chatActivityContext = chatActivityContext
        this.messageInputField = messageInputField
        this.recyclerView = recyclerView
        this.hintText = hintText

        messagesDAOImpl = MessagesDAOImpl(CommonUtils.getDatabaseInstance(chatActivityContext))
    }

    private fun putMessage(isSystemMessage: Boolean, message: String) {
        //create client message object
        val messageObj = Message(
            message,
            if (isSystemMessage) "SYSTEM" else "USER",
            LocalDateTime.now()
        )

        CommonUtils.saveMessageToSystem(
            messageObj,
            messagesDAOImpl
        )
    }


    override fun onClick(v: View?) {
        Thread {
            val message = messageInputField.text.toString()
            putMessage(false, message)

            val botMessage = BotResponseGenerator.getAnswer(message)
            putMessage(true, botMessage)

            chatActivityContext.runOnUiThread {
                var k = ChatContent.ITEMS.size - 1
                while (k <= ChatContent.ITEMS.size) {
                    recyclerView.adapter!!.notifyItemInserted(k++)
                }

                messageInputField.hint = hintText
                messageInputField.text = null
            }
        }.start()
    }
}