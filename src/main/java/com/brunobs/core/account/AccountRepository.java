package com.brunobs.core.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    boolean existsByNameAndIdNot(String name, Long id);


    Optional<Account> findByIdAndActive(Long id, boolean active);
}
