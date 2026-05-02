package com.brunobs.shared.base;


import com.brunobs.audit.configs.*;
import com.brunobs.auth.authorization.AuthorizationLevel;
import com.brunobs.auth.authorization.AuthorizationRequired;
import com.brunobs.core.catalog.common.BaseType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Generic REST Controller for catalog-type resources.
 * Provides standard CRUD endpoints with built-in auditing.
 */
@AuthorizationRequired(level = AuthorizationLevel.OWNER)
public abstract class BaseController<
        D extends BaseTypeDTO<D, I>,
        E extends BaseType,
        I> {

    protected abstract BaseService<E, D, I> getService();

    @GetMapping
    public ResponseEntity<List<D>> findAll(
            @RequestParam(name = "active", defaultValue = "true") boolean active,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "scope", required = false) String scope

    ) {
        List<D> list = getService().findAll(active, name, scope);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> findById(@PathVariable I id) {
        D d = getService().findById(id);
        return ResponseEntity.ok(d);
    }

    @PostMapping

    public ResponseEntity<List<D>> create(@RequestBody List<D> dtos) {
        List<D> list = dtos.stream()
                .map(dto -> getService().create(dto.withId(null)))
                .toList();
        return ResponseEntity.ok(list);

    }

    @PutMapping("/{id}")

    public ResponseEntity<D> update(@PathVariable I id, @RequestBody D dto) {

        D update = getService().update(dto.withId(id));
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<?> delete(@PathVariable I id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{id}/restore")

    public ResponseEntity<D> restore(@PathVariable I id) {
        D d = getService().restore(id);
        return ResponseEntity.ok(d);
    }
}
