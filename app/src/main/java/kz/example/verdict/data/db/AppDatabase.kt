package kz.example.verdict.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kz.example.verdict.data.db.dao.ItemDao
import kz.example.verdict.data.db.dao.VerdictDao
import kz.example.verdict.data.db.dao.SavedDao
import kz.example.verdict.data.db.entities.Item
import kz.example.verdict.data.db.entities.Verdict
import kz.example.verdict.data.db.entities.SavedItem

@Database(
    entities = [Item::class, Verdict::class, SavedItem::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun verdictDao(): VerdictDao
    abstract fun savedDao(): SavedDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "verdict_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
