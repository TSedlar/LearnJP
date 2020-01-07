package me.sedlar.learnjp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import me.sedlar.learnjp.LangGenreType
import me.sedlar.learnjp.MainActivity
import me.sedlar.learnjp.R
import me.sedlar.learnjp.util.Kanji

class KanjiFragment : Fragment() {

    private var fragView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragView = inflater.inflate(R.layout.fragment_kanji, container, false)

        val scaleList = arrayOf(
            R.id.btnGrade1 to Kanji.grade1,
            R.id.btnGrade2 to Kanji.grade2,
            R.id.btnGrade3 to Kanji.grade3,
            R.id.btnGrade4 to Kanji.grade4,
            R.id.btnGrade5 to Kanji.grade5,
            R.id.btnGrade6 to Kanji.grade6,
            R.id.btnJpltN5 to Kanji.jpltn5,
            R.id.btnJpltN4 to Kanji.jpltn4,
            R.id.btnJpltN3 to Kanji.jpltn3,
            R.id.btnJpltN2 to Kanji.jpltn2,
            R.id.btnJpltN1 to Kanji.jpltn1,
            R.id.btnJuniorHigh to Kanji.juniorHigh,
            R.id.btnNewspaper to Kanji.newspaper
        )

        for (scale in scaleList) {
            fragView?.findViewById<Button>(scale.first)?.setOnClickListener {
                (activity as MainActivity).promptStudy(LangGenreType.KANJI, scale.second)
            }
        }

        return fragView
    }
}
