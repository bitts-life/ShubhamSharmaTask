package xyz.bittslife.shubhamsharmatask.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {
    @Test
    fun testFormatNumberWithCommas() {
        // Test with integer value
        assertEquals("1,000.00", Utils.formatNumberWithCommas(1000.0))
        // Test with decimal value
        assertEquals("12,345.68", Utils.formatNumberWithCommas(12345.678))
        // Test with small value
        assertEquals("0.12", Utils.formatNumberWithCommas(0.123))
        // Test with large value
        assertEquals("1,000,000.00", Utils.formatNumberWithCommas(1_000_000.0))
        // Test with negative value
        assertEquals("-1,234.57", Utils.formatNumberWithCommas(-1234.567))
    }
}
