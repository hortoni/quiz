package xyz.manolos.quiz.service

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import xyz.manolos.quiz.model.Answer
import xyz.manolos.quiz.model.AnswerResult
import xyz.manolos.quiz.model.Question

interface QuestionService {

    @GET("question")
    fun fetch(): Single<Question>

    @POST("answer")
    fun sendAnswer(@Query("questionId") questionId : String, @Body answer: Answer): Single<AnswerResult>
    
}