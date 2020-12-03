package com.fu.pums.web.rest;

import com.fu.pums.domain.Observation;
import com.fu.pums.service.ObservationService;
import com.fu.pums.web.rest.errors.BadRequestAlertException;
import com.fu.pums.service.dto.ObservationCriteria;
import com.fu.pums.service.ObservationQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fu.pums.domain.Observation}.
 */
@RestController
@RequestMapping("/api")
public class ObservationResource {

    private final Logger log = LoggerFactory.getLogger(ObservationResource.class);

    private static final String ENTITY_NAME = "observation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObservationService observationService;

    private final ObservationQueryService observationQueryService;

    public ObservationResource(ObservationService observationService, ObservationQueryService observationQueryService) {
        this.observationService = observationService;
        this.observationQueryService = observationQueryService;
    }

    /**
     * {@code POST  /observations} : Create a new observation.
     *
     * @param observation the observation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new observation, or with status {@code 400 (Bad Request)} if the observation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/observations")
    public ResponseEntity<Observation> createObservation(@RequestBody Observation observation) throws URISyntaxException {
        log.debug("REST request to save Observation : {}", observation);
        if (observation.getId() != null) {
            throw new BadRequestAlertException("A new observation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Observation result = observationService.save(observation);
        return ResponseEntity.created(new URI("/api/observations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /observations} : Updates an existing observation.
     *
     * @param observation the observation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observation,
     * or with status {@code 400 (Bad Request)} if the observation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the observation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/observations")
    public ResponseEntity<Observation> updateObservation(@RequestBody Observation observation) throws URISyntaxException {
        log.debug("REST request to update Observation : {}", observation);
        if (observation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Observation result = observationService.save(observation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /observations} : get all the observations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of observations in body.
     */
    @GetMapping("/observations")
    public ResponseEntity<List<Observation>> getAllObservations(ObservationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Observations by criteria: {}", criteria);
        Page<Observation> page = observationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /observations/count} : count all the observations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/observations/count")
    public ResponseEntity<Long> countObservations(ObservationCriteria criteria) {
        log.debug("REST request to count Observations by criteria: {}", criteria);
        return ResponseEntity.ok().body(observationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /observations/:id} : get the "id" observation.
     *
     * @param id the id of the observation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the observation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/observations/{id}")
    public ResponseEntity<Observation> getObservation(@PathVariable Long id) {
        log.debug("REST request to get Observation : {}", id);
        Optional<Observation> observation = observationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(observation);
    }

    /**
     * {@code DELETE  /observations/:id} : delete the "id" observation.
     *
     * @param id the id of the observation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/observations/{id}")
    public ResponseEntity<Void> deleteObservation(@PathVariable Long id) {
        log.debug("REST request to delete Observation : {}", id);
        observationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
