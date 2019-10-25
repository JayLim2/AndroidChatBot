package ru.sergei.komarov.labs.androidchatbot.listeners

import android.view.View
import com.google.android.material.textfield.TextInputLayout

class MessageInputFocusHandler : View.OnFocusChangeListener {
    var layout:TextInputLayout
    var hintText:String

    constructor(layout: TextInputLayout, hintText: String) {
        this.layout = layout
        this.hintText = hintText
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        layout.hint = if (!hasFocus) hintText else null
        print(layout.hint)
    }
}