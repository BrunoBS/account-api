package com.brunobs.features.sharing.origin;

import com.brunobs.core.publisher.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SharingOriginRepository extends JpaRepository<SharingOrigin, Long> {



    boolean existsByNameAndIdNot(String name, Long id);
}
