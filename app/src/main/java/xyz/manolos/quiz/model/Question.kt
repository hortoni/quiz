package xyz.manolos.quiz.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Question (
    val id: String,
    val statement: String,
    val options: List<String> = emptyList()
) : Parcelable