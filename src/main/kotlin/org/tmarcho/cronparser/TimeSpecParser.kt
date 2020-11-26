package org.tmarcho.cronparser

import org.tmarcho.cronparser.model.TimeField
import org.tmarcho.cronparser.model.TimeSpec

object TimeSpecParser {

    fun parse(rawValue: String, timeField: TimeField): TimeSpec {
        val tokens = TimeSpecTokenizer.tokenize(rawValue, timeField)
        return TimeSpec(
            timeField = timeField,
            tokens = tokens
        )
    }
}
