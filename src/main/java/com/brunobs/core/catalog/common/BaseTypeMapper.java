package com.brunobs.core.catalog.common;


import com.brunobs.shared.base.BaseMapper;
import com.brunobs.shared.base.BaseTypeDTO;

/**
 * Abstract mapper for catalog-type entities.
 * Centralizes the conversion logic for common fields like Name, Description, and Sort Order.
 *
 * @param <D>  The DTO type extending BaseTypeDTO
 * @param <E>  The Entity type extending BaseType
 * @param <ID> The Identifier type (usually Long)
 */
public abstract class BaseTypeMapper<
        D extends BaseTypeDTO<D, ID>,
        E extends BaseType,
        ID
        > implements BaseMapper<D, E> {

    private final Class<E> entityClass;

    protected BaseTypeMapper(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Creates a new instance of the entity using reflection.
     */
    protected E createEntityInstance() {
        try {
            return entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate entity: " + entityClass.getSimpleName(), e);
        }
    }

    @Override
    public E toEntity(D dto) {
        if (dto == null) return null;

        E entity = createEntityInstance();
        mapCommonFields(entity, dto);

        return entity;
    }

    @Override
    public void updateEntity(E entity, D dto) {
        if (entity == null || dto == null) return;
        mapCommonFields(entity, dto);
    }

    /**
     * Helper to map shared fields between Entity and DTO.
     */
    private void mapCommonFields(E entity, D dto) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setLabel(dto.label());
        entity.setSortOrder(dto.sortOrder());
        entity.setActive(true);
    }
}
