package xyz.manolos.quiz.questions

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
            showProgressBar()
            presenter.nextStep(questionNumber)
        }

        replyQuestionButton.setOnClickListener {
            var answerString = optionsList.getItemAtPosition(optionsList.checkedItemPosition) as String
            presenter.sendAnswer(currentQuestion!!.id , Answer(answerString ))
        }

    }

    override fun showQuestion(question: Question) {
        currentQuestion = question
        questionTextView.text =  currentQuestion!!.statement
        val adapter = ArrayAdapter(this, R.layout.single_choice_item_left, currentQuestion!!.options)
        optionsList.adapter = adapter
        hideProgressBar()
    }

    override fun showError() {
        // show toast
    }

    override fun showResult() {
        hideProgressBar()
        Toast.makeText(this, "ACABOU", Toast.LENGTH_LONG).show()

    }

    override fun showAnswerResult(result: Boolean) {
        if (result) {
            Toast.makeText(this, "resposta correta", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "resposta incorreta", Toast.LENGTH_LONG).show()
        }
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
    }

}
