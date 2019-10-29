package ru.sergei.komarov.labs.androidchatbot.rest

import okhttp3.*
import ru.sergei.komarov.labs.androidchatbot.models.Message
import ru.sergei.komarov.labs.androidchatbot.utils.GsonConverter
import java.io.IOException

class Client {

    companion object {
        private val OK_HTTP_CLIENT = OkHttpClient()

        private const val HOST = "https://androidchatbotserver.herokuapp.com"
        private const val BASE_LINK = "$HOST/api/messages/"
        private const val LOAD_BASE_LINK = "${BASE_LINK}get/"
        private const val LOAD_ALL_BASE_LINK = "${BASE_LINK}get/all"
        private const val SAVE_BASE_LINK = "${BASE_LINK}save/"
        private const val SAVE_ALL_LINK = "${BASE_LINK}save/all/"

        fun loadMessages(): List<Message> {

            request(LOAD_ALL_BASE_LINK)

            return ArrayList()
        }

        fun loadMessages(userId: String): List<Message> {

            return ArrayList()
        }

        fun saveMessages(userId: String, messages: List<Message>): List<Int> {

            return ArrayList()
        }

        private fun request(url: String) {
            val request = Request.Builder()
                .url(url)
                .build()

            OK_HTTP_CLIENT.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("#### FAILED ####")
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("#### RESPONSE ####")

                    //TODO handle response here
                    val responseStr = response.body()?.string()
                    val jsonArray = responseStr?.let {
                        GsonConverter.parseToJsonArray(it)
                    }
                    jsonArray?.let {
                        GsonConverter.println(it)
                    }
                }
            })
        }
    }

}