package ru.sergei.komarov.labs.androidchatbot.listeners

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.sergei.komarov.labs.androidchatbot.R
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils
import java.util.*

class LocaleButtonClickHandler <T : AppCompatActivity> : View.OnClickListener {
    var context: T
    var id: Int

    constructor(context: T, id: Int) {
        this.context = context
        this.id = id
    }

    override fun onClick(v: View?) {
        val locale: Locale =
            when (id) {
                R.id.en_locale_button -> Locale("en")
                R.id.ru_locale_button -> Locale("ru")
                else -> Locale("en")
            }
        CommonUtils.changeLocale(locale)
        context.recreate()
    }
}