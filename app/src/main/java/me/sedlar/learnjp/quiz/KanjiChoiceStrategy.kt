package me.sedlar.learnjp.quiz

import me.sedlar.learnjp.util.Kanji
import kotlin.random.Random

class KanjiChoiceStrategy(val data: Map<String, Kanji>?) : ChoiceStrategy() {

    var randomKanji: Kanji? = null

    override fun generateQuestion(ctx: QuizCtx) {
        randomKanji = chooseKanji()

        val randomKanjiList = ArrayList<Kanji>()

        randomKanji?.let { kanji ->
            while (randomKanjiList.size < 3) {
                chooseKanji()?.let { rKanji ->
                    if (areDifferent(kanji, rKanji)) {
                        randomKanjiList.add(rKanji)
                    }
                }
            }
        }

        val buttons = findButtons(ctx)

        buttons[0]?.text = randomKanji?.meanings?.chooseKanjiMeaning()
        buttons[1]?.text = randomKanjiList[0].meanings.chooseKanjiMeaning()
        buttons[2]?.text = randomKanjiList[1].meanings.chooseKanjiMeaning()
        buttons[3]?.text = randomKanjiList[2].meanings.chooseKanjiMeaning()

        randomKanji?.let { kanji ->
            findTextDisplay(ctx)?.let { txt ->
                txt.text = kanji.character
            }
        }
    }

    override fun checkAnswer(ctx: QuizCtx, answer: String?): Boolean {
        var valid = false
        answer?.let { answerText ->
            randomKanji?.meanings?.forEach { meaning ->
                if (meaning.toLowerCase() == answerText.toLowerCase()) {
                    valid = true
                }
            }
        }
        return valid
    }

    private fun chooseKanji(): Kanji? {
        var kanji: Kanji? = null
        data?.keys?.let { keys ->
            kanji = data[keys.elementAt(Random.nextInt(keys.size))]
        }
        return kanji
    }

    private fun areDifferent(k1: Kanji, k2: Kanji): Boolean {
        for (m1 in k1.meanings) {
            for (m2 in k2.meanings) {
                if (m1.toLowerCase() == m2.toLowerCase()) {
                    return false
                }
            }
        }
        return true
    }
}

private fun <T> Array<T>.chooseKanjiMeaning(): T {
    return this.first()
//    return this[Random.nextInt(this.size)]
}