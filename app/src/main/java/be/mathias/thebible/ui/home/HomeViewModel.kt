package be.mathias.thebible.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.database.bible.DatabaseVerseDao
import be.mathias.thebible.repository.VerseRepository

class HomeViewModel(val dao: DatabaseVerseDao , application: Application): AndroidViewModel(application) {
    private val database = BibleDatabase.getInstance(application)
    private val verseRepository = VerseRepository(database)

    val verses = verseRepository.verses
}