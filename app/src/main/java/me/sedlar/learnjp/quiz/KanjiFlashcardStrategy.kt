package me.sedlar.learnjp.quiz

import android.widget.TextView
import me.sedlar.learnjp.R
import me.sedlar.learnjp.util.Kanji
import kotlin.random.Random

class KanjiFlashcardStrategy(val data: Map<String, Kanji>?) : QuizStrategy() {

    private var randomKanji: Kanji? = null

    override fun generateQuestion(ctx: QuizCtx) {
        ctx.view?.findViewById<TextView>(R.id.txtContent)?.let { txt ->
            txt.textSize = 200F
            randomKanji = chooseKanji()
            txt.text = randomKanji?.character
        }
    }

    override fun checkAnswer(ctx: QuizCtx, answer: String?): Boolean {
        ctx.view?.findViewById<TextView>(R.id.txtContent)?.let { txt ->
            val key = txt.text.toString()
            if (data != null && data.containsKey(key)) { // Kanji character
                txt.textSize = 20F
                txt.text = ""
                var displayText = ""
                randomKanji?.let { kanji ->
                    for (kana in kanji.readings) {
                        displayText += "$kana\n"
                    }
                    displayText += "\n"
                    for (meaning in kanji.meanings) {
                        displayText += "$meaning\n"
                    }
                    displayText = displayText.substring(0, displayText.length - 1)
                    txt.text = displayText
                }
            } else { // Data listing
                txt.textSize = 200F
                txt.text = ""
                txt.text = randomKanji?.character
            }
        }
        return true
    }

    private fun chooseKanji(): Kanji? {
        var kanji: Kanji? = null
        data?.keys?.let { keys ->
            kanji = data[keys.elementAt(Random.nextInt(keys.size))]
        }
        return kanji
    }
}