package net.azarquiel.ahorcadogame.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Espanol::class], version = 1)
abstract class PalabrasDB: RoomDatabase() {
    abstract fun espanolDao(): EspanolDao
    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE:  PalabrasDB? = null

        fun getDatabase(context: Context): PalabrasDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PalabrasDB::class.java,   "palabras.sqlite"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
