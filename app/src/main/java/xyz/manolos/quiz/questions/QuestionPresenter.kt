package xyz.manolos.quiz.questions

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import xyz.manolos.quiz.model.Answer
import xyz.manolos.quiz.service.QuestionService
import javax.inject.Inject

private const val MAX_QUESTION = 10

class QuestionPresenter @Inject constructor(
    private val questionService: QuestionService,
    private val view: QuestionView
) {

    private val disposables = CompositeDisposable()

    fun nextStep(questionNumber: Int) {
        if (questionNumber == MAX_QUESTION) {
            view.showResult()
        } else {
            fetchQuestion()
        }
    }

    private fun fetchQuestion() {
        questionService.fetch()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.showQuestion(it)
                },
                onError = {
                    view.showError()
                }
            )
            .addTo(disposables)
    }

    fun sendAnswer(questionId : String , answer : Answer) {
        questionService.sendAnswer(questionId, answer)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.showAnswerResult(it.result)
                },
                onError = {
                    view.showError()
                }
            )
            .addTo(disposables)
    }

    fun disposeResources() {
        disposables.dispose()
    }
}