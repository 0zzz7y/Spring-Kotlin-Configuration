import org.springframework.data.jpa.repository.JpaRepository

/**
 * Extension function for `JpaRepository` to find an entity by its ID or throw an exception if not found.
 *
 * @param T The type of the entity.
 * @param ID The type of the entity's ID.
 * @param id The ID of the entity to find.
 * @param field The name of the field used for logging purposes (default is "id").
 *
 * @return The entity if found.
 *
 * @throws NoSuchElementException if the entity is not found.
 */
inline fun <reified T, ID : Any> JpaRepository<T, ID>.findOrThrow(
    id: ID,
    field: String = "id"
): T {
    return this.findById(id).orElseThrow {
        logger().error("${T::class.simpleName} with $field=$id not found.")
        NoSuchElementException("${T::class.simpleName} not found.")
    }
}

/**
 * Extension function for `JpaRepository` to assert that an entity with a given name does not already exist.
 *
 * @param T The type of the entity.
 * @param name The name to check for existence.
 * @param existsByName A lambda function to check if an entity with the given name exists.
 *
 * @throws IllegalArgumentException if an entity with the given name already exists.
 */
inline fun <reified T> JpaRepository<T, *>.assertNameDoesNotExist(
    name: String,
    existsByName: (String) -> Boolean
) {
    if (existsByName(name)) {
        logger().error("${T::class.simpleName} with name='$name' already exists.")
        throw IllegalArgumentException("${T::class.simpleName} with this name already exists.")
    }
}
