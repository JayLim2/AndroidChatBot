package ru.sergei.komarov.labs.androidchatbot.listeners

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class WriteButtonClickHandler<T : AppCompatActivity> : View.OnClickListener {
    var context: T

    constructor(context: T) {
        this.context = context
    }

    override fun onClick(view: View) {
        Snackbar.make(
            view,
            "You can't create new chat.",
            Snackbar.LENGTH_LONG
        ).setAction("Action", null).show()
    }
}