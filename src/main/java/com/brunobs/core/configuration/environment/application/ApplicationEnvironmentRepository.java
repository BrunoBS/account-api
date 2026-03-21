
package com.brunobs.core.configuration.environment.application;

import com.brunobs.core.configuration.PublisherProjection;
import com.brunobs.core.configuration.environment.account.AccountEnvironmentId;
import com.brunobs.core.configuration.environment.application.dto.ApplicationConfigurationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationEnvironmentRepository extends JpaRepository<ApplicationEnvironment, AccountEnvironmentId> {

    List<ApplicationEnvironment> findByIdEnvironmentId(Long environmentId);

    Collection<ApplicationEnvironment> findByIdApplicationId(Long applicationId);

    void deleteByIdApplicationIdAndIdEnvironmentId(Long applicationId, Long environmentId);

    Optional<ApplicationEnvironment> findByIdApplicationIdAndIdEnvironmentId(Long applicationId, Long environmentId);


    List<ApplicationConfigurationProjection> findConfigurationsByApplication(Long applicationId, Long environmentId);


    List<PublisherProjection> findPublishersByEnvironment(Long applicationId, Long environmentId);

}