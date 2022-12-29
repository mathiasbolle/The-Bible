package be.mathias.thebible.database.bible

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "verse")
data class DatabaseVerse(
    @PrimaryKey
    @ColumnInfo(name = "verse_id")
    val verseId: Int,
    @ColumnInfo(name = "book_name")
    val bookName: String,
    val chapter: Int,
    val text: String)
