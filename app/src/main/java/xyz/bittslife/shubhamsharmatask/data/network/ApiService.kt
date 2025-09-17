package xyz.bittslife.shubhamsharmatask.data.network

import retrofit2.http.GET
import xyz.bittslife.shubhamsharmatask.data.model.UserHoldingResponse

interface ApiService {
    @GET("/")
    suspend fun getHoldings(): UserHoldingResponse
}