package xyz.bittslife.shubhamsharmatask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "holdings")
data class UserHolding(
    @PrimaryKey val avgPrice: Double,
    val close: Double,
    val ltp: Double,
    val quantity: Int,
    val symbol: String,
    val pnl: Double
)