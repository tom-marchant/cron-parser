package org.tmarcho.cronparser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.tmarcho.cronparser.exception.InvalidTimeSpecificationException
import org.tmarcho.cronparser.model.*

internal class TimeSpecTokenizerTest {

    @Test
    fun `captures wildcard`() {
        val tokens = TimeSpecTokenizer.tokenize("*", TimeField.DAY_OF_WEEK)
        assertThat(tokens).isEqualTo(listOf(
            WildcardToken()
        ))
    }

    @Test
    fun `captures single value`() {
        val tokens = TimeSpecTokenizer.tokenize("2", TimeField.DAY_OF_WEEK)
        assertThat(tokens).isEqualTo(listOf(
            SingleValueToken(2)
        ))
    }

    @Test
    fun `captures range`() {
        val tokens = TimeSpecTokenizer.tokenize("1-5", TimeField.DAY_OF_WEEK)
        assertThat(tokens).isEqualTo(listOf(
            RangeToken(1, 5)
        ))
    }

    @Test
    fun `captures step`() {
        val tokens = TimeSpecTokenizer.tokenize("*/15", TimeField.MINUTE)
        assertThat(tokens).isEqualTo(listOf(
            StepToken(15)
        ))
    }

    @Test
    fun `captures many single values`() {
        val tokens = TimeSpecTokenizer.tokenize("1,2,5", TimeField.DAY_OF_WEEK)
        assertThat(tokens).isEqualTo(listOf(
            SingleValueToken(1),
            SingleValueToken(2),
            SingleValueToken(5)
        ))
    }

    @Test
    fun `captures step and single values`() {
        val tokens = TimeSpecTokenizer.tokenize("*/2,1,3", TimeField.DAY_OF_WEEK)
        assertThat(tokens).isEqualTo(listOf(
            StepToken(2),
            SingleValueToken(1),
            SingleValueToken(3)
        ))
    }

    @Test
    fun `captures range and single values`() {
        val tokens = TimeSpecTokenizer.tokenize("1-5,1,3", TimeField.DAY_OF_WEEK)
        assertThat(tokens).isEqualTo(listOf(
            RangeToken(1, 5),
            SingleValueToken(1),
            SingleValueToken(3)
        ))
    }

    @Test
    fun `captures wildcard, range, step and single values`() {
        val tokens = TimeSpecTokenizer.tokenize("*,1-5,*/7,1,3", TimeField.DAY_OF_WEEK)
        assertThat(tokens).isEqualTo(listOf(
            WildcardToken(),
            RangeToken(1, 5),
            StepToken(7),
            SingleValueToken(1),
            SingleValueToken(3)
        ))
    }

    @Test
    fun `fail for invalid time specification`() {
        val exception = assertThrows<InvalidTimeSpecificationException> {
            TimeSpecTokenizer.tokenize("bloop", TimeField.MINUTE)
        }

        assertThat(exception).hasMessage("Invalid time specification 'bloop' for field: minute")
    }
}