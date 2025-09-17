package xyz.bittslife.shubhamsharmatask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.bittslife.shubhamsharmatask.data.model.UserHolding

@Dao
interface HoldingsDao {

    @Query("SELECT * FROM holdings")
    fun getAll(): List<UserHolding>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<UserHolding>)
}