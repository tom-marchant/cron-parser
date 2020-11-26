package org.tmarcho.cronparser.exception

import org.tmarcho.cronparser.model.TimeField
import java.lang.RuntimeException

class InvalidTimeSpecificationException(rawValue: String, timeField: TimeField) :
    RuntimeException("Invalid time specification '$rawValue' for field: ${timeField.label}")
