package ru.sergei.komarov.labs.androidchatbot.listeners

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ru.sergei.komarov.labs.androidchatbot.ChatActivity
import ru.sergei.komarov.labs.androidchatbot.dao.MessagesDAOImpl
import ru.sergei.komarov.labs.androidchatbot.dummy.DummyContent
import ru.sergei.komarov.labs.androidchatbot.models.Message
import ru.sergei.komarov.labs.androidchatbot.rest.Client
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

    private fun putMessage(id: Int, isSystemMessage: Boolean, message: String) {
        //save to dummy content
        DummyContent.addItem(
            DummyContent.createDummyItemByData(
                isSystemMessage,
                message
            )
        )

        val messageObj = Message(
            id,
            message,
            if (isSystemMessage) "SYSTEM" else "USER",
            LocalDateTime.now()
        )

        //save to local
        messagesDAOImpl.insert(messageObj)

        //save to server
        Client.saveMessage(messageObj)
    }

    private var k: Int = 1

    override fun onClick(v: View?) {
        val message = messageInputField.text.toString()
        putMessage(k++, false, message)

        val botMessage = BotResponseGenerator.getAnswer(message)
        putMessage(k++, true, botMessage)

        val totalItemsCount = DummyContent.ITEMS.count()
        recyclerView.adapter!!.notifyItemInserted(totalItemsCount)

        messageInputField.hint = hintText
        messageInputField.text = null
    }
}