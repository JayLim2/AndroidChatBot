package ru.sergei.komarov.labs.androidchatbot.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import ru.sergei.komarov.labs.androidchatbot.R
import java.util.*

class CommonUtils {
    companion object {
        var DEFAULT_LOCALE:Locale? = Locale("ru")

        fun isVerticalOrientation(resources: Resources): Boolean {
            return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        }

        fun changeLocale(locale: Locale) {
            DEFAULT_LOCALE = locale
        }

        fun <T : AppCompatActivity> updateActivity(activity: T, baseContext: Context) {
            //locale configuring
            val config = Configuration()
            config.setLocale(DEFAULT_LOCALE)
            baseContext.resources.updateConfiguration(
                config,
                baseContext.resources.displayMetrics
            )
            activity.setTitle(R.string.app_name)
        }
    }
}