package me.sedlar.learnjp.util

import kotlin.random.Random

data class Kana(val romaji: String, val hiragana: String, val katakana: String) {

    fun isEmpty(): Boolean {
        return romaji.isEmpty() || hiragana.isEmpty() || katakana.isEmpty()
    }

    companion object {
        val list by lazy {
            val map = HashMap<String, Kana>()
            val keys = kana.map { it[0] }
            for (kana in kana) {
                map[kana[0]] = Kana(kana[0], kana[1], kana[2])
            }
            return@lazy map.toSortedMap(compareBy { keys.indexOf(it) })
        }

        fun choose(): Kana {
            var random: Kana? = null
            while (random == null || random.isEmpty()) {
                random = list[list.keys.elementAt(Random.nextInt(list.size))]
            }
            return random
        }
    }
}

private val kana = arrayOf(
    arrayOf("A", "あ", "ア"),
    arrayOf("I", "い", "イ"),
    arrayOf("U", "う", "ウ"),
    arrayOf("E", "え", "エ"),
    arrayOf("O", "お", "オ"),
    arrayOf("KA", "か", "カ"),
    arrayOf("KI", "き", "キ"),
    arrayOf("KU", "く", "ク"),
    arrayOf("KE", "け", "ケ"),
    arrayOf("KO", "こ", "コ"),
    arrayOf("GA", "が", "ガ"),
    arrayOf("GI", "ぎ", "ギ"),
    arrayOf("GU", "ぐ", "グ"),
    arrayOf("GE", "げ", "ゲ"),
    arrayOf("GO", "ご", "ゴ"),
    arrayOf("SA", "さ", "サ"),
    arrayOf("SI", "し", "シ"),
    arrayOf("SU", "す", "ス"),
    arrayOf("SE", "せ", "セ"),
    arrayOf("SO", "そ", "ソ"),
    arrayOf("ZA", "ざ", "ザ"),
    arrayOf("DZI", "じ", "ジ"),
    arrayOf("ZU", "ず", "ズ"),
    arrayOf("ZE", "ぜ", "ゼ"),
    arrayOf("ZO", "ぞ", "ゾ"),
    arrayOf("TA", "た", "タ"),
    arrayOf("CHI", "ち", "チ"),
    arrayOf("TSU", "つ", "ツ"),
    arrayOf("TE", "て", "テ"),
    arrayOf("TO", "と", "ト"),
    arrayOf("DA", "だ", "ダ"),
    arrayOf("JI", "ぢ", "ヂ"),
    arrayOf("DZU", "づ", "ヅ"),
    arrayOf("DE", "で", "デ"),
    arrayOf("DO", "ど", "ド"),
    arrayOf("NA", "な", "ナ"),
    arrayOf("NI", "に", "ニ"),
    arrayOf("NU", "ぬ", "ヌ"),
    arrayOf("NE", "ね", "ネ"),
    arrayOf("NO", "の", "ノ"),
    arrayOf("HA", "は", "ハ"),
    arrayOf("HI", "ひ", "ヒ"),
    arrayOf("FU", "ふ", "フ"),
    arrayOf("HE", "へ", "ヘ"),
    arrayOf("HO", "ほ", "ホ"),
    arrayOf("BA", "ば", "バ"),
    arrayOf("BI", "び", "ビ"),
    arrayOf("BU", "ぶ", "ブ"),
    arrayOf("BE", "べ", "ベ"),
    arrayOf("BO", "ぼ", "ボ"),
    arrayOf("PA", "ぱ", "パ"),
    arrayOf("PI", "ぴ", "ピ"),
    arrayOf("PU", "ぷ", "プ"),
    arrayOf("PE", "ぺ", "ペ"),
    arrayOf("PO", "ぽ", "ポ"),
    arrayOf("MA", "ま", "マ"),
    arrayOf("MI", "み", "ミ"),
    arrayOf("MU", "む", "ム"),
    arrayOf("ME", "め", "メ"),
    arrayOf("MO", "も", "モ"),
    arrayOf("YA", "や", "ヤ"),
//    arrayOf("", "", ""), // YI does not exist
    arrayOf("YU", "ゆ", "ユ"),
//    arrayOf("", "", ""), // YE does not exist
    arrayOf("YO", "よ", "ヨ"),
    arrayOf("RA", "ら", "ラ"),
    arrayOf("RI", "り", "リ"),
    arrayOf("RU", "る", "ル"),
    arrayOf("RE", "れ", "レ"),
    arrayOf("RO", "ろ", "ロ"),
    arrayOf("WA", "わ", "ワ"),
    arrayOf("WI", "ゐ", "ヰ"),
//    arrayOf("", "", ""), // WU does not exist
    arrayOf("WE", "ゑ", "ヱ"),
    arrayOf("WO", "を", "ヲ"),
    arrayOf("N", "ん", "ン"),
//    arrayOf("", "", ""), // placeholder
//    arrayOf("", "", ""), // placeholder
//    arrayOf("", "", ""), // placeholder
    arrayOf("VU", "ゔ", "ヴ")
)