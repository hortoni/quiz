package xyz.manolos.quiz.home

import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val view: HomeView
) {

    fun goToQuiz(textviewIsBlank: Boolean, errorMessage: String){
        if (textviewIsBlank)
            view.showError(errorMessage)
        else
            view.goQuestionActivity()
    }

}