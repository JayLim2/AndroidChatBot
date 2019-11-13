package ru.sergei.komarov.labs.androidchatbot.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken

class GsonConverter {

    companion object {

        private val GSON = GsonBuilder().setPrettyPrinting().create()
        private val PARSER = JsonParser()

        fun <T> jsonArrayToList(jsonARray: JsonArray): List<T> {
            val type = object : TypeToken<List<T>>() {}.type
            val list: List<T> = GSON.fromJson(
                jsonARray,
                type
            )
            return list
        }

        fun parseToJsonObject(value: String): JsonObject {
            return parse(value).asJsonObject
        }

        fun parseToJsonArray(value: String): JsonArray {
            return parse(value).asJsonArray
        }

        private fun parse(value: String): JsonElement {
            return PARSER.parse(value)
        }

        fun toString(json: JsonElement): String {
            return GSON.toJson(json)
        }

        fun println(json: JsonElement) {
            println(GSON.toJson(json))
        }
    }

}