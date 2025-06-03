import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS) settings.
 * This class customizes the CORS mappings for the application.
 */
@Suppress("unused")
@Configuration
class CorsConfiguration : WebMvcConfigurer {

    /**
     * Configures CORS mappings for the application.
     *
     * @param registry The `CorsRegistry` used to define CORS settings.
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "Allowed origins here!"
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
    }

}
