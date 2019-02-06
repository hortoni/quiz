package xyz.manolos.quiz.home

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {

    fun inject(activity: HomeActivity)
}

@Module
class HomeModule(private val homeView: HomeView) {

    @Provides
    fun provideHomeView() = homeView
}