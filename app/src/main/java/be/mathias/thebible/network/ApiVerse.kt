package be.mathias.thebible.network

import be.mathias.thebible.database.bible.DatabaseVerse
import com.squareup.moshi.Json

/**
 * Data class that represents a JSON request that contains a list of
 * verses.
 * @see ApiVerse
 */
data class ApiVerseContainer(
    @Json(name = "verses")
    val apiVerses: List<ApiVerse>
)

/**
 * Data class that represents a JSON request that contains a *single*
 * verse.
 */
data class ApiVerse(
    @Json(name = "verse")
    val verseNumber: Int,
    @Json(name = "book_name")
    val bookName: String = "",
    @Json(name = "chapter")
    val chapter: Int,
    @Json(name = "text")
    val text: String = ""
)

/**
 * Converts a JSON request (that is wrapped inside the ApiVerseContainer
 * to an array of DatabaseVerse (objects that represents records in the Verse table).
 * @see DatabaseVerse
 * @see ApiVerseContainer
 */
fun ApiVerseContainer.asDatabase(): Array<DatabaseVerse> {
    return apiVerses.map {
        DatabaseVerse(
            bookName = it.bookName,
            chapter = it.chapter,
            text = it.text,
            verseNumber = it.verseNumber,
        )
    }.toTypedArray()
}