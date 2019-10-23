package ru.sergei.komarov.labs.androidchatbot

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils
import java.util.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get orientation
        val orientationIsVertical = CommonUtils.isVerticalOrientation(resources)

        //locale configuring
        CommonUtils.updateActivity(this, baseContext)

        val layoutId =
            if (orientationIsVertical)
                R.layout.activity_settings
            else
                R.layout.activity_settings_horizontal
        setContentView(layoutId)
        setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Replace with your own action",
                Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        }*/

        val myButton = findViewById<FloatingActionButton>(R.id.fab)
        myButton.setOnClickListener(onClickListener)

        val enLocaleButton =
            if (orientationIsVertical)
                findViewById<ImageButton>(R.id.en_locale_button)
            else
                findViewById<Button>(R.id.en_locale_button)
        val ruLocaleButton =
            if (orientationIsVertical)
                findViewById<ImageButton>(R.id.ru_locale_button)
            else
                findViewById<Button>(R.id.ru_locale_button)

        enLocaleButton.setOnClickListener(LocaleClickHandler(this, R.id.en_locale_button))
        ruLocaleButton.setOnClickListener(LocaleClickHandler(this, R.id.ru_locale_button))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val onClickListener: View.OnClickListener = ButtonClickHandler(this)

    class ButtonClickHandler : View.OnClickListener {
        var context: SettingsActivity

        constructor(context: SettingsActivity) {
            this.context = context
        }

        override fun onClick(v: View?) {
            val changePageIntent = Intent(context, MainActivity::class.java)
            context.startActivity(changePageIntent)
        }
    }

    class LocaleClickHandler : View.OnClickListener {
        var context: SettingsActivity
        var id: Int

        constructor(context: SettingsActivity, id: Int) {
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
}
