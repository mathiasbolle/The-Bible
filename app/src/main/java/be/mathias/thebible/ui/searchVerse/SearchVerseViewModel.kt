package be.mathias.thebible.ui.searchVerse

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.database.bible.DatabaseVerseDao
import be.mathias.thebible.repository.verse.VerseRepository
import be.mathias.thebible.ui.VerseApiStatus
import kotlinx.coroutines.launch

class SearchVerseViewModel(val dao: DatabaseVerseDao, application: Application) :
    AndroidViewModel(application) {
    private val _status = MutableLiveData<VerseApiStatus>()
    val status: LiveData<VerseApiStatus>
        get() = _status

    private val database = BibleDatabase.getInstance(application)
    private val verseRepository = VerseRepository(database)

    val verse = verseRepository.searchedVerse

    //TODO maybe extract this (parameter) to a class?
    fun getVerse(bookName: String, chapter: Int, verse: Int) {
        viewModelScope.launch {
            _status.value = VerseApiStatus.LOADING
            verseRepository.getVerse(bookName, chapter, verse)
            _status.value = VerseApiStatus.DONE
        }
    }

    fun update(verseId: Int) {
        viewModelScope.launch {
            _status.value = VerseApiStatus.LOADING
            verseRepository.makeVerseFavorite(verseId = verseId)
            _status.value = VerseApiStatus.DONE
        }
    }

    fun id(bookName: String, verseNumber: Int, chapter: Int): LiveData<Int> {
        val result = MutableLiveData<Int>()

        viewModelScope.launch {
            _status.value = VerseApiStatus.LOADING
            val id = verseRepository.getId(bookName, verseNumber, chapter)

            result.value = id
            _status.value = VerseApiStatus.DONE
            Log.d("SearchVerseViewModel", id.toString())
        }
        return result
    }
}