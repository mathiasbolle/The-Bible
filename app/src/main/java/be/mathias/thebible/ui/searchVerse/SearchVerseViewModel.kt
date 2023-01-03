package be.mathias.thebible.ui.searchVerse

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.database.bible.DatabaseVerseDao
import be.mathias.thebible.repository.VerseRepository
import kotlinx.coroutines.launch

class SearchVerseViewModel(val dao: DatabaseVerseDao, application: Application) :
    AndroidViewModel(application) {
    private val database = BibleDatabase.getInstance(application)
    private val verseRepository = VerseRepository(database)

    val verse = verseRepository.searchedVerse

    //TODO maybe extract this (parameter) to a class?
    fun getVerse(bookName: String, chapter: Int, verse: Int) {
        viewModelScope.launch {
            verseRepository.getVerse(bookName, chapter, verse)
        }
    }

    fun update(verseId: Int) {
        viewModelScope.launch {
            verseRepository.makeVerseFavorite(verseId = verseId)
        }
    }

    fun id(bookName: String, verseNumber: Int, chapter: Int): LiveData<Int> {
        val result = MutableLiveData<Int>()

        viewModelScope.launch {
            val id = verseRepository.getId(bookName, verseNumber, chapter)

            result.value = id
            Log.d("SearchVerseViewModel", id.toString())
        }
        return result;
    }
}