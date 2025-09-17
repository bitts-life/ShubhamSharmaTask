package com.bittslife.shubhamsharmademotest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.bittslife.shubhamsharmatask.data.db.HoldingsDao
import xyz.bittslife.shubhamsharmatask.data.model.UserHolding

@Database(entities = [UserHolding::class], version = 1, exportSchema = false)
abstract class HoldingsDatabase : RoomDatabase() {
    abstract fun holdingsDao(): HoldingsDao
}
