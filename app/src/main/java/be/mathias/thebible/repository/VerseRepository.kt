package be.mathias.thebible.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.database.bible.asDomain
import be.mathias.thebible.domain.Verse
import be.mathias.thebible.network.VerseApi
import be.mathias.thebible.network.asDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VerseRepository(private val database: BibleDatabase) {

    //this is for the history of all verses
    val verses = Transformations.map(database.databaseVerseDao.getAll()) {
        it.asDomain()
    }

    val searchedVerse = MutableLiveData<Verse>(null)

    suspend fun getVerse(bookName: String, chapter: Int, verse: Int) {
        try {
                val verseResponse =
                    VerseApi.retrofitService.getSingleVerse(bookName, chapter, verse).await()

                database.databaseVerseDao.insertAll(verseResponse.asDatabase())

                Log.d("verseRepository", verseResponse.apiVerses[0].toString())
                val verse = Verse(
                    verseResponse.apiVerses[0].verseNumber,
                    verseResponse.apiVerses[0].bookName,
                    verseResponse.apiVerses[0].chapter,
                    verseResponse.apiVerses[0].text
                )
                Log.d("verseRepository", verse.toString())

                searchedVerse.value = verse
                Log.d("verseRepository", searchedVerse.value.toString())
        } catch (e: Exception) {
            //TODO do something with exception
            e.message?.let { Log.e("VerseRepository", it) }
        }
    }
}