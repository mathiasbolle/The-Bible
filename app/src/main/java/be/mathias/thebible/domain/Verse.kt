package be.mathias.thebible.domain

/**
 * Domain class 'Verse' that is only know by The Bible application.
 */
data class Verse(
    val verseNumber: Int,
    val bookName: String,
    val chapter: Int,
    val text: String,
)