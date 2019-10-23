package ru.sergei.komarov.labs.androidchatbot

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

import ru.sergei.komarov.labs.androidchatbot.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.chat_system_message.view.*
import kotlinx.android.synthetic.main.item_list.*
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils
import java.util.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ ItemDetailActivity ] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ChatActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //locale configuring
        CommonUtils.updateActivity(this, baseContext)

        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)

        //enable "turn back" button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView(item_list)
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
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, twoPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ChatActivity,
        private val values: List<DummyContent.DummyItem>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                /*val item = v.tag as DummyContent.DummyItem
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }*/
                println("Click on message")
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val random = Random()
            val nextBoolean = random.nextBoolean()

            val view =
                if (nextBoolean)
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.chat_system_message, parent, false)
                else
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.chat_user_message, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            //holder.idView.text = item.id
            holder.contentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            //val idView: TextView = view.id_text
            val contentView: TextView = view.message_text
        }
    }
}
