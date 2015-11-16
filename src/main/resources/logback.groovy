import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.INFO

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} - %msg%n"
    }
}
logger("geoand.docker.events", INFO, ["STDOUT"], false)
logger("org.reflections", ERROR, ["STDOUT"], false)
root(INFO, ["STDOUT"])
