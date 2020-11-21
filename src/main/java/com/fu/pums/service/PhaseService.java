package com.fu.pums.service;

import com.fu.pums.domain.Phase;
import com.fu.pums.repository.PhaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Phase}.
 */
@Service
@Transactional
public class PhaseService {

    private final Logger log = LoggerFactory.getLogger(PhaseService.class);

    private final PhaseRepository phaseRepository;

    public PhaseService(PhaseRepository phaseRepository) {
        this.phaseRepository = phaseRepository;
    }

    /**
     * Save a phase.
     *
     * @param phase the entity to save.
     * @return the persisted entity.
     */
    public Phase save(Phase phase) {
        log.debug("Request to save Phase : {}", phase);
        return phaseRepository.save(phase);
    }

    /**
     * Get all the phases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Phase> findAll(Pageable pageable) {
        log.debug("Request to get all Phases");
        return phaseRepository.findAll(pageable);
    }


    /**
     * Get one phase by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Phase> findOne(Long id) {
        log.debug("Request to get Phase : {}", id);
        return phaseRepository.findById(id);
    }

    /**
     * Delete the phase by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Phase : {}", id);
        phaseRepository.deleteById(id);
    }
}
