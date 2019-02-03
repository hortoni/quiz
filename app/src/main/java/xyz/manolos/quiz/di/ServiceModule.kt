package xyz.manolos.quiz.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.manolos.quiz.service.QuestionService
import javax.inject.Singleton

@Module
object ServiceModule {

    @Provides
    @JvmStatic @Singleton
    fun provideQuestionService(): QuestionService = Retrofit
        .Builder()
        .baseUrl("http://34.73.190.231:8080/")
        .client(
            OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()
        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(QuestionService::class.java)

}