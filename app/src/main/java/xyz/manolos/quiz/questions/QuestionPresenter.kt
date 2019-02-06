package xyz.manolos.quiz.questions

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import xyz.manolos.quiz.model.Answer
import xyz.manolos.quiz.service.QuestionService
import javax.inject.Inject

private const val MAX_QUESTION = 1

class QuestionPresenter @Inject constructor(
    private val questionService: QuestionService,
    private val view: QuestionView
) {

    private val disposables = CompositeDisposable()
    var correctAnswers : Int = 0

    fun nextStep(questionNumber: Int) {
        if (questionNumber == MAX_QUESTION) {
            view.showResult()
        } else {
            fetchQuestion()
        }
    }

    private fun fetchQuestion() {
        view.showProgressBar()
        questionService.fetch()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.showQuestion(it)
                    view.hideProgressBar()
                },
                onError = {
                    view.showError()
                    view.hideProgressBar()
                }
            )
            .addTo(disposables)
    }

    fun sendAnswer(questionId : String , answer : Answer) {
        view.showProgressBar()
        questionService.sendAnswer(questionId, answer)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.result) {
                        correctAnswers ++
                    }
                    view.showAnswerResult(it.result)
                    view.hideProgressBar()
                },
                onError = {
                    view.showError()
                    view.hideProgressBar()
                }
            )
            .addTo(disposables)
    }

    fun disposeResources() {
        disposables.dispose()
    }
}