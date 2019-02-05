package xyz.manolos.quiz.questions

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_question.*
import xyz.manolos.quiz.R
import xyz.manolos.quiz.injector
import xyz.manolos.quiz.model.Answer
import xyz.manolos.quiz.model.Question
import javax.inject.Inject


interface QuestionView {
    fun showQuestion(question: Question)
    fun showError()
    fun showResult()
    fun showAnswerResult(result: Boolean)
    fun showProgressBar()
    fun hideProgressBar()
}


private const val QUESTION_NUMBER = "questionNumber"
private const val CURRENT_QUESTION = "current_question"

class QuestionActivity : AppCompatActivity(), QuestionView {

    @Inject
    lateinit var presenter: QuestionPresenter
    private var questionNumber = 0
    private var currentQuestion: Question? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        injector
            .plus(QuestionModule(this))
            .inject(this)

        questionNumber = savedInstanceState?.getInt(QUESTION_NUMBER) ?: 0

        currentQuestion = savedInstanceState?.getParcelable(CURRENT_QUESTION)

        currentQuestion?.let { showQuestion(it) } ?: presenter.nextStep(questionNumber)

        nextQuestionButton.setOnClickListener {
            questionNumber++
            presenter.nextStep(questionNumber)
        }

        replyQuestionButton.setOnClickListener {
            if (optionsList.getItemAtPosition(optionsList.checkedItemPosition) == null) {
                Toast.makeText(this, R.string.error_select_asnwer, Toast.LENGTH_LONG).show()
            } else {
                var answerString = optionsList.getItemAtPosition(optionsList.checkedItemPosition) as String
                presenter.sendAnswer(currentQuestion!!.id, Answer(answerString))
            }
        }
    }

    override fun showQuestion(question: Question) {
        currentQuestion = question
        questionTextView.text = currentQuestion!!.statement
        supportActionBar!!.title = "Questão " + (questionNumber + 1)
        val adapter = ArrayAdapter(this, R.layout.single_choice_item_left, currentQuestion!!.options)
        optionsList.adapter = adapter
        changeLayoutToReply()
    }

    @SuppressLint("RestrictedApi")
    private fun changeLayoutToReply() {
        optionsList.isEnabled = true
        optionsList.selector.colorFilter = PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
        replyQuestionButton.visibility = View.VISIBLE
        nextQuestionButton.visibility = View.GONE
    }

    override fun showError() {
        // show toast
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    override fun showResult() {
        Toast.makeText(this, "ACABOU - Acertou : " + presenter.correctAnswers, Toast.LENGTH_LONG).show()
        finish()
    }


    override fun showAnswerResult(result: Boolean) {
        if (result) {
            Toast.makeText(this, getString(R.string.correct_answer), Toast.LENGTH_LONG).show()
            optionsList.selector.colorFilter = PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN)
        } else {
            Toast.makeText(this, getString(R.string.wrong_answer), Toast.LENGTH_LONG).show()
            optionsList.selector.colorFilter = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        }
        changeLayoutToNextQuestion()
    }

    @SuppressLint("RestrictedApi")
    private fun changeLayoutToNextQuestion() {
        optionsList.isEnabled = false
        replyQuestionButton.visibility = View.GONE
        nextQuestionButton.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        progressBarFrame.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBarFrame.visibility = View.GONE
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(CURRENT_QUESTION, currentQuestion)
        outState.putInt(QUESTION_NUMBER, questionNumber)
    }

}
