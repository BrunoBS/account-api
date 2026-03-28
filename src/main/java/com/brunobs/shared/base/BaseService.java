package com.brunobs.shared.base;


import com.brunobs.core.catalog.common.BaseType;
import com.brunobs.exception.ValidationException;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.validation.ValidationResult;

import java.util.List;

public abstract class BaseService<
        E extends BaseType,
        D extends BaseTypeDTO<D, ID>,
        ID> {

    protected final BaseRepository<E, ID> repository;
    protected final BaseMapper<D, E> mapper;
    protected final BaseValidator<D, ID> validator;
    protected final CatalogMessages catalogMessages;

    protected BaseService(BaseRepository<E, ID> repository,
                          BaseMapper<D, E> mapper,
                          BaseValidator<D, ID> validator,
                          CatalogMessages catalogMessages) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.catalogMessages = catalogMessages;

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
                        new ValidationResult(validator.entityName(), catalogMessages.notFound(validator.entityName()))));
    }

    public E findByName(String name) {
        return repository.findByNameAndActiveTrue(name)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), catalogMessages.notFound(validator.entityName()))));
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
                        new ValidationResult(validator.entityName(), catalogMessages.restoreInvalid(validator.entityName()))));

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


    public abstract String getServiceIdentifier();


    protected void applyAdditionalFields(E entity, D dto) {
    }

    public List<E> featureTypeList(List<String> names) {
        return repository.findByNameInAndActiveTrue(names);
    }
}
