package com.fu.pums.service;

import com.fu.pums.domain.Supervisor;
import com.fu.pums.repository.SupervisorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Supervisor}.
 */
@Service
@Transactional
public class SupervisorService {

    private final Logger log = LoggerFactory.getLogger(SupervisorService.class);

    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    /**
     * Save a supervisor.
     *
     * @param supervisor the entity to save.
     * @return the persisted entity.
     */
    public Supervisor save(Supervisor supervisor) {
        log.debug("Request to save Supervisor : {}", supervisor);
        return supervisorRepository.save(supervisor);
    }

    /**
     * Get all the supervisors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Supervisor> findAll(Pageable pageable) {
        log.debug("Request to get all Supervisors");
        return supervisorRepository.findAll(pageable);
    }


    /**
     * Get all the supervisors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Supervisor> findAllWithEagerRelationships(Pageable pageable) {
        return supervisorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one supervisor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Supervisor> findOne(Long id) {
        log.debug("Request to get Supervisor : {}", id);
        return supervisorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the supervisor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Supervisor : {}", id);
        supervisorRepository.deleteById(id);
    }
}
