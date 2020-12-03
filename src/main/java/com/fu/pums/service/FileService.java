package com.fu.pums.service;

import com.fu.pums.domain.File;
import com.fu.pums.repository.FileRepository;
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
 * Service Implementation for managing {@link File}.
 */
@Service
@Transactional
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Save a file.
     *
     * @param file the entity to save.
     * @return the persisted entity.
     */
    public File save(File file) {
        log.debug("Request to save File : {}", file);
        return fileRepository.save(file);
    }

    /**
     * Get all the files.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<File> findAll(Pageable pageable) {
        log.debug("Request to get all Files");
        return fileRepository.findAll(pageable);
    }



    /**
     *  Get all the files where Observation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<File> findAllWhereObservationIsNull() {
        log.debug("Request to get all files where Observation is null");
        return StreamSupport
            .stream(fileRepository.findAll().spliterator(), false)
            .filter(file -> file.getObservation() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one file by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<File> findOne(Long id) {
        log.debug("Request to get File : {}", id);
        return fileRepository.findById(id);
    }

    /**
     * Delete the file by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.deleteById(id);
    }
}
