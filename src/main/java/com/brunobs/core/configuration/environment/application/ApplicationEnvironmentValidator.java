package com.brunobs.core.configuration.environment.application;

import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.configuration.environment.account.AccountEnvironment;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentDTO;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentIdDTO;
import com.brunobs.core.publisher.Publisher;
import com.brunobs.message.feature.ApplicationEnvMessages;
import com.brunobs.shared.base.BaseValidator;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ApplicationEnvironmentValidator extends BaseValidator<ApplicationEnvironmentDTO, ApplicationEnvironmentIdDTO> {

    private final SchemaValidator schemaValidator;
    private final ApplicationEnvMessages applicationEnvMessages;


    public ApplicationEnvironmentValidator(SchemaValidator schemaValidator, ApplicationEnvMessages applicationEnvMessages) {
        super();
        this.schemaValidator = schemaValidator;
        this.applicationEnvMessages = applicationEnvMessages;
    }

    @Override
    public void validateAttributes(ApplicationEnvironmentDTO dto, ValidationResult vr) {
        Set<String> publisherNames = new HashSet<>();

        if (dto.publishers() == null || dto.publishers().isEmpty()) {
            vr.addError("publishers", applicationEnvMessages.minPublisher(1));
        } else {
            int index = 0;
            for (PublisherConfig config : dto.publishers()) {
                String pathPrefix = "publishers[" + index + "]";

                if (!publisherNames.add(config.getPublisher().getName())) {
                    vr.addError(pathPrefix + ".name", applicationEnvMessages.duplicatePublisher());
                }

                if (config.getOrder() == null || config.getOrder() <= 0) {
                    vr.addError(pathPrefix + ".order", applicationEnvMessages.orderRequired());
                }

                JsonNode jsonNode = schemaValidator.fromString(config.getParameters());
                validatePublisherSchema(config.getPublisher(), jsonNode, pathPrefix + ".parameters", vr);

                index++;
            }
        }

        validateIntegrity(dto, vr);
    }

    @Override
    public String recordRequired() {
        return applicationEnvMessages.recordRequired();
    }


    private void validatePublisherSchema(Publisher p, JsonNode params, String path, ValidationResult vr) {
        schemaValidator.validateJson(p.getJsonSchema(), params, path, vr);
    }

    @Override
    public String entityName() {
        return AccountEnvironment.class.getSimpleName();
    }

    @Override
    protected void validateIntegrity(ApplicationEnvironmentDTO dto, ValidationResult vr) {

        if (isMissingAccount(dto) || isMissingEnvironment(dto)) {
            vr.addError("aplication", applicationEnvMessages.selectionRequired());
            return;
        }
    }

    private boolean isMissingAccount(ApplicationEnvironmentDTO dto) {
        return dto.application() == null || dto.application().getId() == null;
    }

    private boolean isMissingEnvironment(ApplicationEnvironmentDTO dto) {
        return dto.environment() == null || dto.environment().getId() == null;
    }

}