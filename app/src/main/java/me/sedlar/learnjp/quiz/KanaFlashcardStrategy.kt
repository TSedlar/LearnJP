package me.sedlar.learnjp.quiz

import android.widget.TextView
import me.sedlar.learnjp.R
import me.sedlar.learnjp.util.Kana

class KanaFlashcardStrategy(val hiragana: Boolean) : QuizStrategy() {

    var randomKana: Kana? = null

    override fun generateQuestion(ctx: QuizCtx) {
        ctx.view?.findViewById<TextView>(R.id.txtContent)?.let { txt ->
            txt.textSize = 200F
            randomKana = Kana.choose()
            randomKana?.let { rKana ->
                txt.text = if (hiragana) rKana.hiragana else rKana.katakana
            }
        }
    }

    override fun checkAnswer(ctx: QuizCtx, answer: String?): Boolean {
        ctx.view?.findViewById<TextView>(R.id.txtContent)?.let { txt ->
            val key = txt.text.toString()
            if (Kana.list.containsKey(key)) { // Romaji
                txt.textSize = 200F
                txt.text = if (hiragana) randomKana?.hiragana else randomKana?.katakana
            } else { // Kana
                txt.textSize = 80F
                txt.text = randomKana?.romaji
            }
        }
        return true
    }
}