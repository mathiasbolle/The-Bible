package be.mathias.thebible.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.mathias.thebible.database.bible.DatabaseVerse
import be.mathias.thebible.database.bible.DatabaseVerseDao

/**
 * Defines The Bible Room database that contains:
 * - Configurations such as *databaseVerseDao*
 * - Singleton that handles the instance of the DB.
 * @see RoomDatabase
 */
@Database(entities = [DatabaseVerse::class], version = 1, exportSchema = false)
abstract class BibleDatabase : RoomDatabase() {

    abstract val databaseVerseDao: DatabaseVerseDao

    companion object {
        @Volatile
        private var INSTANCE: BibleDatabase? = null

        fun getInstance(context: Context): BibleDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BibleDatabase::class.java,
                        "Bible"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}