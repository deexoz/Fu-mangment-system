package com.fu.pums.repository;

import com.fu.pums.domain.Supervisor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Supervisor entity.
 */
@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    @Query(value = "select distinct supervisor from Supervisor supervisor left join fetch supervisor.faculties",
        countQuery = "select count(distinct supervisor) from Supervisor supervisor")
    Page<Supervisor> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct supervisor from Supervisor supervisor left join fetch supervisor.faculties")
    List<Supervisor> findAllWithEagerRelationships();

    @Query("select supervisor from Supervisor supervisor left join fetch supervisor.faculties where supervisor.id =:id")
    Optional<Supervisor> findOneWithEagerRelationships(@Param("id") Long id);
}
