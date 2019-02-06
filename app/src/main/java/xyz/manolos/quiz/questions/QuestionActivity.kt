package xyz.manolos.quiz.questions

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_question.*
import xyz.manolos.quiz.R
import xyz.manolos.quiz.injector
import xyz.manolos.quiz.model.Answer
import xyz.manolos.quiz.model.Question
import xyz.manolos.quiz.result.ResultActivity
import javax.inject.Inject


interface QuestionView {
    fun showQuestion(question: Question)
    fun showError()
    fun showResult()
    fun showAnswerResult(result: Boolean)
    fun showProgressBar()
    fun hideProgressBar()
}

private const val QUESTION_NUMBER = "question_number"
private const val CURRENT_QUESTION = "current_question"
private const val USERNAME = "username"
private const val CORRECT_ANSWER = "correctAnswer"

class QuestionActivity : AppCompatActivity(), QuestionView {

    @Inject
    lateinit var presenter: QuestionPresenter
    private var questionNumber = 0
    private var currentQuestion: Question? = null
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        injector
            .plusQuestion(QuestionModule(this))
            .inject(this)

        username = intent.getStringExtra(USERNAME)

        questionNumber = savedInstanceState?.getInt(QUESTION_NUMBER) ?: 0

        currentQuestion = savedInstanceState?.getParcelable(CURRENT_QUESTION)

        currentQuestion?.let { showQuestion(it) } ?: presenter.nextStep(questionNumber)

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
        supportActionBar!!.title = String.format(getString(R.string.question), questionNumber + 1)
        val adapter = ArrayAdapter(this, R.layout.single_choice_item_left, currentQuestion!!.options)
        optionsList.selector.colorFilter = PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
        optionsList.adapter = adapter
    }


    override fun showError() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    override fun showResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(USERNAME, username)
        intent.putExtra(CORRECT_ANSWER, presenter.correctAnswers)
        startActivity(intent)
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

        Handler().postDelayed({
            questionNumber++
            presenter.nextStep(questionNumber)
        }, 1200)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(CURRENT_QUESTION, currentQuestion)
        outState.putInt(QUESTION_NUMBER, questionNumber)
        outState.putString(USERNAME, username)
    }

}
