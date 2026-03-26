package com.brunobs.features;


import com.brunobs.core.account.Account;
import com.brunobs.core.account.AccountService;
import com.brunobs.core.application.Application;
import com.brunobs.core.application.ApplicationService;
import com.brunobs.core.environment.Environment;
import com.brunobs.core.environment.EnvironmentService;
import com.brunobs.core.publisher.Publisher;
import com.brunobs.core.publisher.PublisherService;
import com.brunobs.exception.ValidationException;
import com.brunobs.features.sharing.target.SharingTarget;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;

@Service
public class EntityValidationService {


    private final ApplicationService applicationService;
    private final AccountService accountService;
    private final EnvironmentService environmentService;
    private final PublisherService publisherService;

    public EntityValidationService(ApplicationService applicationService,
                                   EnvironmentService environmentService,
                                   PublisherService publisherService,
                                   AccountService accountService
    ) {


        this.applicationService = applicationService;
        this.environmentService = environmentService;
        this.publisherService = publisherService;
        this.accountService = accountService;
    }


    public Account validateAccount(Long accountId, ValidationResult vr) {
        try {
            return accountService.getAccount(accountId);
        } catch (ValidationException e) {
            vr.merge(e.getValidationResult());
            return null;
        }
    }


    public Application validateApplication(Long accountId, Long applicationId, ValidationResult vr) {
        try {
            return applicationService.getApplication(accountId, applicationId);
        } catch (ValidationException e) {
            vr.merge(e.getValidationResult());
            return null;
        }
    }

    public Publisher validatePublisher(String name, ValidationResult vr) {
        try {
            return publisherService.getActiveByName(name);
        } catch (ValidationException e) {
            vr.merge(e.getValidationResult());
            return null;
        }
    }

    public Environment validateEnvironment(Long environmentId, ValidationResult vr) {
        try {
            return environmentService.getEnvironment(environmentId);
        } catch (ValidationException e) {
            vr.merge(e.getValidationResult());
            return null;
        }
    }
}
