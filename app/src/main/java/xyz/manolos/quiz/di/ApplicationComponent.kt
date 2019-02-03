package xyz.manolos.quiz.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import xyz.manolos.quiz.questions.QuestionComponent
import xyz.manolos.quiz.questions.QuestionModule
import javax.inject.Singleton

@Component(modules = [ServiceModule::class])
@Singleton
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun build(): ApplicationComponent
    }

    fun plus(questionModule: QuestionModule): QuestionComponent

}