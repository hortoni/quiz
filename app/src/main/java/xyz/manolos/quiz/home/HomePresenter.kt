package xyz.manolos.quiz.home

import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val view: HomeView
) {

    fun goToQuiz(boolean: Boolean, errorMessage: String){
        if (boolean)
            view.showError(errorMessage)
        else
            view.goQuestionActivity()
    }

}