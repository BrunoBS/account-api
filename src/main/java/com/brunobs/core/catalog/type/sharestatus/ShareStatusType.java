package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity representing the status of a share process (e.g., WAITING_DESTINATION_APPROVAL, APPROVED).
 * Inherits common catalog fields from BaseType.
 */
@Entity
@Table(name = "share_status_types") // Plural, snake_case e inglês simplificado
public class ShareStatusType extends BaseType {


}
