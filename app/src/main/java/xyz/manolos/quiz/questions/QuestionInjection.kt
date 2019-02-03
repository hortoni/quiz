package xyz.manolos.quiz.questions

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [QuestionModule::class])
interface QuestionComponent {

    fun inject(activity: QuestionActivity)
}

@Module
class QuestionModule(private val questionView: QuestionView) {

    @Provides
    fun provideQuestionView() = questionView
}