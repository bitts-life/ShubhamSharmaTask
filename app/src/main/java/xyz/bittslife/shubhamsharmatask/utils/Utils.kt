package xyz.bittslife.shubhamsharmatask.utils

import java.text.NumberFormat
import java.util.Locale

object Utils {
    fun formatNumberWithCommas(number: Double): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2 // Ensure at least 2 decimal places
            maximumFractionDigits = 2 // Ensure at most 2 decimal places
        }
        return numberFormat.format(number)
    }
}