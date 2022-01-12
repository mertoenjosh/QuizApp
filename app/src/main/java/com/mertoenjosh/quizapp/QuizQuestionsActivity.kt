package com.mertoenjosh.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentQuestion = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPos = 0
    private var mCorrectAnswers = 0
    private var mUserName: String? = null

    private lateinit var tvQuestion: TextView
    private lateinit var ivImage: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        tvQuestion = findViewById(R.id.tvQuestion)
        ivImage = findViewById(R.id.ivImage)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tvProgress)
        tvOptionOne = findViewById(R.id.tvOptionOne)
        tvOptionTwo = findViewById(R.id.tvOptionTwo)
        tvOptionThree = findViewById(R.id.tvOptionThree)
        tvOptionFour = findViewById(R.id.tvOptionFour)
        btnSubmit = findViewById(R.id.btnSubmit)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        setQuestion()

        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {
        mQuestionsList = Constants.getQuestions()
//        mCurrentQuestion = 6
        val question = mQuestionsList!![mCurrentQuestion - 1]

        defaultOptionsView()

        if (mCurrentQuestion == mQuestionsList!!.size) {
            btnSubmit.text = "FINISH"
        } else {
            btnSubmit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentQuestion
        tvProgress.text = "${mCurrentQuestion}/${progressBar.max}"
        tvQuestion.text = question.question

        ivImage.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()

        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        for (opt in options) {
            opt.setTextColor(Color.parseColor("#7A8089"))
            opt.typeface = Typeface.DEFAULT
            opt.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvOptionOne -> selectedOptView(tvOptionOne, 1)

            R.id.tvOptionTwo -> selectedOptView(tvOptionTwo, 2)

            R.id.tvOptionThree -> selectedOptView(tvOptionThree, 3)

            R.id.tvOptionFour -> selectedOptView(tvOptionFour, 4)

            R.id.btnSubmit -> {
                if (mSelectedOptionPos == 0) {
                    mCurrentQuestion++

                    when {
                        mCurrentQuestion <= mQuestionsList!!.size -> {
                            setQuestion()
                        } else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentQuestion - 1)
                    if (question!!.correctAnswer != mSelectedOptionPos) {
                        answerView(mSelectedOptionPos, R.drawable.incorrect_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentQuestion == mQuestionsList!!.size) {
                        btnSubmit.text = "FINISH"
                    } else {
                        btnSubmit.text = "GO TO THE NEXT QUESTION"
                    }

                    mSelectedOptionPos = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawable: Int) {
        when (answer) {
            1 -> {
                tvOptionOne.background = ContextCompat.getDrawable(this, drawable)
            }

            2 -> {
                tvOptionTwo.background = ContextCompat.getDrawable(this, drawable)
            }

            3 -> {
                tvOptionThree.background = ContextCompat.getDrawable(this, drawable)
            }

            4 -> {
                tvOptionFour.background = ContextCompat.getDrawable(this, drawable)
            }
        }
    }

    private fun selectedOptView(tv: TextView, selectedOptNumber: Int) {
        defaultOptionsView()
        mSelectedOptionPos = selectedOptNumber

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}