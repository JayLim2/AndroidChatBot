package ru.sergei.komarov.labs.androidchatbot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import ru.sergei.komarov.labs.androidchatbot.listeners.LocaleButtonClickHandler
import ru.sergei.komarov.labs.androidchatbot.listeners.WriteButtonClickHandler
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils

class SettingsActivity : AppCompatActivity() {

    private val onClickListener: View.OnClickListener = WriteButtonClickHandler(this)

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

        //enable "turn back" button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val writeButton = findViewById<FloatingActionButton>(R.id.write_button)
        writeButton.setOnClickListener(onClickListener)

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

        enLocaleButton.setOnClickListener(LocaleButtonClickHandler(this, R.id.en_locale_button))
        ruLocaleButton.setOnClickListener(LocaleButtonClickHandler(this, R.id.ru_locale_button))
    }

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
}
