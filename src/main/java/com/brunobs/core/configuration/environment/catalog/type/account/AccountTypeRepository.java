package com.brunobs.core.catalog.type.account;

import com.brunobs.shared.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for AccountType catalog.
 * Inherits all generic operations from BaseRepository (findByName, existsByName, etc).
 */
@Repository
public interface AccountTypeRepository extends BaseRepository<AccountType, Long> {

    // Como a BaseRepository já possui findByName, existsByNameAndIdNot
    // e findAllByActiveTrue, você não precisa escrever nada aqui
    // para o funcionamento padrão de catálogos.
}
