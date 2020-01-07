package me.sedlar.learnjp.fragments

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import me.sedlar.learnjp.R
import me.sedlar.learnjp.quiz.QuizCtx
import me.sedlar.learnjp.quiz.QuizStrategy
import me.sedlar.learnjp.util.Kana
import kotlin.math.abs

class FlashcardFragment(val quizStrategy: QuizStrategy) : Fragment() {

    private var fragView: View? = null
    private var randomKana: Kana? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_flashcard, container, false)

        val ctx = QuizCtx(context, fragView)

        val size = Point()

        activity?.windowManager?.defaultDisplay?.getSize(size)

        val midX = size.x / 2

        var downX = -1F
        var downY = -1F
        var dragCount = 0

        fragView?.setOnTouchListener { _, event ->
            val mx = event.x
            val my = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = mx
                    downY = my
                }
                MotionEvent.ACTION_MOVE -> {
                    fragView?.x = mx - downX
                    fragView?.y = my - downY
                    fragView?.rotation = ((mx - midX) * (Math.PI / 32)).toFloat()
                    dragCount++
                }
                MotionEvent.ACTION_UP -> {
                    if (downX != -1F && downY != -1F && abs(downX - mx) >= 60) {
                        randomize(ctx)
                    } else if (dragCount <= 4) {
                        quizStrategy.checkAnswer(ctx, null)
                    }
                    dragCount = 0
                    fragView?.x = 0F
                    fragView?.y = 0F
                    fragView?.rotation = 0F
                    downX = -1F
                    downY = -1F
                }
            }
            return@setOnTouchListener true
        }

        randomize(ctx)

        Snackbar.make(
            activity!!.findViewById(android.R.id.content),
            "Tap to flip and swipe to randomize!",
            Snackbar.LENGTH_LONG
        ).show()

        return fragView
    }

    private fun randomize(ctx: QuizCtx) {
        quizStrategy.generateQuestion(ctx)
    }
}
