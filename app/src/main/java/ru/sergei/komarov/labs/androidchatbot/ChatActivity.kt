package ru.sergei.komarov.labs.androidchatbot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_list.*
import ru.sergei.komarov.labs.androidchatbot.adapters.ChatViewAdapter
import ru.sergei.komarov.labs.androidchatbot.dao.MessagesDAOImpl
import ru.sergei.komarov.labs.androidchatbot.dummy.DummyContent
import ru.sergei.komarov.labs.androidchatbot.listeners.MessageInputFocusHandler
import ru.sergei.komarov.labs.androidchatbot.listeners.SendMessageButtonClickHandler
import ru.sergei.komarov.labs.androidchatbot.models.Message
import ru.sergei.komarov.labs.androidchatbot.rest.Client
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ ItemDetailActivity ] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //locale configuring
        CommonUtils.updateActivity(this, baseContext)

        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)

        //enable "turn back" button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //loading messages
        val dbInstance = CommonUtils.getDatabaseInstance(this)
        val dao = MessagesDAOImpl(dbInstance)

        val animatedLoader = findViewById<ProgressBar>(R.id.animated_loader)
        val currentRequestId = Client.loadMessages()
        var loadedData: MutableList<Message>?
        Thread(Runnable {
            this@ChatActivity.runOnUiThread {
                this@ChatActivity.item_list.visibility = View.INVISIBLE
            }
            animatedLoader.visibility = View.VISIBLE

            loadedData = Client.getLoadedMessages(currentRequestId)
            while (loadedData == null) {
                Thread.sleep(1000)
                loadedData = Client.getLoadedMessages(currentRequestId)
            }

            //save to database
            for (message in loadedData!!) {
                dao.insert(message)
            }

            //render
            this@ChatActivity.runOnUiThread {
                this@ChatActivity.item_list.visibility = View.VISIBLE
            }
            animatedLoader.visibility = View.INVISIBLE
        }).start()

        //create interface
        setupRecyclerView(item_list)

        val messageInputLayout = findViewById<TextInputLayout>(R.id.message_input_layout)
        val messageInputView = findViewById<TextInputEditText>(R.id.message_input)
        val messageInputHintText = resources.getString(R.string.input_message_hint)
        messageInputView.onFocusChangeListener =
            MessageInputFocusHandler(messageInputLayout, messageInputHintText)

        val sendMessageButton = findViewById<ImageButton>(R.id.send_button)
        sendMessageButton.setOnClickListener(
            SendMessageButtonClickHandler(
                this,
                messageInputView,
                item_list,
                messageInputHintText
            )
        )
    }

    //------------- OPTIONS MENU

    //TODO move it to common class
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //TODO create common handler or move to common class
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val changePageIntent = Intent(this, SettingsActivity::class.java)
                startActivity(changePageIntent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //--------------------------

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val dbInstance = CommonUtils.getDatabaseInstance(this)
        val loadedMessages = MessagesDAOImpl(dbInstance).getAll()

        for (loadedMessage in loadedMessages) {
            DummyContent.addItem(
                DummyContent.DummyItem(
                    loadedMessage.userId == "SYSTEM",
                    loadedMessage.message,
                    ""
                )
            )
        }

        recyclerView.adapter = ChatViewAdapter(this, DummyContent.ITEMS)
    }

}
