package xyz.bittslife.shubhamsharmatask.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class DataTest {
    @Test
    fun testDataModel() {
        val userHolding = UserHolding(100.0, 110.0, 105.0, 10, "TCS", 50.0)
        val data = Data(listOf(userHolding))
        assertEquals(1, data.userHolding.size)
        assertEquals(userHolding, data.userHolding[0])
    }
}
