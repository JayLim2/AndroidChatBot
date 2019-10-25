package ru.sergei.komarov.labs.androidchatbot.listeners

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ru.sergei.komarov.labs.androidchatbot.adapters.ChatViewAdapter
import ru.sergei.komarov.labs.androidchatbot.dummy.DummyContent
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils
import java.util.*

class SendMessageButtonClickHandler : View.OnClickListener {
    private val messageInputField: TextInputEditText
    private val recyclerView: RecyclerView
    private val hintText: String

    constructor(
        messageInputField: TextInputEditText,
        recyclerView: RecyclerView,
        hintText: String
    ) {
        this.messageInputField = messageInputField
        this.recyclerView = recyclerView
        this.hintText = hintText
    }

    override fun onClick(v: View?) {
        val message = messageInputField.text.toString()
        DummyContent.addItem(
            DummyContent.createDummyItemByData(
                CommonUtils.IS_SYSTEM_MESSAGE,
                message
            )
        )

        val totalItemsCount = DummyContent.ITEMS.count()
        recyclerView.adapter!!.notifyItemInserted(totalItemsCount)

        CommonUtils.revertIsSystemMessage()

        messageInputField.hint = hintText
        messageInputField.text = null
    }
}