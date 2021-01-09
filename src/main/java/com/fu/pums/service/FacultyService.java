package com.fu.pums.service;

import com.fu.pums.domain.Batch;
import com.fu.pums.domain.Faculty;
import com.fu.pums.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Faculty}.
 */
@Service
@Transactional
public class FacultyService {

    private final Logger log = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    /**
     * Save a faculty.
     *
     * @param faculty the entity to save.
     * @return the persisted entity.
     */
    public Faculty save(Faculty faculty) {
        log.debug("Request to save Faculty : {}", faculty);
        return facultyRepository.save(faculty);
    }

    /**
     * Get all the faculties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Faculty> findAll(Pageable pageable) {
        log.debug("Request to get all Faculties");
        return facultyRepository.findAll(pageable);
    }


    /**
     * Get all the faculties with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Faculty> findAllWithEagerRelationships(Pageable pageable) {
        return facultyRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one faculty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Faculty> findOne(Long id) {
        log.debug("Request to get Faculty : {}", id);
        return facultyRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the faculty by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Faculty : {}", id);
        facultyRepository.deleteById(id);
    }
}
