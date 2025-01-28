package cz.mendelu.todost11.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.mendelu.todost11.model.Transaction
import cz.mendelu.todost11.model.UserSettings

@Database(entities = [Transaction::class,UserSettings::class], version = 16, exportSchema = true)
abstract class BankDatabase : RoomDatabase() {

    abstract fun tasksDao(): BankDao

    companion object {
        private var INSTANCE: BankDatabase? = null

        fun getDatabase(context: Context): BankDatabase {
            if (INSTANCE == null) {
                synchronized(BankDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            BankDatabase::class.java, "tasks_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}