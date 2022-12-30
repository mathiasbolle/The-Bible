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

    val searchedVerse = MutableLiveData<Verse>()

    suspend fun getVerse(bookName: String, chapter: Int, verse: Int) {
        try {
            withContext(Dispatchers.IO) {
                val verseResponse = VerseApi.retrofitService.getSingleVerse(bookName, chapter, verse).await()

                database.databaseVerseDao.insertAll(verseResponse.asDatabase())


                Log.d("verseRepository", verseResponse.apiVerses[0].toString())
                searchedVerse.value = Verse(0, "qsjdfoqsdjpf", 1, "qsiodjfoiqdsjio")
            }
        }catch(e: Exception) {
            //TODO do something with exception
        }
    }
}