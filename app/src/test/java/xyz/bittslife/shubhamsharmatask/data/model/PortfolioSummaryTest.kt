package xyz.bittslife.shubhamsharmatask.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PortfolioSummaryTest {
    @Test
    fun testPortfolioSummaryModel() {
        val summary = PortfolioSummary(1000.0, 800.0, 200.0, 20.0)
        assertEquals(1000.0, summary.currentValue, 0.0)
        assertEquals(800.0, summary.totalInvestment, 0.0)
        assertEquals(200.0, summary.totalPNL, 0.0)
        assertEquals(20.0, summary.todaysPNL, 0.0)
    }
}
