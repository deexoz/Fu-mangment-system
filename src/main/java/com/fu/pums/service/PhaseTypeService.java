package com.fu.pums.service;

import com.fu.pums.domain.PhaseType;
import com.fu.pums.repository.PhaseTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PhaseType}.
 */
@Service
@Transactional
public class PhaseTypeService {

    private final Logger log = LoggerFactory.getLogger(PhaseTypeService.class);

    private final PhaseTypeRepository phaseTypeRepository;

    public PhaseTypeService(PhaseTypeRepository phaseTypeRepository) {
        this.phaseTypeRepository = phaseTypeRepository;
    }

    /**
     * Save a phaseType.
     *
     * @param phaseType the entity to save.
     * @return the persisted entity.
     */
    public PhaseType save(PhaseType phaseType) {
        log.debug("Request to save PhaseType : {}", phaseType);
        return phaseTypeRepository.save(phaseType);
    }

    /**
     * Get all the phaseTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PhaseType> findAll(Pageable pageable) {
        log.debug("Request to get all PhaseTypes");
        return phaseTypeRepository.findAll(pageable);
    }


    /**
     * Get one phaseType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PhaseType> findOne(Long id) {
        log.debug("Request to get PhaseType : {}", id);
        return phaseTypeRepository.findById(id);
    }

    /**
     * Delete the phaseType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PhaseType : {}", id);
        phaseTypeRepository.deleteById(id);
    }
}
