package ru.sergei.komarov.labs.androidchatbot.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chat_message.view.*
import ru.sergei.komarov.labs.androidchatbot.ChatActivity
import ru.sergei.komarov.labs.androidchatbot.R
import ru.sergei.komarov.labs.androidchatbot.dummy.ChatContent

class ChatViewAdapter(
    private val parentActivity: ChatActivity,
    private val values: List<ChatContent.ChatItem>
) : RecyclerView.Adapter<ChatViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            /*val item = v.tag as ChatContent.ChatItem
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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_message, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.content

        val layout = holder.itemView as LinearLayout
        val isUserMessage = !item.isSystemMessage
        if (isUserMessage) {
            layout.gravity = Gravity.END

            val messageContainer = layout.message_text
            messageContainer.background =
                parentActivity.resources.getDrawable(R.drawable.user_message_shape)
            messageContainer.setTextColor(parentActivity.resources.getColor(R.color.colorWhite))

            val avatarLeft = layout.avatar_left
            avatarLeft.visibility = View.INVISIBLE

            val avatarRight = layout.avatar_right
            avatarRight.visibility = View.VISIBLE
        }

        with(layout) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.message_text
    }
}