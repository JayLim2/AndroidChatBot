package ru.sergei.komarov.labs.androidchatbot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import ru.sergei.komarov.labs.androidchatbot.listeners.WriteButtonClickHandler
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils

class MainActivity : AppCompatActivity() {

    private val onClickListener: View.OnClickListener = WriteButtonClickHandler(this)

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //locale configuring
        CommonUtils.updateActivity(this, baseContext)

        val layoutId =
            if (CommonUtils.isVerticalOrientation(resources))
                R.layout.activity_main
            else
                R.layout.activity_main_horizontal
        setContentView(layoutId)
        setSupportActionBar(toolbar)

        textView = findViewById(R.id.hello_world)

        val writeButton = findViewById<FloatingActionButton>(R.id.fab)
        writeButton.setOnClickListener(onClickListener)
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
