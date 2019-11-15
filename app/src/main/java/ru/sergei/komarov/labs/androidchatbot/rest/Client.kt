package ru.sergei.komarov.labs.androidchatbot.rest

import com.google.gson.JsonObject
import okhttp3.*
import ru.sergei.komarov.labs.androidchatbot.models.Message
import ru.sergei.komarov.labs.androidchatbot.utils.CommonParameters
import ru.sergei.komarov.labs.androidchatbot.utils.GsonConverter
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Client {

    companion object {
        val DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME
        private val OK_HTTP_CLIENT = OkHttpClient()

        private var currentRequest: Int = 1
        private val loadedDate: MutableMap<Int, MutableList<Message>> = TreeMap()

        private const val HOST = "https://androidchatbotserver.herokuapp.com"
        private const val MESSAGES_BASE_LINK = "$HOST/api/messages/"
        private const val LOAD_ALL_BASE_LINK = "${MESSAGES_BASE_LINK}get/all"
        private const val SAVE_BASE_LINK = "${MESSAGES_BASE_LINK}save"
        private const val USERS_BASE_LINK = "$HOST/api/users/"
        private const val LOGIN_LINK = "${USERS_BASE_LINK}validate/credentials"

        fun getLoadedMessages(requestId: Int): MutableList<Message>? {
            return loadedDate[requestId]
        }

        private fun toNextRequestId() {
            //increment status number
            ++currentRequest
            //reset current loadRequest id if limit exceed
            if (currentRequest == Integer.MAX_VALUE) {
                currentRequest = 1
            }
        }

        fun loadMessages(): Int {
            //move to next loadRequest id
            toNextRequestId()
            //send loadRequest
            loadRequest(LOAD_ALL_BASE_LINK)
            //return response
            return currentRequest
        }

        fun saveMessage(message: Message) {

            saveRequest(SAVE_BASE_LINK, message)
        }

        fun login(login: String, password: String): Boolean {

            CommonParameters.authenticationResponse = null
            CommonParameters.authentication = null

            return loginRequest(login, password)
        }

        private fun loginRequest(login: String, password: String): Boolean {
            val params = FormBody.Builder()
                .add("login", login)
                .add("password", password)
                .build()

            val request = Request.Builder()
                .url(LOGIN_LINK)
                .post(params)
                .build()

            OK_HTTP_CLIENT.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("#### LOGIN REQUEST FAILED ####")
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("#### LOGIN RESPONSE ####")

                    val authenticationResponse = response.body()?.string()

                    println(authenticationResponse + " <- response")

                    CommonParameters.authenticationResponse = authenticationResponse
                    if (authenticationResponse == "true") {
                        CommonParameters.authentication = "$login : $password"
                    }
                }
            })

            //FIXME return actual value!
            return true
        }

        private fun saveRequest(url: String, message: Message) {
            val jsonMessage = JsonObject()
            jsonMessage.addProperty("message", message.message)
            jsonMessage.addProperty("date", DATE_TIME_FORMATTER.format(message.date))
            jsonMessage.addProperty("userId", message.userId)

            val requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                GsonConverter.toString(jsonMessage)
            )

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            OK_HTTP_CLIENT.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("#### FAILED ####")
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("#### RESPONSE ####")
                    println(response)
                }
            })
        }

        private fun loadRequest(url: String) {
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

                    val responseStr = response.body()?.string()
                    val jsonArray = responseStr?.let {
                        GsonConverter.parseToJsonArray(it)
                    }

                    //map "json" to "object"
                    val messages: MutableList<Message> = ArrayList(20)
                    if (jsonArray != null) {
                        for (jsonMessage in jsonArray) {
                            try {
                                val messageJsonObject = jsonMessage.asJsonObject
                                val message = Message(
                                    messageJsonObject.get("id").asInt,
                                    messageJsonObject.get("message").asString,
                                    messageJsonObject.get("userId").asString,
                                    LocalDateTime.parse(
                                        messageJsonObject.get("date").asString,
                                        DATE_TIME_FORMATTER
                                    )
                                )
                                messages.add(message)
                            } catch (e: Exception) {
                                println("FAILED ON: \n")
                                GsonConverter.println(jsonMessage)
                                e.printStackTrace()
                            }
                        }
                    }

                    //set status as "data loaded"
                    loadedDate[currentRequest] = messages
                }
            })
        }
    }

}