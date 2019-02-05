package xyz.manolos.quiz.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnswerResult (
    val result: Boolean
)