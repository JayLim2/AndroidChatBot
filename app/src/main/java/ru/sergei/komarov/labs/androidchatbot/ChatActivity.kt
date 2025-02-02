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
import ru.sergei.komarov.labs.androidchatbot.comparators.MessagesComparator
import ru.sergei.komarov.labs.androidchatbot.dao.MessagesDAOImpl
import ru.sergei.komarov.labs.androidchatbot.dummy.ChatContent
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

        //action bar
        setSupportActionBar(toolbar)
        //enable "turn back" button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //loading messages
        val dbInstance = CommonUtils.getDatabaseInstance(this)
        val dao = MessagesDAOImpl(dbInstance)

        val animatedLoader = findViewById<ProgressBar>(R.id.animated_loader)
        var loadedData: MutableList<Message>?
        Thread(Runnable {
            this@ChatActivity.runOnUiThread {
                this@ChatActivity.item_list.visibility = View.INVISIBLE
            }
            animatedLoader.visibility = View.VISIBLE

            //clear dummy content assigned with item list on UI
            ChatContent.ITEMS.clear()

            //load data from server
            loadedData = Client.loadMessages()

            //save to database
            for (message in loadedData!!) {
                //insert to local DB
                dao.insert(message)
            }

            //render
            this@ChatActivity.runOnUiThread {
                //initial synchronizing UI with local DB
                setupRecyclerView(item_list)
                //show messages list
                this@ChatActivity.item_list.visibility = View.VISIBLE
                //refresh UI
                item_list.adapter!!.notifyItemInserted(loadedData!!.size)
            }
            animatedLoader.visibility = View.INVISIBLE
        }).start()

        //create interface
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
            .sortedWith(MessagesComparator())

        for (loadedMessage in loadedMessages) {
            ChatContent.addItem(
                ChatContent.ChatItem(
                    loadedMessage.userId == "SYSTEM",
                    loadedMessage.message,
                    ""
                )
            )
        }

        recyclerView.adapter = ChatViewAdapter(this, ChatContent.ITEMS)
    }

}
