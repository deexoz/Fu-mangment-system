package com.fu.pums.service;

import com.fu.pums.domain.ResultData;
import com.fu.pums.repository.ResultDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link ResultData}.
 */
@Service
@Transactional
public class ResultDataService {

    private final Logger log = LoggerFactory.getLogger(ResultDataService.class);

    private final ResultDataRepository resultDataRepository;

    public ResultDataService(ResultDataRepository resultDataRepository) {
        this.resultDataRepository = resultDataRepository;
    }

    /**
     * Save a resultData.
     *
     * @param resultData the entity to save.
     * @return the persisted entity.
     */
    public ResultData save(ResultData resultData) {
        log.debug("Request to save ResultData : {}", resultData);
        return resultDataRepository.save(resultData);
    }

    /**
     * Get all the resultData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultData> findAll(Pageable pageable) {
        log.debug("Request to get all ResultData");
        return resultDataRepository.findAll(pageable);
    }



    /**
     *  Get all the resultData where Phase is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ResultData> findAllWherePhaseIsNull() {
        log.debug("Request to get all resultData where Phase is null");
        return StreamSupport
            .stream(resultDataRepository.findAll().spliterator(), false)
            .filter(resultData -> resultData.getPhase() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one resultData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResultData> findOne(Long id) {
        log.debug("Request to get ResultData : {}", id);
        return resultDataRepository.findById(id);
    }

    /**
     * Delete the resultData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ResultData : {}", id);
        resultDataRepository.deleteById(id);
    }
}
