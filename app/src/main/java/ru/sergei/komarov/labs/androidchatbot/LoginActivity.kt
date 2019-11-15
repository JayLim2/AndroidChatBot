package ru.sergei.komarov.labs.androidchatbot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import ru.sergei.komarov.labs.androidchatbot.listeners.LoginButtonClickHandler
import ru.sergei.komarov.labs.androidchatbot.listeners.WriteButtonClickHandler
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils

class LoginActivity : AppCompatActivity() {

    private val onWriteButtonClickListener: View.OnClickListener = WriteButtonClickHandler(this)
    private val onLoginButtonClickListener: View.OnClickListener = LoginButtonClickHandler(this)

    lateinit var loginInput: TextInputEditText
    lateinit var passwordInput: TextInputEditText
    lateinit var loginButton: Button
    lateinit var writeButton: FloatingActionButton
    lateinit var errorMessage: TextView
    lateinit var animatedLoader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //locale configuring
        CommonUtils.updateActivity(this, baseContext)

        //set content view
        setContentView(R.layout.activity_login)

        //fetch controls
        animatedLoader = findViewById(R.id.animated_loader)
        errorMessage = findViewById(R.id.invalid_creds_error_msg)
        loginInput = findViewById(R.id.login_input)
        passwordInput = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_button)
        writeButton = findViewById(R.id.fab)

        loginButton.setOnClickListener(onLoginButtonClickListener)
        writeButton.setOnClickListener(onWriteButtonClickListener)
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
