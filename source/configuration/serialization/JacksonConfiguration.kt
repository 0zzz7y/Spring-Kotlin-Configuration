import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 * Configuration class for Jackson ObjectMapper.
 * This class provides beans for customizing JSON serialization and deserialization.
 */
@Suppress("unused")
@Configuration
class JacksonConfiguration {

    /**
     * Configures the primary `ObjectMapper` bean.
     * This mapper is used for general JSON serialization and deserialization.
     *
     * @return The configured `ObjectMapper` instance.
     */
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .registerModule(JavaTimeModule())
            .registerModule(kotlinModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    /**
     * Configures a specialized `ObjectMapper` bean for Redis serialization.
     * This mapper includes additional settings for handling polymorphic types and visibility.
     *
     * @return The configured `ObjectMapper` instance for Redis.
     */
    @Bean(name = ["redisObjectMapper"])
    @Qualifier("redisObjectMapper")
    fun redisObjectMapper(): ObjectMapper {
        return ObjectMapper()
            .registerModule(JavaTimeModule())
            .registerModule(kotlinModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.EVERYTHING,
                JsonTypeInfo.As.PROPERTY
            )
            .setVisibility(
                PropertyAccessor.ALL,
                JsonAutoDetect.Visibility.ANY
            )
    }

}
