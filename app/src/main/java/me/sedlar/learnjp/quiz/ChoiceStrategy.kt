package me.sedlar.learnjp.quiz

import android.widget.Button
import android.widget.TextView
import me.sedlar.learnjp.R

abstract class ChoiceStrategy : QuizStrategy() {

    fun findButtons(ctx: QuizCtx): List<Button?> = listOf<Button?>(
        ctx.view?.findViewById(R.id.btnChoice1),
        ctx.view?.findViewById(R.id.btnChoice2),
        ctx.view?.findViewById(R.id.btnChoice3),
        ctx.view?.findViewById(R.id.btnChoice4)
    ).shuffled()

    fun findTextDisplay(ctx: QuizCtx): TextView? = ctx.view?.findViewById(R.id.txtContent)
}