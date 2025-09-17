package xyz.bittslife.shubhamsharmatask.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class UserHoldingTest {
    @Test
    fun testUserHoldingModel() {
        val holding = UserHolding(100.0, 110.0, 105.0, 10, "TCS", 50.0)
        assertEquals(100.0, holding.avgPrice, 0.0)
        assertEquals(110.0, holding.close, 0.0)
        assertEquals(105.0, holding.ltp, 0.0)
        assertEquals(10, holding.quantity)
        assertEquals("TCS", holding.symbol)
        assertEquals(50.0, holding.pnl, 0.0)
    }
}
