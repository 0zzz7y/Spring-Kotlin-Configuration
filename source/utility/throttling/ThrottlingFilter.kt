import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

/**
 * A servlet filter for throttling incoming requests based on client IP.
 * Limits the number of requests a client can make within a specified time frame.
 */
@Component
class ThrottlingFilter : Filter {

    /**
     * A thread-safe map to store rate-limiting buckets for each client IP.
     */
    private val _buckets: MutableMap<String, Bucket> = ConcurrentHashMap()

    /**
     * Filters incoming requests to enforce throttling.
     * If the client exceeds the allowed request limit, a 429 status is returned.
     *
     * @param request The incoming servlet request.
     * @param response The outgoing servlet response.
     * @param chain The filter chain to pass the request/response to the next filter.
     */
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest: HttpServletRequest = request as HttpServletRequest
        val httpResponse: HttpServletResponse = response as HttpServletResponse
        val clientIp: String = getClientIp(httpRequest)
        val bucket: Bucket = _buckets.computeIfAbsent(clientIp) { createBucket() }

        if (bucket.tryConsume(1)) {
            chain?.doFilter(request, response)
        } else {
            httpResponse.status = 429
            httpResponse.writer.write("Too many requests. Try again later.")
        }
    }

    /**
     * Creates a new rate-limiting bucket with a predefined limit and refill rate.
     *
     * @return A new `Bucket` instance configured with the rate limit.
     */
    private fun createBucket(): Bucket {
        val limit: Bandwidth = Bandwidth.classic(
            MAXIMUM_REQUESTS_PER_MINUTE,
            Refill.greedy(MAXIMUM_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))
        )
        return Bucket.builder().addLimit(limit).build()
    }

    /**
     * Retrieves the client IP address from the request.
     * Checks the "X-Forwarded-For" header first, falling back to the remote address if not present.
     *
     * @param request The HTTP servlet request.
     * @return The client IP address as a string.
     */
    private fun getClientIp(request: HttpServletRequest): String {
        return request.getHeader("X-Forwarded-For") ?: request.remoteAddr
    }

}
