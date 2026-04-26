package com.brunobs.cache.sync;

import com.brunobs.auth.messaging.dto.AccountCacheDTO;
import com.brunobs.auth.messaging.dto.GroupDetailDTO;
import com.brunobs.auth.messaging.kafka.KafkaPublishRequest;
import com.brunobs.auth.messaging.kafka.KafkaPublisherService;
import com.brunobs.cache.sync.repository.AuthorizationRepository;
import com.brunobs.cache.sync.repository.AuthorizationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorizationCacheRefreshService {

    private final AuthorizationRepository repository;
    private final KafkaPublisherService kafkaPublisherService;
    private final ObjectMapper objectMapper;

    private static final String TOPIC_CACHE = "account-permissions-cache";

    public AuthorizationCacheRefreshService(AuthorizationRepository repository,
                                            KafkaPublisherService kafkaPublisherService,
                                            ObjectMapper objectMapper) {
        this.repository = repository;
        this.kafkaPublisherService = kafkaPublisherService;
        this.objectMapper = objectMapper;
    }

    public void refresh(AccountCacheDTO request) {

        String type = request.authorizationTypeDefault();

        List<AuthorizationResult> allResults = repository.findAuthorizationsByAccount(request.accountId(), type);

        Map<Long, List<AuthorizationResult>> grouped =
                allResults.stream()
                        .collect(Collectors.groupingBy(AuthorizationResult::getIdAccount));

        grouped.forEach((accountId, results) ->
                publish(accountId, type, results)
        );
    }

    private void publish(Long accountId,
                         String type,
                         List<AuthorizationResult> results) {

        try {
            List<GroupDetailDTO> details = results.stream()
                    .map(r -> new GroupDetailDTO(
                            formatKey(r),
                            r.getAuthorizerGroup()
                    ))
                    .toList();

            AccountCacheDTO dto = new AccountCacheDTO(accountId, type, details);

            String json = objectMapper.writeValueAsString(dto);

            kafkaPublisherService.publish(
                    new KafkaPublishRequest(
                            "PROD",
                            TOPIC_CACHE,
                            accountId,
                            json
                    )
            );

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String formatKey(AuthorizationResult r) {
        return String.format("AUTH::%s::%s::%s",
                r.getIdAccount(),
                r.getIdEnvironment() != null ? r.getIdEnvironment() : "NONE",
                r.getIdApplication() != null ? r.getIdApplication() : "NONE"
        );
    }
}