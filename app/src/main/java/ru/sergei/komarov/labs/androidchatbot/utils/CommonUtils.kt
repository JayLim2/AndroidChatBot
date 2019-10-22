package ru.sergei.komarov.labs.androidchatbot.utils

import android.content.res.Configuration
import android.content.res.Resources

class CommonUtils {
    companion object {
        fun isVerticalOrientation(resources: Resources): Boolean {
            return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        }
    }
}