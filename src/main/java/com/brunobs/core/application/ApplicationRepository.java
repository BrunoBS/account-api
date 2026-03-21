package com.brunobs.core.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByNameAndAccountIdAndIdNot(String name, Long accountId, Long id);
    Optional<Application> findByIdAndActive(Long id, boolean active);
    Optional<Application> findByIdAndAccountIdAndActive(Long id, Long accountId, boolean active);
    List<Application> findByAccountIdAndActive(Long accountId, boolean active);
}
