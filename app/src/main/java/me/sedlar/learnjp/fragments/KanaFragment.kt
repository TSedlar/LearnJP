package me.sedlar.learnjp.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import me.sedlar.learnjp.LangGenreType
import me.sedlar.learnjp.MainActivity
import me.sedlar.learnjp.R
import me.sedlar.learnjp.util.Kana

class KanaFragment : Fragment() {

    private var hiragana: Boolean = true
    private var fragView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_kana, container, false)

        fragView = view

        addCards(view)

        view?.findViewById<Button>(R.id.btnKanaSwitch)?.let { btn ->
            btn.setOnClickListener {
                handleKanaClick()
            }
        }

        view?.findViewById<Button>(R.id.btnStudy)?.let { btn ->
            btn.setOnClickListener {
                handleStudyClick()
            }
        }

        return view
    }

    private fun handleKanaClick() {
        hiragana = !hiragana
        fragView?.findViewById<Button>(R.id.btnKanaSwitch)?.let { btn ->
            btn.text = if (hiragana) "Hiragana" else "Katakana"
            fragView?.findViewById<TableLayout>(R.id.tblCards)?.let { cards ->
                for (i in 0..cards.childCount) {
                    cards.getChildAt(i)?.let { child ->
                        if (child is TableRow) {
                            for (j in 0..child.childCount) {
                                child.getChildAt(j)?.let { card ->
                                    val primary = card.findViewById<TextView>(R.id.primaryKana)
                                    val secondary = card.findViewById<TextView>(R.id.secondaryKana)
                                    val romaji = card.findViewById<TextView>(R.id.romaji)
                                    if (primary != null && secondary != null && romaji != null) {
                                        val romajiTxt = romaji.text.toString()
                                        Kana.list[romajiTxt]?.let { kana ->
                                            if (hiragana) {
                                                primary.text = kana.hiragana
                                                secondary.text = kana.katakana
                                            } else {
                                                primary.text = kana.katakana
                                                secondary.text = kana.hiragana
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleStudyClick() {
        (activity as MainActivity).promptStudy(if (hiragana) LangGenreType.HIRAGANA else LangGenreType.KATAKANA)
    }

    private fun addCards(view: View?) {
        view?.findViewById<TableLayout>(R.id.tblCards)?.let { cards ->
            var i = 0
            var row = TableRow(view.context)
            var lastCard: View? = null
            for (k in Kana.list.values) {
                if (i >= 5) {
                    lastCard?.let {
                        val params = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins(3, 3, 0, 3)
                        it.layoutParams = params
                    }
                    cards.addView(row)
                    row = TableRow(view.context)
                    i = 0
                }
                lastCard = makeCard(row, k.hiragana, k.katakana, k.romaji)
                // handle blank spaces
                if (k.romaji == "YA" || k.romaji == "YU" || k.romaji == "WI") {
                    lastCard = makeCard(row, "", "", "")
                    i++
                } else if (k.romaji == "N") {
                    makeCard(row, "", "", "")
                    makeCard(row, "", "", "")
                    lastCard = makeCard(row, "", "", "")
                    i += 3
                }
                i++
            }
            if (i > 0) {
                cards.addView(row)
            }
        }
    }

    private fun makeCard(root: ViewGroup, primaryKana: String, secondaryKana: String, romanji: String): View? {
        val card = LayoutInflater.from(context).inflate(R.layout.kana_card, root, false)
        if (primaryKana.isEmpty() || secondaryKana.isEmpty() || romanji.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                card?.background = null
            }
        }
        card?.findViewById<TextView>(R.id.primaryKana)?.let { txtPrimary ->
            txtPrimary.text = primaryKana
        }
        card?.findViewById<TextView>(R.id.secondaryKana)?.let { txtSecondary ->
            txtSecondary.text = secondaryKana
        }
        card?.findViewById<TextView>(R.id.romaji)?.let { txtRomanji ->
            txtRomanji.text = romanji
        }
        root.addView(card)
        return card
    }
}
