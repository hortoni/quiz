package xyz.manolos.quiz.result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import xyz.manolos.quiz.R
import xyz.manolos.quiz.home.HomeActivity
import xyz.manolos.quiz.injector
import javax.inject.Inject

interface ResultView {
    fun showResultText(text: String)
    fun showResultImage(resource: Int)
}

private const val CORRECT_ANSWER = "correctAnswer"
private const val USERNAME = "username"

class ResultActivity : AppCompatActivity(), ResultView {

    @Inject
    lateinit var presenter: ResultPresenter
    private lateinit var username: String
    private var correctAnswer : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        injector
            .plusResult(ResultModule(this))
            .inject(this)

        username = intent.getStringExtra(USERNAME)
        correctAnswer = intent.getIntExtra(CORRECT_ANSWER, 0)

        presenter.getResultText(resources.getQuantityString(R.plurals.questions_result, correctAnswer),
            username, correctAnswer)

        presenter.getImageResourceResult(correctAnswer)

        playAgainButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            this.startActivity(intent)
            finish()
        }
    }

    override fun showResultText(text : String ) {
        resultTextView.text = text
    }

    override fun showResultImage(resource: Int) {
        resultImageView.setImageResource(resource)
    }

}
