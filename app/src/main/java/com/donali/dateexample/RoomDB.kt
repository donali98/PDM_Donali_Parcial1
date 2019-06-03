package com.donali.dateexample

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Database(
    entities = [Persona::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TimestampConrverter::class)
public abstract class RoomDB : RoomDatabase() {
    abstract fun personaDao(): PersonaDao


    private class RoomDBCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDB(database.personaDao())
                }
            }
        }

        suspend fun populateDB(
            personaDao: PersonaDao
        ) {
            personaDao.deleteAll()

            val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE,1)
            val id = personaDao.insert(Persona("dios",dateFormat.parse(dateFormat.format(cal.time))))
            Log.d("CUSTOM",id.toString())
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getInstance(
            appContext: Context,
            scope: CoroutineScope
        )
                : RoomDB {
            val tmp = INSTANCE
            if (tmp != null) return tmp
            synchronized(this) {
                val instance = Room.databaseBuilder(appContext, RoomDB::class.java, "BasketApp")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDBCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}