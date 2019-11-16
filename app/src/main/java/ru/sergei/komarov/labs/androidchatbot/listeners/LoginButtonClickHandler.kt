package ru.sergei.komarov.labs.androidchatbot.listeners

import android.content.Intent
import android.view.View
import ru.sergei.komarov.labs.androidchatbot.LoginActivity
import ru.sergei.komarov.labs.androidchatbot.MainActivity
import ru.sergei.komarov.labs.androidchatbot.rest.Client
import ru.sergei.komarov.labs.androidchatbot.utils.CommonParameters

class LoginButtonClickHandler : View.OnClickListener {

    var context: LoginActivity

    constructor(context: LoginActivity) {
        this.context = context
    }

    override fun onClick(v: View?) {

        Thread(Runnable {
            val login = context.loginInput.text.toString()
            val password = context.passwordInput.text.toString()

            context.runOnUiThread {
                context.animatedLoader.visibility = View.VISIBLE //enable loader
                context.errorMessage.visibility = View.INVISIBLE //hide error message
            }

            //send login request and waiting
            Client.login(login, password)
            while (!CommonParameters.isAuthenticationResponseReceived()) {
                Thread.sleep(100)
            }

            context.runOnUiThread {
                context.animatedLoader.visibility = View.INVISIBLE //disable loader

                if (!CommonParameters.isAuthenticated()) {
                    context.errorMessage.visibility = View.VISIBLE //show error message
                } else {
                    //move to MainActivity
                    val changePageIntent = Intent(context, MainActivity::class.java)
                    context.startActivity(changePageIntent)
                }
            }
        }).start()
    }

}