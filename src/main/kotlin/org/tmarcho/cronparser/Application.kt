package org.tmarcho.cronparser

import org.tmarcho.cronparser.exception.InvalidNumberOfCommandLineArgumentsException
import org.tmarcho.cronparser.exception.InvalidNumberOfCronJobArgumentsException

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size != 1) {
            throw InvalidNumberOfCommandLineArgumentsException(args.size)
        }

        val cronJobArgs = args.first().split(" ")

        if (cronJobArgs.size != 6) {
            throw InvalidNumberOfCronJobArgumentsException(cronJobArgs.size)
        }

        val parser = CronParser(cronJobArgs.subList(0, 5), cronJobArgs.last())

        println(parser.getTerminalOutput())
    }
}