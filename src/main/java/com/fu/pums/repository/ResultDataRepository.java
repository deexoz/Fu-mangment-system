package com.fu.pums.repository;

import com.fu.pums.domain.ResultData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ResultData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultDataRepository extends JpaRepository<ResultData, Long> {
}
