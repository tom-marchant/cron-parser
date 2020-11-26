package org.tmarcho.cronparser

import org.tmarcho.cronparser.exception.InvalidNumberOfTimeFieldArgumentsException
import org.tmarcho.cronparser.model.TimeField
import org.tmarcho.cronparser.model.TimeSpec

class CronParser(
    private val rawTimeArgs: List<String>,
    private val command: String
) {

    private val timeSpecs: List<TimeSpec>

    init {
        if (rawTimeArgs.size != 5) {
            throw InvalidNumberOfTimeFieldArgumentsException()
        }

        timeSpecs = TimeField.values().mapIndexed { i, timeField ->
            TimeSpecParser.parse(
                rawValue = rawTimeArgs[i],
                timeField = timeField
            )
        }
    }

    fun getTerminalOutput(): String {
        return StringBuilder().apply {
            timeSpecs.forEach {
                append(it.timeField.label.padEnd(PADDING_LENGTH))
                append(it.indices.joinToString(" "))
                append("\n")
            }

            append("command".padEnd(PADDING_LENGTH))
            append(command)
        }.toString()
    }

    companion object {
        private const val PADDING_LENGTH = 14
    }
}
