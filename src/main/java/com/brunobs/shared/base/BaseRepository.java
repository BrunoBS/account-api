package com.brunobs.shared.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {


    Optional<T> findByNameAndActiveTrue(String name);

    Optional<T> findByIdAndActiveTrue(ID id);

    Optional<T> findByIdAndActiveFalse(ID nome);

    List<T> findByActive(boolean active);

    boolean existsByNameAndIdNot(String name, ID id);


    Optional<T> findFirstByOrderBySortOrderDesc();


    Optional<T> findFirstByIdNotOrderBySortOrderDesc(ID id);

    List<T> findByNameInAndActiveTrue(List<String> names);

}
