package com.brunobs.core.environment;

import com.brunobs.core.account.Account;
import com.brunobs.core.account.AccountService;
import com.brunobs.core.catalog.type.authorization.AuthorizationType;
import com.brunobs.core.catalog.type.authorization.AuthorizationTypeService;
import com.brunobs.core.catalog.type.environment.EnvironmentType;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeService;
import com.brunobs.exception.ValidationException;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class EnvironmentService {

    private final EnvironmentRepository repository;
    private final EnvironmentTypeService typeService;
    private final AuthorizationTypeService authService;
    private final EnvironmentMapper mapper;
    private final EnvironmentValidator validator;
    private final AccountService accountService;

    public EnvironmentService(EnvironmentRepository repository,
                              EnvironmentTypeService typeService,
                              AuthorizationTypeService authService,
                              EnvironmentMapper mapper,
                              EnvironmentValidator validator,
                              AccountService accountService) {
        this.repository = repository;
        this.typeService = typeService;
        this.authService = authService;
        this.mapper = mapper;
        this.validator = validator;
        this.accountService = accountService;
    }

    public Optional<Environment> findLastEnvironment(EnvironmentDTO dto) {
        EnvironmentType type = typeService.findByName(dto.environmentType());
        if (dto.accountId() == null) {
            return repository.findFirstByTypeIdAndAccountIsNullAndActiveOrderByIdDesc(type.getId(), dto.active());
        }
        return repository.findFirstByTypeIdAndAccountIdAndActiveOrderByIdDesc(type.getId(), dto.accountId(), dto.active());
    }

    public EnvironmentDTO findBy(EnvironmentDTO dto) {
        Environment entity = getEnvironment(dto);
        return mapper.toDTO(entity);
    }

    @Transactional
    public EnvironmentDTO create(EnvironmentDTO dto) {
        validator.validateForCreate(dto);
        EnvironmentDependencies deps = resolveDependencies(dto);

        Environment entity = mapper.toEntity(dto, deps.account(), deps.type(), deps.authorization());

        // Business Rule: Auto-increment order if not provided
        if (entity.getSortOrder() == null || entity.getSortOrder() < 0) {
            Optional<Environment> lastRecord = findLastEnvironment(dto);
            entity.setSortOrder(lastRecord.map(value -> value.getSortOrder() + 1).orElse(1));
        }

        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public EnvironmentDTO update(EnvironmentDTO dto) {
        validator.validateForUpdate(dto);
        Environment entity = getEnvironment(dto.id());
        EnvironmentDependencies deps = resolveDependencies(dto);

        mapper.updateEntity(entity, dto, deps.account(), deps.type(), deps.authorization());

        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void delete(EnvironmentDTO dto) {
        validator.validateForDelete(dto.id());
        Environment entity = getEnvironment(dto);
        entity.setActive(false);
        repository.save(entity);
    }

    public List<EnvironmentDTO> findByAccount(Long accountId, boolean active) {
        EnvironmentType customType = typeService.findByName(EnvironmentTypeEnum.CUSTOM.name());
        List<EnvironmentDTO> defaultEnvironments = findAllDefault(active);

        List<Environment> customEnvironments = repository.findByAccountIdAndTypeIdAndActiveOrderByAuthorizationTypeSortOrderAsc(
                accountId,
                customType.getId(),
                active);

        return Stream.concat(
                defaultEnvironments.stream(),
                customEnvironments.stream().map(mapper::toDTO)
        ).toList();
    }

    public List<EnvironmentDTO> findAllDefault(boolean active) {
        EnvironmentType defaultType = typeService.findByName(EnvironmentTypeEnum.DEFAULT.name());
        return repository.findByTypeIdAndActiveOrderByAuthorizationTypeSortOrderAsc(defaultType.getId(), active)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    private EnvironmentDependencies resolveDependencies(EnvironmentDTO dto) {
        EnvironmentType type = typeService.findByName(dto.environmentType());
        AuthorizationType auth = authService.findByName(dto.authorizationType());
        Account account = null;

        EnvironmentTypeEnum typeEnum = BaseEnum.from(EnvironmentTypeEnum.class, dto.environmentType());
        if (EnvironmentTypeEnum.CUSTOM.equals(typeEnum)) {
            account = accountService.getAccount(dto.accountId());
        }
        return new EnvironmentDependencies(type, auth, account);
    }

    private record EnvironmentDependencies(EnvironmentType type, AuthorizationType authorization, Account account) {}

    public Environment getEnvironment(EnvironmentDTO dto) {
        EnvironmentType type = typeService.findByName(dto.environmentType());
        return repository.findByTypeIdAndIdAndActive(type.getId(), dto.id(), dto.active())
                .orElseThrow(() -> new ValidationException(new ValidationResult("environment", BaseValidator.MSG_NOT_FOUND)));
    }

    public Environment getEnvironment(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException(new ValidationResult("environment", BaseValidator.MSG_NOT_FOUND)));
    }

    public Environment getEnvironment(Long id, boolean active) {
        return repository.findByIdAndActive(id, active)
                .orElseThrow(() -> new ValidationException(new ValidationResult("environment", BaseValidator.MSG_NOT_FOUND)));
    }
}
