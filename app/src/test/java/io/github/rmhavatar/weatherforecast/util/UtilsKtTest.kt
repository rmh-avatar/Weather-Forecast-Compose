package io.github.rmhavatar.weatherforecast.util

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Date

class UtilsKtTest {

    @Test
    fun formatTime_get24hoursFormat_return12hoursFormat() {
        // UNIX time for July 15th, 2023 22:26:33 EDT
        val date = Date(1689474393941)
        val format = formatTime(date)
        assertEquals("10:26 PM", format)
    }

    @Test
    fun formatDateTime_get24hoursFormat_return12hoursFormat() {
        // UNIX time for July 15th, 2023 22:26:33 EDT
        val date = Date(1689474393941)
        val format = formatDateTime(date)
        assertEquals("Jul 15, 2023, 10:26 PM", format)
    }
}