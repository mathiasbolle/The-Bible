package be.mathias.thebible.repository

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.database.bible.asDomain
import be.mathias.thebible.domain.Verse
import be.mathias.thebible.network.VerseApi
import be.mathias.thebible.network.asDatabase

class VerseRepository(private val database: BibleDatabase) {

    //this is for the history of all verses
    val verses = Transformations.map(database.databaseVerseDao.getAll()) {
        it.asDomain()
    }

    val verse = MediatorLiveData<Verse>()

    suspend fun getVerse(bookName: String, chapter: Int, verse: Int): Verse? {
        try {
            val verseResponse = VerseApi.retrofitService.getSingleVerse(bookName, chapter, verse).await()

            database.databaseVerseDao.insertAll(verseResponse.asDatabase())

            return Verse(verseResponse.apiVerses[0].verseId, verseResponse.apiVerses[0].bookName, verseResponse.apiVerses[0].chapter, verseResponse.apiVerses[0].text)
        }catch(e: Exception) {
            //TODO do something with exception
        }
        return null
    }
}