package com.fu.pums.repository;

import com.fu.pums.domain.Faculty;
import com.fu.pums.domain.Observation;

import com.fu.pums.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Observation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long>, JpaSpecificationExecutor<Observation> {
    // Optional <List<Observation>> findByFaculty(Faculty faculty);
//    List<Observation> findByStudent(Student student);
}
