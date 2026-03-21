package com.brunobs.shared;

/**
 * Base interface for all catalog-type DTOs.
 * Ensures compatibility with BaseTypeMapper and provides a fluent API for ID assignment.
 *
 * @param <T> The concrete DTO type for withId (Wither) pattern
 * @param <ID> The type of the identifier (usually Long)
 */
public interface BaseTypeDTO<T extends BaseTypeDTO<T, ID>, ID> extends BaseDTO<String, ID> {

    ID id();

    String name();

    String label();

    String description();

    Integer sortOrder();

    boolean active();

    /**
     * Wither-style method to return a new instance with the given ID.
     */
    T withId(ID id);

    @Override
    default ID registrationIdentifier() {
        return id();
    }

    @Override
    default String registrationName() {
        return name();
    }
}
