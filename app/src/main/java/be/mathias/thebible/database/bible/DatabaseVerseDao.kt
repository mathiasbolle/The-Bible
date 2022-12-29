package be.mathias.thebible.database.bible

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseVerseDao {
    @Query("SELECT * FROM verse WHERE book_name = :book ORDER BY verse_id DESC")
    suspend fun getAllVersesFromBook(book: String): LiveData<List<DatabaseVerse>>

    @Query("SELECT * FROM verse WHERE verse_id = verse_id")
    suspend fun get(verseId: Int)

    @Insert
    suspend fun insert(verse: DatabaseVerse)

    @Query("DELETE FROM verse")
    suspend fun clear()

    @Query("SELECT COUNT(*) FROM verse")
    suspend fun numberOfVerses(): Int
}