package com.seokan.calculator

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seokan.calculator.dao.HistoryDao
import com.seokan.calculator.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}