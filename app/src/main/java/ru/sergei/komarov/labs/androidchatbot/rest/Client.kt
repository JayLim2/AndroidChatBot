package ru.sergei.komarov.labs.androidchatbot.rest

import okhttp3.*
import ru.sergei.komarov.labs.androidchatbot.models.Message
import ru.sergei.komarov.labs.androidchatbot.utils.GsonConverter
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Client {

    companion object {
        val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        private val OK_HTTP_CLIENT = OkHttpClient()

        private var currentRequest: Int = 1
        private val loadedDate: MutableMap<Int, MutableList<Message>> = TreeMap()

        private const val HOST = "https://androidchatbotserver.herokuapp.com"
        private const val BASE_LINK = "$HOST/api/messages/"
        private const val LOAD_BASE_LINK = "${BASE_LINK}get/"
        private const val LOAD_ALL_BASE_LINK = "${BASE_LINK}get/all"
        private const val SAVE_BASE_LINK = "${BASE_LINK}save/"
        private const val SAVE_ALL_LINK = "${BASE_LINK}save/all/"

        fun getLoadedMessages(requestId: Int): MutableList<Message>? {
            return loadedDate[requestId]
        }

        private fun toNextRequestId() {
            //increment status number
            ++currentRequest
            //reset current request id if limit exceed
            if (currentRequest == Integer.MAX_VALUE) {
                currentRequest = 1
            }
        }

        fun loadMessages(): Int {
            //move to next request id
            toNextRequestId()
            //send request
            request(LOAD_ALL_BASE_LINK)
            //return response
            return currentRequest
        }

        fun loadMessages(userId: String): List<Message> {

            return ArrayList()
        }

        fun saveMessage(message: Message) {

            saveRequest(SAVE_BASE_LINK)
        }

        private fun saveRequest(url: String) {
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
                }
            })
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