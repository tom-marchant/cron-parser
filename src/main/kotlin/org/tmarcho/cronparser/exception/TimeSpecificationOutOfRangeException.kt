package org.tmarcho.cronparser.exception

import org.tmarcho.cronparser.model.TimeField

class TimeSpecificationOutOfRangeException(timeField: TimeField)
    : RuntimeException("Time specification out of range for field: ${timeField.label}. " +
        "Must be between ${timeField.min} and ${timeField.max} inclusive")
