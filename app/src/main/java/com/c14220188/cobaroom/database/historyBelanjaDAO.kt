package com.c14220188.cobaroom.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c14220188.cobaroom.History

@Dao
interface historyBelanjaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: historyBelanja)

    @Delete
    fun delete(daftar: historyBelanja)

    @Query("SELECT * FROM historyBelanja ORDER BY id ASC")
    fun selectAll(): MutableList<historyBelanja>
}