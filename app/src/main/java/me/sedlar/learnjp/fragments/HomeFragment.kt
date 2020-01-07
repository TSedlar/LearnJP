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

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Kana shortcut
        view.findViewById<Button>(R.id.btnKana)?.let { btn ->
            btn.setOnClickListener {
                (activity as MainActivity).goToPage(R.id.nav_kana, KanaFragment())
            }
        }

        // Study hiragana shortcut
        view.findViewById<Button>(R.id.btnHiragana)?.let { btn ->
            btn.setOnClickListener {
                (activity as MainActivity).promptStudy(LangGenreType.HIRAGANA)
            }
        }

        // Study katakana shortcut
        view.findViewById<Button>(R.id.btnKatakana)?.let { btn ->
            btn.setOnClickListener {
                (activity as MainActivity).promptStudy(LangGenreType.KATAKANA)
            }
        }

        // Study kanji shortcut
        view.findViewById<Button>(R.id.btnKanji)?.let { btn ->
            btn.setOnClickListener {
                (activity as MainActivity).goToPage(R.id.nav_kanji, KanjiFragment())
            }
        }

        return view
    }
}
