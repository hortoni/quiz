package xyz.manolos.quiz.result

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [ResultModule::class])
interface ResultComponent {

    fun inject(activity: ResultActivity)
}

@Module
class ResultModule(private val resultView: ResultView) {

    @Provides
    fun provideResultView() = resultView
}