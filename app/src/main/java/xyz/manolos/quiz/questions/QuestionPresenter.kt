package xyz.manolos.quiz.questions

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import xyz.manolos.quiz.service.QuestionService
import javax.inject.Inject

class QuestionPresenter @Inject constructor(
    private val questionService: QuestionService,
    private val view: QuestionView
) {

    private val MAX_QUESTION = 10
    private val disposables = CompositeDisposable()


    fun nextStep(questionNUmber: Int) {
        if (questionNUmber == 10) {
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

    fun disposeResources() {
        disposables.dispose()
    }
}