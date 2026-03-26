package com.brunobs.shared.base;


import com.brunobs.audit.configs.Auditable;
import com.brunobs.audit.configs.IdSource;
import com.brunobs.core.catalog.common.BaseType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Generic REST Controller for catalog-type resources.
 * Provides standard CRUD endpoints with built-in auditing.
 */
public abstract class BaseController<
        D extends BaseTypeDTO<D, I>,
        E extends BaseType,
        I> {

    protected abstract BaseService<E, D, I> getService();

    @GetMapping
    public List<D> findAll(
            @RequestParam(name = "active", defaultValue = "true") boolean active
    ) {
        return getService().findAll(active);
    }

    @GetMapping("/{id}")
    public D findById(@PathVariable I id) {
        return getService().findById(id);
    }

    @PostMapping
    @Auditable(action = "CREATE_RECORD", source = IdSource.RESPONSE, field = "id")
    public D create(@RequestBody D dto) {
        // Guideline: Ensure ID is null for new records to prevent manual ID injection
        return getService().create(dto.withId(null));
    }

    @PutMapping("/{id}")
    @Auditable(action = "UPDATE_RECORD", source = IdSource.PATH, field = "id")
    public D update(@PathVariable I id, @RequestBody D dto) {
        // Guideline: Force the ID from the Path to the DTO for consistency
        return getService().update(dto.withId(id));
    }

    @DeleteMapping("/{id}")
    @Auditable(action = "DELETE_RECORD", source = IdSource.PATH, field = "id")
    public void delete(@PathVariable I id) {
        getService().delete(id);
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<D> restore(@PathVariable I id) {
        D d = getService().restore(id);
        return ResponseEntity.ok(d);
    }
}
