package xyz.manolos.quiz.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question (
    val id: String,
    val statement: String,
    val options: List<String> = emptyList()
)