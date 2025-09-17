package xyz.bittslife.shubhamsharmatask.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class UserHoldingResponseTest {
    @Test
    fun testUserHoldingResponseModel() {
        val holding = UserHolding(100.0, 110.0, 105.0, 10, "TCS", 50.0)
        val data = Data(listOf(holding))
        val response = UserHoldingResponse(data)
        assertEquals(data, response.data)
    }
}
