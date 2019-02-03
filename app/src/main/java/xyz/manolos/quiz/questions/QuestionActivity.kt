package xyz.manolos.quiz.questions

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_question.*
import xyz.manolos.quiz.R
import xyz.manolos.quiz.injector
import xyz.manolos.quiz.model.Question
import javax.inject.Inject


interface QuestionView {
    fun showQuestion(question: Question)
    fun showError()
    fun showResult()
}


private const val QUESTION_NUMBER = "questionNumber"

class QuestionActivity : AppCompatActivity(), QuestionView {

    @Inject
    lateinit var presenter: QuestionPresenter
    private var questionNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        injector
            .plus(QuestionModule(this))
            .inject(this)

        addQuestionButton.setOnClickListener {
            questionNumber = savedInstanceState?.getInt(QUESTION_NUMBER) ?: 0
            presenter.nextStep(questionNumber)
        }

    }

    override fun showQuestion(question: Question) {
        Toast.makeText(this, "statement: ${question.statement} - id: ${question.id}", Toast.LENGTH_LONG).show()
    }

    override fun showError() {
        // show toast
    }

    override fun showResult() {
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(QUESTION_NUMBER, questionNumber)
    }

}
