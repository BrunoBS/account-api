package com.brunobs.common.factory;

import com.brunobs.common.builder.BaseCatalogBuilder;

public abstract class BaseCatalogFactory<D, B extends BaseCatalogBuilder<D, B>
        > {

    protected abstract B builder();

    // =========================================================
    // INVALID STATES
    // =========================================================

    public D withoutName() {
        return builder()
                .withName(null)
                .build();
    }

    public D withoutLabel() {
        return builder()
                .withLabel(null)
                .build();
    }

    public D withoutDescription() {
        return builder()
                .withDescription(null)
                .build();
    }

    public D withoutSortOrder() {
        return builder()
                .withName("ADMIN")
                .withSortOrder(null)
                .build();
    }

    public D withSortOrderNegative() {
        return builder()
                .withName("CATALOG")
                .withSortOrder(-1)
                .build();
    }

    public D withEmptyName() {
        return builder()
                .withName("")
                .build();
    }

    public D withEmptyLabel() {
        return builder()
                .withLabel("")
                .build();
    }

    public D withEmptyDescription() {
        return builder()
                .withDescription("")
                .build();
    }

    public D withLongDescription() {
        return builder()
                .withDescription("A".repeat(300))
                .build();
    }

    public D withShortDescription() {
        return builder()
                .withDescription("D")
                .build();
    }

    // =========================================================
    // CUSTOM
    // =========================================================

    public D custom(
            String name,
            String label,
            String description,
            Integer sortOrder,
            String settings
    ) {
        return builder()
                .withName(name)
                .withLabel(label)
                .withDescription(description)
                .withSortOrder(sortOrder)
                .withSettings(settings)
                .build();
    }
}