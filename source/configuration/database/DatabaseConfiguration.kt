import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

/**
 * Configuration class for database settings.
 * This class sets up the primary `DataSource` bean and enables JPA repositories for specific packages.
 */
@Suppress("unused")
@Configuration
@EnableJpaRepositories(
    basePackages = [
        TODO
    ]
)
class DatabaseConfiguration(

    /**
     * The URL of the database, injected from the application properties.
     */
    @Value("\${spring.datasource.url}") private val _url: String,

    /**
     * The driver class name for the database, injected from the application properties.
     */
    @Value("\${spring.datasource.driver-class-name}") private val _driverClassName: String,

    /**
     * The username for the database connection, injected from the application properties.
     * Defaults to an empty string if not provided.
     */
    @Value("\${spring.datasource.username:}") private val _username: String?,

    /**
     * The password for the database connection, injected from the application properties.
     * Defaults to an empty string if not provided.
     */
    @Value("\${spring.datasource.password:}") private val _password: String?
) {

    /**
     * Creates and configures the primary `DataSource` bean.
     *
     * @return The configured `DataSource` instance.
     */
    @Bean
    @Primary
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.url = _url
        dataSource.setDriverClassName(_driverClassName)

        if (!_username.isNullOrBlank()) dataSource.username = _username
        if (!_password.isNullOrBlank()) dataSource.password = _password

        return dataSource
    }

}
