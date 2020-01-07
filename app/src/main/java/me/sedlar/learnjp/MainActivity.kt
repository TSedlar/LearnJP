package me.sedlar.learnjp

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import me.sedlar.learnjp.fragments.*
import me.sedlar.learnjp.quiz.KanaChoiceStrategy
import me.sedlar.learnjp.quiz.KanaFlashcardStrategy
import me.sedlar.learnjp.quiz.KanjiChoiceStrategy
import me.sedlar.learnjp.quiz.KanjiFlashcardStrategy
import me.sedlar.learnjp.util.Kanji

val QUIZ_FLASHCARDS = "Flashcards"
val QUIZ_MULTI_CHOICE = "Multiple Choice"

enum class LangGenreType(val quizTypes: Array<String>) {
    HIRAGANA(arrayOf(QUIZ_FLASHCARDS, QUIZ_MULTI_CHOICE)),
    KATAKANA(arrayOf(QUIZ_FLASHCARDS, QUIZ_MULTI_CHOICE)),
    KANJI(arrayOf(QUIZ_FLASHCARDS, QUIZ_MULTI_CHOICE))
}

open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var navView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView?.setNavigationItemSelectedListener(this)

        goToPage(R.id.nav_home, HomeFragment(), false)

        Thread {
            Kanji.setup(this)
        }.start()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                goToPage(R.id.nav_home, HomeFragment())
            }
            R.id.nav_kana -> {
                goToPage(R.id.nav_kana, KanaFragment())
            }
            R.id.nav_kanji -> {
                goToPage(R.id.nav_kanji, KanjiFragment())
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun goToPage(id: Int, fragment: Fragment, addToBackStack: Boolean = true) {
        if (id != -1) {
            navView?.setCheckedItem(id)
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun promptStudy(type: LangGenreType, kanjiData: Map<String, Kanji>? = null): Dialog {
        val builder = AlertDialog.Builder(this, R.style.DarkDialog)
        val dialog = builder.setTitle("Choose A Quiz Type")
            .setItems(type.quizTypes) { _, which ->
                if (which >= 0) {
                    when (type.quizTypes[which]) {
                        QUIZ_FLASHCARDS -> {
                            when (type) {
                                LangGenreType.KANJI -> {
                                    goToPage(-1, FlashcardFragment(KanjiFlashcardStrategy(kanjiData)))
                                }
                                LangGenreType.HIRAGANA -> {
                                    goToPage(-1, FlashcardFragment(KanaFlashcardStrategy(true)))
                                }
                                LangGenreType.KATAKANA -> {
                                    goToPage(-1, FlashcardFragment(KanaFlashcardStrategy(false)))
                                }
                            }
                        }
                        QUIZ_MULTI_CHOICE -> {
                            when (type) {
                                LangGenreType.KANJI -> {
                                    goToPage(-1, MultiChoiceFragment(KanjiChoiceStrategy(kanjiData), false))
                                }
                                LangGenreType.HIRAGANA -> {
                                    goToPage(-1, MultiChoiceFragment(KanaChoiceStrategy(true)))
                                }
                                LangGenreType.KATAKANA -> {
                                    goToPage(-1, MultiChoiceFragment(KanaChoiceStrategy(false)))
                                }
                            }
                        }
                    }
                }
            }.create()
        dialog.show()
        return dialog
    }
}
