package xyz.manolos.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    lateinit var username: String
    private var correctAnswer : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        username = intent.getStringExtra("username")
        correctAnswer = intent.getIntExtra("correctAnswer", 0)

        correctAnswer = 10
        when (correctAnswer){
            0 -> resultTextView.text = username  + ", você não acertou nenhuma pergunta."
            1 -> resultTextView.text = username + ", você acertou 1 pergunta."
            else -> resultTextView.text = username + ", você acertou " + correctAnswer + " perguntas."
        }

        when (correctAnswer){
            in 0..5 -> resultImageView.setImageResource(R.drawable.ic_bad)
            in 6..8 -> resultImageView.setImageResource(R.drawable.ic_good)
            else -> resultImageView.setImageResource(R.drawable.ic_excellent)
        }

        playAgainButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            this.startActivity(intent)
            finish()
        }
    }

}
