package com.fu.pums.repository;

import com.fu.pums.domain.Faculty;
import com.fu.pums.domain.Project;

import com.fu.pums.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Optional<Project> findByStudents(Student students);
    Optional<List<Project>> findAllByFaculty (Faculty faculty);
    Optional<Project> findOneByStudents(Student student);

}
