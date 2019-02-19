package xyz.manolos.quiz.question

import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import xyz.manolos.quiz.model.Answer
import xyz.manolos.quiz.model.AnswerResult
import xyz.manolos.quiz.model.Question
import xyz.manolos.quiz.questions.QuestionPresenter
import xyz.manolos.quiz.questions.QuestionView
import xyz.manolos.quiz.service.QuestionService

@RunWith(MockitoJUnitRunner::class)
class QuestionPresenterTest {

    @Mock
    private lateinit var view: QuestionView

    @Mock
    private lateinit var questionService: QuestionService

    @InjectMocks
    private lateinit var presenter: QuestionPresenter

    @Before
    fun `set up`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `should show error when fetch fails`() {
        given(questionService.fetch()).willReturn(Single.error(Throwable()))
        presenter.nextStep(1)
        verify(view).showErrorQuestion()
    }

    @Test
    fun `should add question when question is fetched`() {
        val question = Question("1", "teste")
        given(questionService.fetch()).willReturn(Single.just(question))
        presenter.nextStep(1)

        verify(view).showQuestion(question)
    }

    @Test
    fun `should show error when send answer fails`() {
        val answer = Answer("teste")
        given(questionService.sendAnswer("10", answer)).willReturn(Single.error(Throwable()))
        presenter.sendAnswer("10", answer)
        verify(view).showErrorAnswer()
    }

    @Test
    fun `should show answer result when reply is sent`() {
        val answer = Answer("teste")
        val answerResult = AnswerResult(true)
        given(questionService.sendAnswer("10", answer)).willReturn(Single.just(answerResult))
        presenter.sendAnswer("10", answer)

        verify(view).showAnswerResult(answerResult.result)

    }
}