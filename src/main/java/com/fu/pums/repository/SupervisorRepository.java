package com.fu.pums.repository;

import com.fu.pums.domain.Supervisor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Supervisor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long>, JpaSpecificationExecutor<Supervisor> {
}
