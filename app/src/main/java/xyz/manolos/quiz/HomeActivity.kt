package xyz.manolos.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import xyz.manolos.quiz.questions.QuestionActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        goToQuizButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            if (usernameEditText.text.isBlank()) {
                Toast.makeText(this, getString(R.string.username_error), Toast.LENGTH_LONG).show()
            } else {
                intent.putExtra("username", usernameEditText.text.toString())
                this.startActivity(intent)
            }
        }
    }
}
