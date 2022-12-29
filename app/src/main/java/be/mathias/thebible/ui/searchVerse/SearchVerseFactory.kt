package be.mathias.thebible.ui.searchVerse

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.mathias.thebible.database.bible.DatabaseVerseDao

class SearchVerseFactory(private val dataSource: DatabaseVerseDao, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchVerseViewModel::class.java)) {
            return SearchVerseViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}