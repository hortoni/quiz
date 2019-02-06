package xyz.manolos.quiz.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import xyz.manolos.quiz.R
import xyz.manolos.quiz.injector
import xyz.manolos.quiz.questions.QuestionActivity
import javax.inject.Inject

interface HomeView {
    fun showError(text: String)
    fun goQuestionActivity()
}

private const val USERNAME = "username"

class HomeActivity : AppCompatActivity(), HomeView {

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        injector
            .plusHome(HomeModule(this))
            .inject(this)

        goToQuizButton.setOnClickListener {
            presenter.goToQuiz(usernameEditText.text.isBlank(), getString(R.string.username_error))
        }
    }

    override fun showError(text: String) {
        Toast.makeText(this, getString(R.string.username_error), Toast.LENGTH_LONG).show()
    }

    override fun goQuestionActivity() {
        val intent = Intent(this, QuestionActivity::class.java)
        intent.putExtra(USERNAME, usernameEditText.text.toString())
        this.startActivity(intent)
    }
}
