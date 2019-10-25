package ru.sergei.komarov.labs.androidchatbot.listeners

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ru.sergei.komarov.labs.androidchatbot.ChatActivity
import ru.sergei.komarov.labs.androidchatbot.dao.MessagesDAOImpl
import ru.sergei.komarov.labs.androidchatbot.dummy.DummyContent
import ru.sergei.komarov.labs.androidchatbot.models.Message
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils
import java.time.LocalDateTime
import java.util.*

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

    override fun onClick(v: View?) {
        val message = messageInputField.text.toString()
        DummyContent.addItem(
            DummyContent.createDummyItemByData(
                CommonUtils.IS_SYSTEM_MESSAGE,
                message
            )
        )

        messagesDAOImpl.insert(Message(
            message,
            Random().nextInt(1000),
            LocalDateTime.now()
        ))

        val totalItemsCount = DummyContent.ITEMS.count()
        recyclerView.adapter!!.notifyItemInserted(totalItemsCount)

        CommonUtils.revertIsSystemMessage()

        messageInputField.hint = hintText
        messageInputField.text = null
    }
}