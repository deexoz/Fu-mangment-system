package com.fu.pums.service;

import com.fu.pums.domain.Batch;
import com.fu.pums.repository.BatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Batch}.
 */
@Service
@Transactional
public class BatchService {

    private final Logger log = LoggerFactory.getLogger(BatchService.class);

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    /**
     * Save a batch.
     *
     * @param batch the entity to save.
     * @return the persisted entity.
     */
    public Batch save(Batch batch) {
        log.debug("Request to save Batch : {}", batch);
        return batchRepository.save(batch);
    }

    /**
     * Get all the batches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Batch> findAll(Pageable pageable) {
        log.debug("Request to get all Batches");
        return batchRepository.findAll(pageable);
    }


    /**
     * Get one batch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Batch> findOne(Long id) {
        log.debug("Request to get Batch : {}", id);
        return batchRepository.findById(id);
    }

    /**
     * Delete the batch by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Batch : {}", id);
        batchRepository.deleteById(id);
    }
}
