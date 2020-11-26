# Cron Parser

Takes input in the form of a single [cron](https://en.wikipedia.org/wiki/Cron) job and outputs a time breakdown showing
when the job will run.

## Dependencies

- JDK 11 installed and available on classpath

## Overview

Built using:

- Kotlin 1.4 on JDK 11
- Gradle 6 for dependency/build orchestration
- JUnit 5, AssertJ for testing

## Run instructions

Build and run tests:

```
./gradlew clean build
```

Build executable jarfile:

```
./gradlew jar
```

Run the jar from command line with a single argument, containing the cron job spec:

```
java -jar build/libs/cron-parser-1.0-SNAPSHOT.jar "*/15 0 1,15 * 1-5 /user/bin/thing"
```

This will print the expected output to the terminal. 

If you want to run from an IDE, you can run the `org.tmarcho.cronparser.Application` class directly instead.

## Implementation

- Day of week goes from 0 - 6
- `Application` class provides the command-line entry point and performs basic validation to ensure the user input
  looks reasonably sensible.
- `CronParser` will step through the 'time fields' (minute, hour etc) and invoke `TimeSpecParser` on each time field
  input. It provides another method `getTerminalOutput` to produce the required string output.
- `TimeSpecTokenizer` does most of the hard work in parsing each time field input and mapping it to a list of 
  `TimeSpecTokens`. If it is unable to tokenize any part of the input an exception is raised.
- `TimeSpec` objects hold the mapping between a time field and a list of tokens. On construction these are validated
  together to ensure the tokens are valid for the given time field i.e. the user hasn't specified an index/range which 
  isn't within the bounds of the time field. 

## Limitations

- The scheduled command to run cannot contain spaces. Reasonably simple to fix this by changing how the command line 
  arguments are parsed.
- Some edge cases are not handled e.g. a range in the format `3-1` (where the start is larger than the end) will fail
  as no valid indices are produced. This would need additional validation during tokenization/parsing.
- If the user enters junk input at the command line, it would be nice if some usage information was returned instead of
  just an error, to give them a chance at fixing their input.