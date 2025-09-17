package xyz.bittslife.shubhamsharmatask.data.model

data class UserHolding(
    val avgPrice: Double,
    val close: Double,
    val ltp: Double,
    val quantity: Int,
    val symbol: String,
    val pnl: Double
)