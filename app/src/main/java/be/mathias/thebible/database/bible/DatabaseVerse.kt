package be.mathias.thebible.database.bible

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.mathias.thebible.domain.Verse

@Entity(tableName = "verse")
data class DatabaseVerse(
    @ColumnInfo(name = "book_name")
    val bookName: String,
    @ColumnInfo(name = "verse_number")
    val verseNumber: Int,
    val chapter: Int,
    val text: String
    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "verse_id")
    var verseId: Int = 0
}

fun List<DatabaseVerse>.asDomain(): List<Verse> {
    return map {
        Verse(verseNumber = it.verseNumber, bookName = it.bookName, chapter = it.chapter, text = it.text)
    }
}