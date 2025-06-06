package net.lag129.mastodon.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.lag129.mastodon.data.converter.StatusConverters
import net.lag129.mastodon.data.model.Status

@Database(
    entities = [Status::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StatusConverters::class)
abstract class MastodonDatabase : RoomDatabase() {
    abstract fun statusDao(): StatusDao

    companion object {
        @Volatile
        private var INSTANCE: MastodonDatabase? = null

        fun getDatabase(context: Context): MastodonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MastodonDatabase::class.java,
                    "mastodon_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}