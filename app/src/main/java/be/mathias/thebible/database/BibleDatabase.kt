package be.mathias.thebible.database

import androidx.room.Database
import androidx.room.RoomDatabase
import be.mathias.thebible.database.bible.DatabaseVerse
import be.mathias.thebible.database.bible.DatabaseVerseDao

@Database(entities = [DatabaseVerse::class], version = 1, exportSchema = false)
abstract class BibleDatabase: RoomDatabase() {

    abstract val databaseVerseDao: DatabaseVerseDao
}