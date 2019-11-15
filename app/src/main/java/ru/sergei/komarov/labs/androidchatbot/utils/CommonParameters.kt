package ru.sergei.komarov.labs.androidchatbot.utils

class CommonParameters {
    companion object {
        var authentication: String? = null
        var authenticationResponse: String? = null

        fun isAuthenticationResponseReceived(): Boolean {
            return authenticationResponse != null
        }

        fun isAuthenticated(): Boolean {
            return authentication != null
        }
    }
}