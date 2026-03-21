package com.brunobs.shared;


import com.brunobs.core.catalog.common.BaseType;
import com.brunobs.exception.ValidationException;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

/**
 * Abstract Service providing generic CRUD operations for catalog-type entities.
 * Coordinates Repository, Mapper, and Validator with i18n and Audit support.
 */
public abstract class BaseService<
        E extends BaseType,
        D extends BaseTypeDTO<D, ID>,
        ID> {

    protected final BaseRepository<E, ID> repository;
    protected final BaseMapper<D, E> mapper;
    protected final BaseValidator<D, ID> validator;
    protected final MessageSource messageSource;

    protected BaseService(BaseRepository<E, ID> repository,
                          BaseMapper<D, E> mapper,
                          BaseValidator<D, ID> validator,
                          MessageSource messageSource) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    public List<D> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public D findById(ID id) {
        return mapper.toDTO(getEntity(id));
    }

    protected E getEntity(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("id", getMessage(BaseValidator.MSG_NOT_FOUND))
                ));
    }

    public E findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("name", getMessage(BaseValidator.MSG_NOT_FOUND))
                ));
    }

    public D create(D dto) {
        validator.validateForCreate(dto);

        E entity = mapper.toEntity(dto);

        // Logic for automatic sort order
        Integer nextOrder = repository.findFirstByOrderBySortOrderDesc()
                .map(last -> last.getSortOrder() + 1)
                .orElse(1);

        applyAdditionalFields(entity, dto);
        adjustSortOrder(entity, nextOrder);

        return mapper.toDTO(repository.save(entity));
    }

    public D update(D dto) {
        validator.validateForUpdate(dto);

        E entity = getEntity(dto.id());
        mapper.updateEntity(entity, dto);

        Integer nextOrder = repository.findFirstByIdNotOrderBySortOrderDesc(dto.id())
                .map(last -> last.getSortOrder() + 1)
                .orElse(1);

        applyAdditionalFields(entity, dto);
        adjustSortOrder(entity, nextOrder);

        return mapper.toDTO(repository.save(entity));
    }

    public void delete(ID id) {
        validator.validateForDelete(id);
        repository.deleteById(id);
    }

    protected void adjustSortOrder(E entity, Integer nextOrder) {
        if (entity.getSortOrder() == null || entity.getSortOrder() < 1) {
            entity.setSortOrder(nextOrder);
        }
    }

    protected String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * Unique identifier for the service (used in logging/auditing)
     */
    public abstract String getServiceIdentifier();

    /**
     * Hook for specific domain logic during save/update
     */
    protected void applyAdditionalFields(E entity, D dto) {
    }
}
