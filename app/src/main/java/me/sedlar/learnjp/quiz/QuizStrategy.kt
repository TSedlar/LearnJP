package me.sedlar.learnjp.quiz

import android.content.Context
import android.view.View

data class QuizCtx(val context: Context?, val view: View?)

abstract class QuizStrategy {

    var guessCount = 0
    var correctCount = 0
    var guessed = false

    abstract fun generateQuestion(ctx: QuizCtx)
    abstract fun checkAnswer(ctx: QuizCtx, answer: String?): Boolean
}