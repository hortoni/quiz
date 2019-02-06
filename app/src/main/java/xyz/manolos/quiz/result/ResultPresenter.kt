package xyz.manolos.quiz.result

import xyz.manolos.quiz.R
import javax.inject.Inject

class ResultPresenter @Inject constructor(
    private val view: ResultView
) {

    fun getResultText(text: String, username : String, correctAnswer : Int){
        if (correctAnswer == 0) {
            view.showResultZeroText(username)
        } else {
            view.showResultText(String.format(text, username, correctAnswer))
        }

    }

    fun getImageResourceResult(correctAnswer: Int){
        when (correctAnswer){
            in 0..5 -> view.showResultImage(R.drawable.ic_bad)
            in 6..8 -> view.showResultImage(R.drawable.ic_good)
            else -> view.showResultImage(R.drawable.ic_excellent)
        }

    }
}