package com.fu.pums.web.rest;

import com.fu.pums.domain.Phase;
import com.fu.pums.service.PhaseService;
import com.fu.pums.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.fu.pums.domain.Phase}.
 */
@RestController
@RequestMapping("/api")
public class PhaseResource {

    private final Logger log = LoggerFactory.getLogger(PhaseResource.class);

    private static final String ENTITY_NAME = "phase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhaseService phaseService;

    public PhaseResource(PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    /**
     * {@code POST  /phases} : Create a new phase.
     *
     * @param phase the phase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phase, or with status {@code 400 (Bad Request)} if the phase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phases")
    public ResponseEntity<Phase> createPhase(@RequestBody Phase phase) throws URISyntaxException {
        log.debug("REST request to save Phase : {}", phase);
        if (phase.getId() != null) {
            throw new BadRequestAlertException("A new phase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Phase result = phaseService.save(phase);
        return ResponseEntity.created(new URI("/api/phases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phases} : Updates an existing phase.
     *
     * @param phase the phase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phase,
     * or with status {@code 400 (Bad Request)} if the phase is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phases")
    public ResponseEntity<Phase> updatePhase(@RequestBody Phase phase) throws URISyntaxException {
        log.debug("REST request to update Phase : {}", phase);
        if (phase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Phase result = phaseService.save(phase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phase.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /phases} : get all the phases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phases in body.
     */
    @GetMapping("/phases")
    public ResponseEntity<List<Phase>> getAllPhases(Pageable pageable) {
        log.debug("REST request to get a page of Phases");
        Page<Phase> page = phaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phases/:id} : get the "id" phase.
     *
     * @param id the id of the phase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phases/{id}")
    public ResponseEntity<Phase> getPhase(@PathVariable Long id) {
        log.debug("REST request to get Phase : {}", id);
        Optional<Phase> phase = phaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phase);
    }

    /**
     * {@code DELETE  /phases/:id} : delete the "id" phase.
     *
     * @param id the id of the phase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phases/{id}")
    public ResponseEntity<Void> deletePhase(@PathVariable Long id) {
        log.debug("REST request to delete Phase : {}", id);
        phaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
