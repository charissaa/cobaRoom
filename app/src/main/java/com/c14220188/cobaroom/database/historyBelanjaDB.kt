package com.c14220188.cobaroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [historyBelanja::class], version = 1)
abstract class historyBelanjaDB : RoomDatabase() {
    abstract fun funhistoryBelanjaDAO() : daftarBelanjaDAO

    companion object {
        @Volatile
        private var INSTANCE: historyBelanjaDB? = null

        @JvmStatic
        fun getDatabase(context: Context) : historyBelanjaDB {
            if (INSTANCE == null) {
                synchronized(historyBelanjaDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        historyBelanjaDB::class.java, "historyBelanja_DB"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as historyBelanjaDB
        }
    }
}