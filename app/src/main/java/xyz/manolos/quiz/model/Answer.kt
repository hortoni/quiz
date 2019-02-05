package xyz.manolos.quiz.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Answer (
    val answer: String
)