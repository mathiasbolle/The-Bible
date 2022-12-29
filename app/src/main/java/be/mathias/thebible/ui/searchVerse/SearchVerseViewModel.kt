package be.mathias.thebible.ui.searchVerse

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.database.bible.DatabaseVerseDao
import be.mathias.thebible.domain.Verse
import be.mathias.thebible.repository.VerseRepository
import kotlinx.coroutines.launch

class SearchVerseViewModel(val dao: DatabaseVerseDao, application: Application): AndroidViewModel(application) {
    private val database = BibleDatabase.getInstance(application)
    private val verseRepository = VerseRepository(database)


    //TODO maybe extract this (parameter) to a class?
    fun getVerse(bookName: String, chapter: Int, verse: Int) {
        viewModelScope.launch {
            val verse1 = verseRepository.getVerse(bookName, chapter, verse)
            Log.d("SearchVerseViewModel", verse1.toString())
        }
    }



    /*
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchVerseViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchVerseViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }*/


}