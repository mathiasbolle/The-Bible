package be.mathias.thebible.repository

import androidx.lifecycle.Transformations
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.database.bible.asDomain
import be.mathias.thebible.network.VerseApi
import be.mathias.thebible.network.asDatabase

class VerseRepository(private val database: BibleDatabase) {

    //this is for the history of all verses
    val verses = Transformations.map(database.databaseVerseDao.getAll()) {
        it.asDomain()
    }

    suspend fun getVerse(bookName: String, chapter: Int, verse: Int) {
        val verse = VerseApi.retrofitService.getSingleVerse(bookName, chapter, verse).await()


        database.databaseVerseDao.insertAll(verse.asDatabase())

    }
}