package com.brunobs.core.publisher;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeType;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeService;
import com.brunobs.exception.ValidationException;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service responsible for Publisher lifecycle and scope resolution.
 */
@Service
public class PublisherService {

    private final PublisherRepository repository;
    private final PublisherMapper mapper;
    private final PublisherValidator validator;
    private final PublisherScopeTypeService scopeService;
    private final MessageSource messageSource;

    public PublisherService(PublisherRepository repository,
                            PublisherMapper mapper,
                            PublisherValidator validator,
                            PublisherScopeTypeService scopeService,
                            MessageSource messageSource) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.scopeService = scopeService;
        this.messageSource = messageSource;
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
    public void deactivate(Long id) {
        Publisher publisher = getPublisherOrThrow(id);
        publisher.setActive(false);
        repository.save(publisher);
    }

    public List<PublisherDTO> findAllByStatus(boolean active) {
        // Guideline: Use repository filters instead of memory filtering for better performance
        return repository.findAll()
                .stream()
                .filter(p -> p.isActive() == active)
                .map(mapper::toDTO)
                .toList();
    }

    public Publisher getPublisherOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("publisher", getMessage(PublisherValidator.MSG_NOT_FOUND, id))));
    }

    public Publisher getActiveByName(String name) {
        return repository.findByNameAndActiveTrue(name)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("publisher", getMessage(PublisherValidator.MSG_NOT_FOUND, name))));
    }

    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
