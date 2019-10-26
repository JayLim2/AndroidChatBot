package ru.sergei.komarov.labs.androidchatbot.utils

import java.util.*
import kotlin.collections.ArrayList

/*
Utils for generation of dummy responses from "ChatBot"
 */
class BotResponseGenerator {

    companion object {

        private const val DEFAULT_ANSWER: String = "Sorry, I have a headache."
        private val DEFAULT_GREETINGS: List<String> = listOf(
            "hello", "hi", "здравствуйте", "привет"
        )
        private val DEFAULT_PARTINGS: List<String> = listOf(
            "goodbye", "bye", "до свидания", "пока"
        )
        private val DEFAULT_MAIN_PHRASES: List<String> = listOf(
            "good weather today", "I like KFC wings",
            "хорошая погодка сегодня", "я люблю крылышки из KFC"
        )

        fun getAnswer(inputMessage: String):String {

            var answer = getGreeting(inputMessage)
            if(answer == null) {
                answer = getMainConversation(inputMessage)
            }
            if(answer == null) {
                answer = getParting(inputMessage)
            }

            return answer ?: DEFAULT_ANSWER
        }

        private fun getGreeting(inputMessage: String):String? {

            return getDefaultTypedAnswer(inputMessage, DEFAULT_GREETINGS)
        }

        private fun getMainConversation(inputMessage: String):String? {

            return getDefaultTypedAnswer(inputMessage, DEFAULT_MAIN_PHRASES)
        }

        private fun getParting(inputMessage: String):String? {

            return getDefaultTypedAnswer(inputMessage, DEFAULT_PARTINGS)
        }

        private fun getDefaultTypedAnswer(inputMessage: String, defaultValues: List<String>):String? {
            val handledInputMessage = inputMessage.trim().toLowerCase(Locale.getDefault())

            val conditionResults = ArrayList<Boolean>()
            for (greeting in defaultValues) {
                conditionResults.add(handledInputMessage == greeting || handledInputMessage.contains(greeting))
            }
            val result = conditionResults.stream().reduce(Boolean::or).orElse(false)

            if(result) {
                val random = Random()
                return defaultValues[random.nextInt(defaultValues.size)]
            }

            return null
        }

    }

}