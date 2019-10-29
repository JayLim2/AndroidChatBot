package ru.sergei.komarov.labs.androidchatbot.utils

import com.google.gson.*

class GsonConverter {

    companion object {

        private val GSON = GsonBuilder().setPrettyPrinting().create()
        private val PARSER = JsonParser()

        fun parseToJsonObject(value: String): JsonObject {
            return parse(value).asJsonObject
        }

        fun parseToJsonArray(value: String): JsonArray {
            return parse(value).asJsonArray
        }

        private fun parse(value: String): JsonElement {
            return PARSER.parse(value)
        }

        fun println(json: JsonElement) {
            println(GSON.toJson(json))
        }
    }

}