package com.fu.pums.service;

import com.fu.pums.domain.Announcement;
import com.fu.pums.repository.AnnouncementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Announcement}.
 */
@Service
@Transactional
public class AnnouncementService {

    private final Logger log = LoggerFactory.getLogger(AnnouncementService.class);

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    /**
     * Save a announcement.
     *
     * @param announcement the entity to save.
     * @return the persisted entity.
     */
    public Announcement save(Announcement announcement) {
        log.debug("Request to save Announcement : {}", announcement);
        return announcementRepository.save(announcement);
    }

    /**
     * Get all the announcements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Announcement> findAll(Pageable pageable) {
        log.debug("Request to get all Announcements");
        return announcementRepository.findAll(pageable);
    }


    /**
     * Get one announcement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Announcement> findOne(Long id) {
        log.debug("Request to get Announcement : {}", id);
        return announcementRepository.findById(id);
    }

    /**
     * Delete the announcement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Announcement : {}", id);
        announcementRepository.deleteById(id);
    }
}
