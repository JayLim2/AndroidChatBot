package ru.sergei.komarov.labs.androidchatbot

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import ru.sergei.komarov.labs.androidchatbot.listeners.LoginButtonClickHandler
import ru.sergei.komarov.labs.androidchatbot.utils.CommonUtils

class LoginActivity : AppCompatActivity() {

    private val onLoginButtonClickListener: View.OnClickListener = LoginButtonClickHandler(this)

    lateinit var loginInput: TextInputEditText
    lateinit var passwordInput: TextInputEditText
    lateinit var loginButton: Button
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

        loginButton.setOnClickListener(onLoginButtonClickListener)
    }

}
