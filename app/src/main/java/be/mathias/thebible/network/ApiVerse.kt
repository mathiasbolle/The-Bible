package be.mathias.thebible.network

import be.mathias.thebible.database.bible.DatabaseVerse
import be.mathias.thebible.domain.Verse
import com.squareup.moshi.Json

data class ApiVerseContainer(
    @Json(name = "verses")
    val apiVerses: List<ApiVerse>
)

data class ApiVerse(
    @Json(name = "verse")
    val verseId: Int,
    @Json(name = "book_name")
    val bookName: String = "",
    @Json(name = "chapter")
    val chapter: Int,
    @Json(name = "text")
    val text: String = ""
)

/**
 * Covert a network result into domain object (verse)
 */

fun ApiVerseContainer.asDomain(): List<Verse> {
    return apiVerses.map {
        Verse(
            verseId = it.verseId,
            bookName = it.bookName,
            chapter = it.chapter,
            text = it.text
        )
    }
}

fun ApiVerseContainer.asDatabase(): Array<DatabaseVerse> {
    return apiVerses.map {
        DatabaseVerse(
            verseId = it.verseId,
            bookName = it.bookName,
            chapter = it.chapter,
            text = it.text
        )
    }.toTypedArray()
}