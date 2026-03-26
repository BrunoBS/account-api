package com.brunobs.filter;

import java.util.List;

public abstract class AbstractFilterService<T> {

    public List<T> filter(List<T> items, AuthorizationContext context) {

        if (context.isOwner()) {
            return items;
        }

        return items.stream()
                .filter(item -> authorize(item, context))
                .toList();
    }

    public T filter(T item, AuthorizationContext context) {

        if (context.isOwner()) {
            return item;
        }

        return authorize(item, context) ? item : null;
    }

    protected abstract boolean authorize(T item, AuthorizationContext context);
}