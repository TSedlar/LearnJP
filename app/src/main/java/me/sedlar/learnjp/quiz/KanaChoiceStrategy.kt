package me.sedlar.learnjp.quiz

import me.sedlar.learnjp.util.Kana

class KanaChoiceStrategy(val hiragana: Boolean) : ChoiceStrategy() {

    private var randomKana: Kana? = null

    override fun generateQuestion(ctx: QuizCtx) {
        guessed = false
        randomKana = Kana.choose()

        val randomKanaList = ArrayList<Kana>()

        while (randomKanaList.size < 3) {
            val rKana = Kana.choose()
            if (randomKana != rKana && !randomKanaList.contains(rKana)) {
                randomKanaList.add(rKana)
            }
        }

        val buttons = findButtons(ctx)

        buttons[0]?.text = randomKana?.romaji
        buttons[1]?.text = randomKanaList[0].romaji
        buttons[2]?.text = randomKanaList[1].romaji
        buttons[3]?.text = randomKanaList[2].romaji

        randomKana?.isEmpty()?.let { isEmpty ->
            if (!isEmpty) {
                findTextDisplay(ctx)?.let { txt ->
                    txt.text = if (hiragana) randomKana!!.hiragana else randomKana!!.katakana
                }
            }
        }
    }

    override fun checkAnswer(ctx: QuizCtx, answer: String?): Boolean {
        return answer == randomKana?.romaji
    }
}