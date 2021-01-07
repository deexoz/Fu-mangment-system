package com.fu.pums.service;

import com.fu.pums.domain.Student;
import com.fu.pums.repository.StudentRepository;
import com.fu.pums.repository.UserRepository;
import com.fu.pums.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a student.
     *
     * @param student the entity to save.
     * @return the persisted entity.org.hibernate.PersistentObjectException: detached entity passed to persist: com.fu.pums.domain.User
     */
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);
        Long userId = student.getUser().getId();
        userRepository.findById(userId).ifPresent(student::user);

        return studentRepository.save(student);
    }

    /**
     * Get all the students.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Student> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentRepository.findAll(pageable);
    }

    /**
     * Get one student by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Student> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id);
    }

    // @Transactional(readOnly = true)
    // public Optional<Student> findByIndex(String index) {
    //     return studentRepository.findByIndex(index);
    // }

    /**
     * Delete the student by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Student> getCurrentStudent() {
        return studentRepository.findById(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId());
    }
}
