package com.fu.pums.repository;

import com.fu.pums.domain.PhaseType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PhaseType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhaseTypeRepository extends JpaRepository<PhaseType, Long> {
}
