package org.tmarcho.cronparser.model

enum class TimeField(
    val label: String,
    val min: Int,
    val max: Int
) {
    MINUTE("minute", 0, 59),
    HOUR("hour", 0, 23),
    DAY_OF_MONTH("day of month", 1, 31),
    MONTH("month", 1, 12),
    DAY_OF_WEEK("day of week", 0, 6)
}