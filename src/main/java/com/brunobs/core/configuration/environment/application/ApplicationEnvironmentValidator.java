package com.brunobs.core.configuration.environment.application;

import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.configuration.environment.account.AccountEnvironment;
import com.brunobs.core.configuration.environment.account.AccountEnvironmentRepository;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentDTO;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentIdDTO;
import com.brunobs.core.publisher.Publisher;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ApplicationEnvironmentValidator extends BaseValidator<ApplicationEnvironmentDTO, ApplicationEnvironmentIdDTO> {

    // Chaves i18n (definidas no messages_en.properties)
    public static final String MSG_DUPLICATE_PUBLISHER = "validator.application-env.duplicate-publisher";
    public static final String MSG_MIN_PUBLISHER = "validator.application-env.min-publisher";
    public static final String MSG_ORDER_REQUIRED = "validator.application-env.order-required";

    private final SchemaValidator schemaValidator;
    private final AccountEnvironmentRepository repository;

    public ApplicationEnvironmentValidator(SchemaValidator schemaValidator, AccountEnvironmentRepository repository, MessageSource messageSource) {
        super(messageSource);
        this.schemaValidator = schemaValidator;
        this.repository = repository;
    }

    @Override
    public void validateAttributes(ApplicationEnvironmentDTO dto, ValidationResult vr) {
        Set<String> publisherNames = new HashSet<>();

        if (dto.publishers() == null || dto.publishers().isEmpty()) {
            vr.addError("publishers", MSG_MIN_PUBLISHER);
        } else {
            int index = 0;
            for (PublisherConfig config : dto.publishers()) {
                String pathPrefix = "publishers[" + index + "]";

                if (!publisherNames.add(config.getPublisher().getName())) {
                    vr.addError(pathPrefix + ".name", MSG_DUPLICATE_PUBLISHER);
                }

                if (config.getOrder() == null || config.getOrder() <= 0) {
                    vr.addError(pathPrefix + ".order", MSG_ORDER_REQUIRED);
                }

                JsonNode jsonNode = schemaValidator.fromString(config.getParameters());
                validatePublisherSchema(config.getPublisher(), jsonNode, pathPrefix + ".parameters", vr);

                index++;
            }
        }

        validateIntegrity(dto, vr);
    }


    @Override
    protected boolean recordExists(ApplicationEnvironmentIdDTO identifier) {
        return repository.findByIdAccountIdAndIdEnvironmentId(identifier.applicationId(), identifier.environmentId()).isPresent();
    }

    private void validatePublisherSchema(Publisher p, JsonNode params, String path, ValidationResult vr) {
        schemaValidator.validateJson(p.getJsonSchema(), params, path, vr);
    }

    @Override
    protected String entityName() {
        return AccountEnvironment.class.getSimpleName();
    }

    @Override
    protected void validateIntegrity(ApplicationEnvironmentDTO dto, ValidationResult vr) {
    }

}
