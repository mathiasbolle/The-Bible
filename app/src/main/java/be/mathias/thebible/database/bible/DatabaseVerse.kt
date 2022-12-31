package be.mathias.thebible.database.bible

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import be.mathias.thebible.domain.Verse

/**
 * Defines table 'verse'.
 * each instance of this class is a record in the table.
 * Add index because otherwise we will get the same record
 * in the room DB twice (even with OnConflictStrategy.REPLACE)
 */
@Entity(
    indices = [Index( value = ["book_name", "verse_number", "chapter"], unique = true)],
    tableName = "verse")
data class DatabaseVerse(
    @PrimaryKey
    @ColumnInfo(name = "verse_id")
    var verseId: Int? = null,
    @ColumnInfo(name = "book_name")
    val bookName: String,
    @ColumnInfo(name = "verse_number")
    val verseNumber: Int,
    val chapter: Int,
    val text: String,
    ){
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
}

/**
 * Converts a list of DatabaseVerse objects (objects that represents records in the Verse table)
 * to a list of Verse objects (domain objects that only the application itself knows about).
 * @see DatabaseVerse
 * @see Verse
 */
fun List<DatabaseVerse>.asDomain(): List<Verse> {
    return map {
        Verse(verseNumber = it.verseNumber, bookName = it.bookName, chapter = it.chapter, text = it.text)
    }
}