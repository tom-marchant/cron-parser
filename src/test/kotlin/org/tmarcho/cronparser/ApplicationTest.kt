package org.tmarcho.cronparser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.tmarcho.cronparser.exception.InvalidNumberOfCommandLineArgumentsException
import org.tmarcho.cronparser.exception.InvalidNumberOfCronJobArgumentsException

internal class ApplicationTest {

    @Test
    fun `parse valid command line input without error`() {
        val args = arrayOf("*/15 0 1,15 * 1-5 /usr/bin/find")

        Application.main(args)
    }

    @Test
    fun `fails if number of arguments not equal to 1`() {
        val args = arrayOf("*/15 0 1,15 * 1-5", "/usr/bin/find")

        val exception = assertThrows<InvalidNumberOfCommandLineArgumentsException> {
            Application.main(args)
        }

        assertThat(exception).hasMessage("Invalid number of command line arguments. Expected 1, got 2")
    }

    @Test
    fun `fails if number of cron job arguments not equal to 6`() {
        val args = arrayOf("1 1 1 1 /usr/bin/find")

        val exception = assertThrows<InvalidNumberOfCronJobArgumentsException> {
            Application.main(args)
        }

        assertThat(exception).hasMessage("Invalid number of cron job arguments. Expected 6, got 5")
    }
}