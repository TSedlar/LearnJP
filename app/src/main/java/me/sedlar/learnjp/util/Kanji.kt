package me.sedlar.learnjp.util

import android.content.Context
import me.sedlar.learnjp.R
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

data class Kanji(val character: String, val readings: Array<String>, val meanings: Array<String>) {

    companion object {
        private var grade1Map: Map<String, Kanji>? = null
        private var grade2Map: Map<String, Kanji>? = null
        private var grade3Map: Map<String, Kanji>? = null
        private var grade4Map: Map<String, Kanji>? = null
        private var grade5Map: Map<String, Kanji>? = null
        private var grade6Map: Map<String, Kanji>? = null
        private var jpltn5Map: Map<String, Kanji>? = null
        private var jpltn4Map: Map<String, Kanji>? = null
        private var jpltn3Map: Map<String, Kanji>? = null
        private var jpltn2Map: Map<String, Kanji>? = null
        private var jpltn1Map: Map<String, Kanji>? = null
        private var juniorHighMap: Map<String, Kanji>? = null
        private var newspaperMap: Map<String, Kanji>? = null

        val grade1 get() = grade1Map
        val grade2 get() = grade2Map
        val grade3 get() = grade3Map
        val grade4 get() = grade4Map
        val grade5 get() = grade5Map
        val grade6 get() = grade6Map
        val jpltn5 get() = jpltn5Map
        val jpltn4 get() = jpltn4Map
        val jpltn3 get() = jpltn3Map
        val jpltn2 get() = jpltn2Map
        val jpltn1 get() = jpltn1Map
        val juniorHigh get() = juniorHighMap
        val newspaper get() = newspaperMap

        fun setup(context: Context?) {
            grade1Map = setup(context, R.raw.grade1)
            grade2Map = setup(context, R.raw.grade2)
            grade3Map = setup(context, R.raw.grade3)
            grade4Map = setup(context, R.raw.grade4)
            grade5Map = setup(context, R.raw.grade5)
            grade6Map = setup(context, R.raw.grade6)
            jpltn5Map = setup(context, R.raw.jpltn5)
            jpltn4Map = setup(context, R.raw.jpltn4)
            jpltn3Map = setup(context, R.raw.jpltn3)
            jpltn2Map = setup(context, R.raw.jpltn2)
            jpltn1Map = setup(context, R.raw.jpltn1)
            juniorHighMap = setup(context, R.raw.juniorhigh)
            newspaperMap = setup(context, R.raw.newspaper)
        }

        private fun setup(context: Context?, id: Int): Map<String, Kanji>? {
            context?.let { ctx ->
                ctx.resources?.openRawResource(id)?.let { stream ->
                    val data = Scanner(stream).useDelimiter("\\A").next()
                    val kanjiList = parse(data)
                    return sortedMap(kanjiList)
                }
            }
            return null
        }

        private fun parse(jsonString: String): List<Kanji> {
            val list = ArrayList<Kanji>()
            val o = JSONObject(jsonString)
            val kanji = o.getJSONArray("kanji")
            for (i in 0 until kanji.length()) {
                val obj = kanji.getJSONObject(i)
                val symbol = obj.getString("symbol")

                val kanaArray = obj.getJSONArray("kana")
                val meaningArray = obj.getJSONArray("meanings")

                val kanaList = ArrayList<String>()
                val meaningList = ArrayList<String>()

                for (j in 0 until kanaArray.length()) {
                    kanaList.add(kanaArray.getString(j))
                }

                for (j in 0 until meaningArray.length()) {
                    meaningList.add(meaningArray.getString(j))
                }

                list.add(Kanji(symbol, kanaList.toTypedArray(), meaningList.toTypedArray()))
            }
            return list
        }

        private fun sortedMap(list: List<Kanji>): Map<String, Kanji> {
            val map = HashMap<String, Kanji>()
            val keys = list.map { it.character }

            for (kanji in list) {
                map[kanji.character] = kanji
            }

            return map.toSortedMap(compareBy { keys.indexOf(it) })
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Kanji

        if (character != other.character) return false
        if (!readings.contentEquals(other.readings)) return false
        if (!meanings.contentEquals(other.meanings)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = character.hashCode()
        result = 31 * result + readings.contentHashCode()
        result = 31 * result + meanings.contentHashCode()
        return result
    }
}