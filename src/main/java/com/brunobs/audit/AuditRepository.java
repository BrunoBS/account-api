
package com.brunobs.audit; // Package no plural por convenção

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for persisting audit logs.
 */
@Repository
public interface AuditRepository extends JpaRepository<AuditLog, Long> {

    // As a guideline reference, you can add custom finders here if needed,
    // e.g., finding by action or system name.
}
