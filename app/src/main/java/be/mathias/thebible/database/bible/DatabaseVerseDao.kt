package be.mathias.thebible.database.bible

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for interacting with the *verse* table.
 * @see DatabaseVerse
 */
@Dao
interface DatabaseVerseDao {
    @Query("SELECT * FROM verse ORDER BY chapter DESC")
    fun getAll(): LiveData<List<DatabaseVerse>>

    @Query("SELECT * FROM verse WHERE is_favorite = 1 ORDER BY chapter DESC")
    fun getFavorites(): LiveData<List<DatabaseVerse>>

    @Query("SELECT * FROM verse WHERE verse_id = :verseId")
    fun get(verseId: Int): LiveData<DatabaseVerse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(verse: DatabaseVerse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(verses: Array<DatabaseVerse>)

    @Query("DELETE FROM verse")
    suspend fun clear()

    @Query("UPDATE verse SET is_favorite = 1 WHERE verse_id = :verseId")
    suspend fun updateVerseMakeFavorite(verseId: Int)

    // SELECT verse_id FROM verse WHERE book_name = "John" AND verse_number = 1 AND chapter = 2
    @Query("SELECT verse_id FROM verse WHERE book_name = :bookName AND verse_number = :verseNumber AND chapter = :chapter")
    suspend fun getId(bookName: String, verseNumber: Int, chapter: Int): Int

}