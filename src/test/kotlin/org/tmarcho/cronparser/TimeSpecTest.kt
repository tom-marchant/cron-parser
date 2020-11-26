package org.tmarcho.cronparser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.tmarcho.cronparser.exception.TimeSpecificationOutOfRangeException
import org.tmarcho.cronparser.model.*

internal class TimeSpecTest {

    @Test
    fun `returns expected indices for wildcard`() {
        val timeSpec = TimeSpec(
            timeField = TimeField.DAY_OF_WEEK,
            tokens = listOf(
                WildcardToken()
            )
        )
        assertThat(timeSpec.indices).isEqualTo(listOf(0,1,2,3,4,5,6))
    }

    @Test
    fun `returns expected indices for single values`() {
        val timeSpec = TimeSpec(
            timeField = TimeField.DAY_OF_MONTH,
            tokens = listOf(
                SingleValueToken(2),
                SingleValueToken(5)
            )
        )
        assertThat(timeSpec.indices).isEqualTo(listOf(2,5))
    }

    @Test
    fun `returns expected indices for range`() {
        val timeSpec = TimeSpec(
            timeField = TimeField.DAY_OF_MONTH,
            tokens = listOf(
                RangeToken(3, 7)
            )
        )
        assertThat(timeSpec.indices).isEqualTo(listOf(3,4,5,6,7))
    }

    @Test
    fun `returns expected indices for step`() {
        val timeSpec = TimeSpec(
            timeField = TimeField.MINUTE,
            tokens = listOf(
                StepToken(15)
            )
        )
        assertThat(timeSpec.indices).isEqualTo(listOf(0,15,30,45))
    }

    @Test
    fun `orders indices`() {
        val timeSpec = TimeSpec(
            timeField = TimeField.DAY_OF_MONTH,
            tokens = listOf(
                SingleValueToken(15),
                RangeToken(3, 5),
                SingleValueToken(10)
            )
        )
        assertThat(timeSpec.indices).isEqualTo(listOf(3,4,5,10,15))
    }

    @Test
    fun `de-dupes indices`() {
        val timeSpec = TimeSpec(
            timeField = TimeField.DAY_OF_MONTH,
            tokens = listOf(
                RangeToken(1, 5),
                SingleValueToken(2),
                SingleValueToken(3),
                SingleValueToken(6)
            )
        )
        assertThat(timeSpec.indices).isEqualTo(listOf(1,2,3,4,5,6))
    }

    @Test
    fun `fails for items above eligible index range`() {
        val exception = assertThrows<TimeSpecificationOutOfRangeException> {
            TimeSpec(
                timeField = TimeField.DAY_OF_WEEK,
                tokens = listOf(
                    SingleValueToken(7)
                )
            )
        }

        assertThat(exception).hasMessage("Time specification out of range for field: day of week. " +
                "Must be between 0 and 6 inclusive")
    }

    @Test
    fun `fails for items below eligible index range`() {
        val exception = assertThrows<TimeSpecificationOutOfRangeException> {
            TimeSpec(
                timeField = TimeField.DAY_OF_MONTH,
                tokens = listOf(
                    SingleValueToken(0)
                )
            )
        }

        assertThat(exception).hasMessage("Time specification out of range for field: day of month. " +
                "Must be between 1 and 31 inclusive")
    }
}