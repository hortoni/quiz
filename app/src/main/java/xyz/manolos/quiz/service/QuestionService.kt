package xyz.manolos.quiz.service

import io.reactivex.Single
import retrofit2.http.GET
import xyz.manolos.quiz.model.Question

interface QuestionService {

    @GET("question")
    fun fetch(): Single<Question>

    
}