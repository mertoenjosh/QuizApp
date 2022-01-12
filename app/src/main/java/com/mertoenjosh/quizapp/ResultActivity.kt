package com.mertoenjosh.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var tvScore: TextView
    private lateinit var btnFinish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        tvName = findViewById(R.id.tv_name)
        tvScore = findViewById(R.id.tv_score)
        btnFinish = findViewById(R.id.btn_finish)

        val username = intent.getStringExtra(Constants.USER_NAME)
        tvName.text = username

        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        tvScore.text = "Your score is $correctAnswers out of $totalQuestions"

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}