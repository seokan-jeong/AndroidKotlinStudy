package com.seokan.calculator.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.seokan.calculator.model.History

@Dao
interface HistoryDao{

    @Query("select * from history")
    fun getAll(): List<History>

    @Insert
    fun insertHistory(history: History)

    @Query("delete from history")
    fun deleteAll()

}