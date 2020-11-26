package org.tmarcho.cronparser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.tmarcho.cronparser.exception.InvalidNumberOfCronJobArgumentsException
import org.tmarcho.cronparser.exception.InvalidNumberOfTimeFieldArgumentsException

class CronParserTest {

    @Test
    fun `fail if time field arguments size does not equal 5`() {
        assertThrows<InvalidNumberOfTimeFieldArgumentsException> {
            CronParser(listOf("1", "1", "1", "1"), command = "/usr/bin/find")
        }
    }

    @Test
    fun `print 1 for every time field`() {
        val parser = CronParser(listOf("1", "1", "1", "1", "1"), command = "/usr/bin/find")

        assertThat(parser.getTerminalOutput()).isEqualTo("""
            minute        1
            hour          1
            day of month  1
            month         1
            day of week   1
            command       /usr/bin/find
        """.trimIndent())
    }

    @Test
    fun `print a mixture of time specs`() {
        val parser = CronParser(listOf("*/15", "0", "1,15", "*", "1-5"), command = "/usr/bin/find")

        assertThat(parser.getTerminalOutput()).isEqualTo("""
            minute        0 15 30 45
            hour          0
            day of month  1 15
            month         1 2 3 4 5 6 7 8 9 10 11 12
            day of week   1 2 3 4 5
            command       /usr/bin/find
        """.trimIndent())
    }
}