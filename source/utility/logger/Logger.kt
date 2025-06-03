import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Extension function to create a logger for the specified class.
 * Uses the `LoggerFactory` to create a logger instance.
 *
 * @return A logger instance for the class.
 */
inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

/**
 * Logs an informational message using the logger associated with the source class.
 *
 * @param source The object or class from which the log is generated.
 * @param message The informational message to log.
 */
fun info(source: Any, message: String) {
    LoggerFactory.getLogger(source::class.java).info(message)
}

/**
 * Logs a warning message using the logger associated with the source class.
 *
 * @param source The object or class from which the log is generated.
 * @param message The warning message to log.
 */
fun warn(source: Any, message: String) {
    LoggerFactory.getLogger(source::class.java).warn(message)
}

/**
 * Logs an error message using the logger associated with the source class.
 *
 * @param source The object or class from which the log is generated.
 * @param message The error message to log.
 */
fun error(source: Any, message: String) {
    LoggerFactory.getLogger(source::class.java).error(message)
}
