package org.tmarcho.cronparser.exception

class InvalidNumberOfCronJobArgumentsException(numberOfArgs: Int)
    : RuntimeException("Invalid number of cron job arguments. Expected 6, got $numberOfArgs")
