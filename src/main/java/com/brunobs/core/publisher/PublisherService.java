package com.brunobs.core.publisher;

import com.brunobs.core.catalog.type.publisherscope.PublisherScopeType;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeService;
import com.brunobs.core.environment.Environment;
import com.brunobs.core.environment.EnvironmentDTO;
import com.brunobs.exception.ValidationException;
import com.brunobs.message.feature.PublisherMessages;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PublisherService {

    private final PublisherRepository repository;
    private final PublisherMapper mapper;
    private final PublisherValidator validator;
    private final PublisherScopeTypeService scopeService;
    private final PublisherMessages publisherMessages;

    public PublisherService(PublisherRepository repository,
                            PublisherMapper mapper,
                            PublisherValidator validator,
                            PublisherScopeTypeService scopeService,
                            PublisherMessages publisherMessages) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.scopeService = scopeService;
        this.publisherMessages = publisherMessages;
    }

    public PublisherDTO findById(Long id) {
        return mapper.toDTO(getPublisherOrThrow(id));
    }

    @Transactional
    public PublisherDTO create(PublisherDTO dto) {
        validator.validateForCreate(dto);
        PublisherScopeType scope = scopeService.findByName(dto.publisherScope());
        Publisher entity = mapper.toEntity(dto, scope);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public PublisherDTO update(PublisherDTO dto) {
        validator.validateForUpdate(dto);
        Publisher base = getPublisherOrThrow(dto.id());
        PublisherScopeType scope = scopeService.findByName(dto.publisherScope());
        mapper.updateEntity(base, scope, dto);
        return mapper.toDTO(repository.save(base));
    }

    @Transactional
    public void delete(Long id) {
        Publisher publisher = getPublisherOrThrow(id);
        publisher.setActive(false);
        repository.save(publisher);
    }

    public PublisherDTO restore(Long id) {
        Publisher entity = repository.findByIdAndActiveFalse(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), publisherMessages.restoreInvalided())));

        entity.setActive(true);
        return mapper.toDTO(repository.save(entity));
    }


    public List<PublisherDTO> findAllByStatus(boolean active, String scope) {
        return repository.findAll()
                .stream()
                .filter(p -> p.isActive() == active)
                .map(mapper::toDTO)
                .filter(entity -> scope == null || entity.publisherScope().contains(scope))
                .toList();
    }

    public Publisher getPublisherOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("publisher", publisherMessages.notFound())));
    }

    public Publisher getActiveByName(String name) {
        return repository.findByNameAndActiveTrue(name)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("publisher", publisherMessages.notFound())));
    }
}
