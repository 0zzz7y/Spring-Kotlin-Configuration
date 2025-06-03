import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for registering the throttling filter.
 * Sets up the `ThrottlingFilter` to apply rate-limiting to all incoming requests.
 */
@Suppress("unused")
@Configuration
class ThrottlingFilterConfiguration {

    /**
     * Registers the `ThrottlingFilter` with the servlet container.
     * Configures the filter to apply to all URL patterns and sets its order of execution.
     *
     * @param filter The `ThrottlingFilter` instance to register.
     *
     * @return A `FilterRegistrationBean` configured with the provided filter.
     */
    fun rateLimitingFilter(filter: ThrottlingFilter): FilterRegistrationBean<ThrottlingFilter> {
        val registration: FilterRegistrationBean<ThrottlingFilter> = FilterRegistrationBean<ThrottlingFilter>(filter)
        registration.order = 1
        registration.addUrlPatterns("/*")
        return registration
    }

}
