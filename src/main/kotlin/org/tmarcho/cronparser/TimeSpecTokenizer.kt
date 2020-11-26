package org.tmarcho.cronparser

import org.tmarcho.cronparser.exception.InvalidTimeSpecificationException
import org.tmarcho.cronparser.model.*

// Breaks up a single time field specification into strongly-typed tokens.
//
// E.g. input of "1-4,7,10" would yield a list of [RangeToken, SingleValueToken, SingleValueToken]
object TimeSpecTokenizer {

    fun tokenize(input: String, timeField: TimeField): List<TimeSpecToken> {
        return input
            .split(",")
            .map { rawToken ->
                when (rawToken) {
                    "*" -> WildcardToken()
                    in Regex("\\d+") -> parseSingleValue(rawToken)
                    in Regex("\\d+-\\d+") -> parseRange(rawToken)
                    in Regex("\\*/\\d+") -> parseStep(rawToken)
                    else -> throw InvalidTimeSpecificationException(rawToken, timeField)
                }
            }
    }

    // Allows use of regex matcher in when statement
    private operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)

    private fun parseSingleValue(rawToken: String) = SingleValueToken(rawToken.toInt())

    private fun parseStep(rawValue: String): StepToken {
        val step = rawValue.substring(2).toInt() // Grab everything after the '*/'
        return StepToken(step)
    }

    private fun parseRange(rawValue: String): RangeToken {
        val minMax = rawValue.split("-").map { it.toInt() }

        return RangeToken(
            from = minMax[0],
            to = minMax[1]
        )
    }
}


