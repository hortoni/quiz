package xyz.manolos.quiz

import android.app.Activity
import android.app.Application
import xyz.manolos.quiz.di.DaggerApplicationComponent

class QuizApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationContext(applicationContext)
            .build()
    }
}

val Activity.injector get() = (application as QuizApplication).component