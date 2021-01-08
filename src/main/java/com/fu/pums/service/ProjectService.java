package com.fu.pums.service;

import com.fu.pums.domain.Batch;
import com.fu.pums.domain.Faculty;
import com.fu.pums.domain.Project;
import com.fu.pums.domain.Student;
import com.fu.pums.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link Project}.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    private final StudentService studentService ;

    public ProjectService(ProjectRepository projectRepository ,StudentService studentService) {
        this.projectRepository = projectRepository;
        this.studentService = studentService;
    }

    /**
     * Save a project.
     *
     * @param project the entity to save.
     * @return the persisted entity.
     */
    public Project save(Project project) {
        log.debug("Request to save Project : {}", project);
        return projectRepository.save(project);
    }

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectRepository.findAll(pageable);
    }


    /**
     * Get one project by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Project> findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findById(id);
    }

    /**
     * Delete the project by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Project> findOneByStudent(Student student) {
        return projectRepository.findOneByStudents(student);
    }

    @Transactional(readOnly = true)
    public Optional<List<Project>> findAllByFaculty(Faculty faculty){
        return projectRepository.findAllByFaculty(faculty);
    }


    public Set<Project> findAllByFacultyAndBatch(Faculty faculty, Batch batch) {
        return projectRepository.findAllByFacultyAndBatch(faculty, batch);
    }
}
