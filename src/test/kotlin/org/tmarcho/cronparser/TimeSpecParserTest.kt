package org.tmarcho.cronparser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.tmarcho.cronparser.model.SingleValueToken
import org.tmarcho.cronparser.model.TimeField
import org.tmarcho.cronparser.model.TimeSpec
import org.tmarcho.cronparser.model.WildcardToken

internal class TimeSpecParserTest {

    @Test
    fun `builds appropriate time spec`() {
        val timeSpec = TimeSpecParser.parse("*,1", TimeField.DAY_OF_WEEK)
        assertThat(timeSpec).isEqualTo(
            TimeSpec(
                timeField = TimeField.DAY_OF_WEEK,
                tokens = listOf(
                    WildcardToken(),
                    SingleValueToken(1),
                )
            )
        )
    }
}