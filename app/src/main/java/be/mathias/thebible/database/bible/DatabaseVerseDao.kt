package be.mathias.thebible.database.bible

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Defines methods for interacting with the *verse* table.
 * @see DatabaseVerse
 */
@Dao
interface DatabaseVerseDao {
    @Query("SELECT * FROM verse ORDER BY chapter DESC")
    fun getAll(): LiveData<List<DatabaseVerse>>

    @Query("SELECT * FROM verse WHERE book_name = :book ORDER BY verse_id DESC")
    fun getAllVersesFromBook(book: String): LiveData<List<DatabaseVerse>>

    @Query("SELECT * FROM verse WHERE verse_id = :verseId")
    fun get(verseId: Int): LiveData<DatabaseVerse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(verse: DatabaseVerse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(verses: Array<DatabaseVerse>)

    @Query("DELETE FROM verse")
    suspend fun clear()

    @Query("SELECT COUNT(*) FROM verse")
    suspend fun numberOfVerses(): Int
}