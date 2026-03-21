package com.brunobs.features.configuration.account;


import com.brunobs.core.account.Account;
import com.brunobs.core.account.AccountService;
import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.configuration.PublisherProjection;
import com.brunobs.core.configuration.environment.account.AccountEnvironmentService;
import com.brunobs.core.configuration.environment.account.dto.AccountConfigurationProjection;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentDTO;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentIdDTO;
import com.brunobs.core.environment.Environment;
import com.brunobs.core.environment.EnvironmentService;
import com.brunobs.core.publisher.Publisher;
import com.brunobs.core.publisher.PublisherService;
import com.brunobs.exception.ValidationException;
import com.brunobs.features.EntityValidationService;
import com.brunobs.features.configuration.account.dto.AccountEnvironmentPublishersResponseDTO;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountConfigurationService {


    private final EntityValidationService entityValidationService;

    private final AccountEnvironmentService accountEnvironmentService;
    private final SchemaValidator schemaValidator;

    public AccountConfigurationService(
            EntityValidationService entityValidationService, AccountEnvironmentService accountEnvironmentService,
            SchemaValidator schemaValidator
    ) {
        this.entityValidationService = entityValidationService;


        this.accountEnvironmentService = accountEnvironmentService;
        this.schemaValidator = schemaValidator;
    }

    @Transactional
    public EnvironmentConfigDTO create(Long accountId, EnvironmentConfigDTO dto) {
        AccountEnvironmentDTO accountEnvDto = resolveAccountEnvironmentDTO(accountId, dto);
        return accountEnvironmentService.create(accountEnvDto);
    }

    @Transactional
    public void delete(Long accountId, Long environmentId) {
        AccountEnvironmentIdDTO idDto = new AccountEnvironmentIdDTO(environmentId, accountId);
        accountEnvironmentService.delete(idDto);
    }

    public List<AccountConfigurationProjection> findByAccount(Long accountId) {
        ValidationResult vr = new ValidationResult();
        Account account = entityValidationService.validateAccount(accountId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);

        return accountEnvironmentService.findConfigurationsByAccount(account.getId(), null);
    }

    public AccountConfigurationProjection findByAccountAndEnvironment(Long accountId, Long environmentId) {
        ValidationResult vr = new ValidationResult();
        Account account = entityValidationService.validateAccount(accountId, vr);
        Environment environment = entityValidationService.validateEnvironment(environmentId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);

        return accountEnvironmentService.findConfigurationsByAccount(account.getId(), environment.getId())
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public EnvironmentConfigDTO update(Long accountId, Long environmentId, EnvironmentConfigDTO dto) {
        EnvironmentConfigDTO configDto = dto.withEnvironmentId(environmentId);
        AccountEnvironmentDTO accountEnvDto = resolveAccountEnvironmentDTO(accountId, configDto);
        return accountEnvironmentService.update(accountEnvDto);
    }

    public AccountEnvironmentPublishersResponseDTO findPublishersByEnvironment(Long accountId, Long environmentId) {
        ValidationResult vr = new ValidationResult();
        Account account = entityValidationService.validateAccount(accountId, vr);
        Environment environment = entityValidationService.validateEnvironment(environmentId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);

        List<PublisherProjection> publishers = accountEnvironmentService.findPublishersByEnvironment(account.getId(), environment.getId());

        return new AccountEnvironmentPublishersResponseDTO(
                account.getId(), account.getName(),
                environment.getId(), environment.getName(),
                publishers
        );
    }

    private AccountEnvironmentDTO resolveAccountEnvironmentDTO(Long accountId, EnvironmentConfigDTO dto) {
        ValidationResult vr = new ValidationResult();

        Account account = entityValidationService.validateAccount(accountId, vr);
        Environment environment = entityValidationService.validateEnvironment(dto.environmentId(), vr);

        List<PublisherConfig> publisherConfigs = dto.publishers().stream().map(p -> {
            Publisher publisher = entityValidationService.validatePublisher(p.name(), vr);
            return new PublisherConfig(
                    publisher,
                    p.order(),
                    schemaValidator.toJsonString(p.parameters())
            );
        }).collect(Collectors.toList());

        if (vr.hasErrors()) throw new ValidationException(vr);

        return new AccountEnvironmentDTO(
                account,
                environment,
                publisherConfigs,
                dto.authorizerGroup()
        );
    }


}
