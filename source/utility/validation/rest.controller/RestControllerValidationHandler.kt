import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * A global exception handler for validation errors in REST controllers.
 * Captures `MethodArgumentNotValidException` and returns a structured error response.
 */
@Suppress("unused")
@RestControllerAdvice
class ValidationHandler {

    /**
     * Handles `MethodArgumentNotValidException` thrown during validation of method arguments.
     * Extracts field errors and returns a `ResponseEntity` with a detailed error response.
     *
     * @param exception The exception containing validation errors.
     *
     * @return A `ResponseEntity` containing the error details, including status, message, and field-specific errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(exception: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errors: Map<String, String> = exception.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }

        val response: ResponseEntity<Map<String, Any>> = ResponseEntity.badRequest().body(
            mapOf(
                "status" to "error",
                "message" to "Validation failed",
                "errors" to errors
            )
        )

        return response
    }

}
