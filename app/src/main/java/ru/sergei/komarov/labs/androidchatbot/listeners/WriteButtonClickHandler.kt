package ru.sergei.komarov.labs.androidchatbot.listeners

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.sergei.komarov.labs.androidchatbot.ChatActivity

class WriteButtonClickHandler<T : AppCompatActivity> : View.OnClickListener {
    var context: T

    constructor(context: T) {
        this.context = context
    }

    override fun onClick(view: View) {
        val intent = Intent(context, ChatActivity::class.java)
        context.startActivity(intent)

        if(false) {
            Snackbar.make(
                view,
                "You can't create new chat.",
                Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        }
    }
}