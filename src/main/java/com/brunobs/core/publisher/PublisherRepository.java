package com.brunobs.core.publisher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {


    Optional<Publisher> findByNameAndActiveTrue(String name);

    Optional<Publisher> findByIdAndActiveTrue(Long id);


    boolean existsByNameAndIdNot(String name, Long id);
}
