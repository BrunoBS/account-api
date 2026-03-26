package com.brunobs.shared.base;

public interface BaseMapper<D, E> {

    E toEntity(D dto);

    D toDTO(E entity);

    void updateEntity(E entity, D dto);
}