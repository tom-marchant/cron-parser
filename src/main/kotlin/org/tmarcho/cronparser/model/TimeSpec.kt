package org.tmarcho.cronparser.model

import org.tmarcho.cronparser.exception.TimeSpecificationOutOfRangeException

data class TimeSpec(
    val timeField: TimeField,
    val tokens: List<TimeSpecToken>
) {

    val indices: List<Int> = buildIndices()

    init {
        validate()
    }

    private fun buildIndices(): List<Int> {
        return tokens.map { token ->
            when (token) {
                is SingleValueToken -> listOf(token.value)
                is WildcardToken -> (timeField.min..timeField.max).map { it }
                is RangeToken -> (token.from..token.to).map { it }
                is StepToken -> (timeField.min..timeField.max step token.step).map { it }
            }
        }
            .flatten()
            .toSet()
            .sorted()
    }

    private fun validate() {
        if (indices.first() < timeField.min || indices.last() > timeField.max) {
            throw TimeSpecificationOutOfRangeException(timeField)
        }
    }
}