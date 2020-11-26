package org.tmarcho.cronparser.exception

class InvalidNumberOfCommandLineArgumentsException(numberOfArgs: Int)
    : RuntimeException("Invalid number of command line arguments. Expected 1, got $numberOfArgs")
