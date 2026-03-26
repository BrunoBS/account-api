package com.brunobs.shared.base;


import com.brunobs.core.catalog.common.BaseType;
import com.brunobs.exception.ValidationException;
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

    public List<D> findAll(boolean active) {
        return repository.findByActive(active).stream()
                .map(mapper::toDTO)
                .toList();
    }

    public D findById(ID id) {
        return mapper.toDTO(getEntity(id));
    }

    protected E getEntity(ID id) {
        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), getMessage(BaseValidator.MSG_NOT_FOUND))
                ));
    }

    public E findByName(String name) {
        return repository.findByNameAndActiveTrue(name)
                .orElseThrow(() -> new ValidationException(

                        new ValidationResult(validator.entityName(), getMessage(BaseValidator.MSG_NOT_FOUND))
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


    public D restore(ID id) {
        E entity = repository.findByIdAndActiveFalse(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), getMessage(BaseValidator.MSG_NOT_FOUND_RESTORE))));
        entity.setActive(true);
        return mapper.toDTO(repository.save(entity));
    }

    public void delete(ID id) {
        validator.validateForDelete(id);

        E account = getEntity(id);
        account.setActive(false);
        repository.save(account);
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

    public List<E> featureTypeList(List<String> names) {
        return repository.findByNameInAndActiveTrue(names);
    }
}
