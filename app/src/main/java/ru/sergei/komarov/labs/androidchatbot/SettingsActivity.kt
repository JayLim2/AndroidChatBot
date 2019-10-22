package ru.sergei.komarov.labs.androidchatbot

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils

class SettingsActivity : AppCompatActivity() {

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId =
            if (CommonUtils.isVerticalOrientation(resources))
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

        textView = findViewById(R.id.hello_world)
        val myButton = findViewById<FloatingActionButton>(R.id.fab)
        myButton.setOnClickListener(onClickListener)
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
}
