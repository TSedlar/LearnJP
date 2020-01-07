package me.sedlar.learnjp.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import me.sedlar.learnjp.R
import me.sedlar.learnjp.quiz.QuizCtx
import me.sedlar.learnjp.quiz.QuizStrategy

class MultiChoiceFragment(private val quizStrategy: QuizStrategy, private val fourSquare: Boolean = true) : Fragment() {

    private var fragView: View? = null
    private val buttons: List<Button?>
        get() = listOf(
            fragView?.findViewById(R.id.btnChoice1),
            fragView?.findViewById(R.id.btnChoice2),
            fragView?.findViewById(R.id.btnChoice3),
            fragView?.findViewById(R.id.btnChoice4)
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(
            if (fourSquare) R.layout.fragment_multi_choice else R.layout.fragment_multi_line_choice,
            container,
            false
        )

        val ctx = QuizCtx(context, fragView)

        randomize(ctx)

        for (button in buttons) {
            button?.setOnClickListener {
                if (!quizStrategy.guessed) {
                    quizStrategy.guessCount++
                }
                if (quizStrategy.checkAnswer(ctx, button.text.toString())) {
                    if (!quizStrategy.guessed) {
                        quizStrategy.correctCount++
                    }
                    updateProgress(ctx)
                    randomize(ctx)
                } else {
                    quizStrategy.guessed = true
                    button.setTextColor(Color.RED)
                    updateProgress(ctx)
                }
            }
        }

        return fragView
    }

    private fun randomize(ctx: QuizCtx) {
        for (button in buttons) {
            ctx.context?.let { c -> button?.setTextColor(ContextCompat.getColor(c, R.color.colorText)) }
        }
        quizStrategy.guessed = false
        quizStrategy.generateQuestion(ctx)
    }

    private fun updateProgress(ctx: QuizCtx) {
        val percent = ((quizStrategy.correctCount.toDouble() / quizStrategy.guessCount.toDouble()) * 100.0).toInt()

        ctx.view?.findViewById<TextView>(R.id.progressPercent)?.text = "$percent%"
        ctx.view?.findViewById<TextView>(R.id.progressCount)?.text =
            "${quizStrategy.correctCount}/${quizStrategy.guessCount}"
    }
}
