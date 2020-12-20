package com.fu.pums.repository;

import com.fu.pums.domain.Student;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    // Optional<Student> findByIndex(String index);

//    Optional<Student> findOneByLogin(String login);
}
