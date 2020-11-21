package com.fu.pums.web.rest;

import com.fu.pums.domain.PhaseType;
import com.fu.pums.service.PhaseTypeService;
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
 * REST controller for managing {@link com.fu.pums.domain.PhaseType}.
 */
@RestController
@RequestMapping("/api")
public class PhaseTypeResource {

    private final Logger log = LoggerFactory.getLogger(PhaseTypeResource.class);

    private static final String ENTITY_NAME = "phaseType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhaseTypeService phaseTypeService;

    public PhaseTypeResource(PhaseTypeService phaseTypeService) {
        this.phaseTypeService = phaseTypeService;
    }

    /**
     * {@code POST  /phase-types} : Create a new phaseType.
     *
     * @param phaseType the phaseType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phaseType, or with status {@code 400 (Bad Request)} if the phaseType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phase-types")
    public ResponseEntity<PhaseType> createPhaseType(@RequestBody PhaseType phaseType) throws URISyntaxException {
        log.debug("REST request to save PhaseType : {}", phaseType);
        if (phaseType.getId() != null) {
            throw new BadRequestAlertException("A new phaseType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhaseType result = phaseTypeService.save(phaseType);
        return ResponseEntity.created(new URI("/api/phase-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phase-types} : Updates an existing phaseType.
     *
     * @param phaseType the phaseType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phaseType,
     * or with status {@code 400 (Bad Request)} if the phaseType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phaseType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phase-types")
    public ResponseEntity<PhaseType> updatePhaseType(@RequestBody PhaseType phaseType) throws URISyntaxException {
        log.debug("REST request to update PhaseType : {}", phaseType);
        if (phaseType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhaseType result = phaseTypeService.save(phaseType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phaseType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /phase-types} : get all the phaseTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phaseTypes in body.
     */
    @GetMapping("/phase-types")
    public ResponseEntity<List<PhaseType>> getAllPhaseTypes(Pageable pageable) {
        log.debug("REST request to get a page of PhaseTypes");
        Page<PhaseType> page = phaseTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phase-types/:id} : get the "id" phaseType.
     *
     * @param id the id of the phaseType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phaseType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phase-types/{id}")
    public ResponseEntity<PhaseType> getPhaseType(@PathVariable Long id) {
        log.debug("REST request to get PhaseType : {}", id);
        Optional<PhaseType> phaseType = phaseTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phaseType);
    }

    /**
     * {@code DELETE  /phase-types/:id} : delete the "id" phaseType.
     *
     * @param id the id of the phaseType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phase-types/{id}")
    public ResponseEntity<Void> deletePhaseType(@PathVariable Long id) {
        log.debug("REST request to delete PhaseType : {}", id);
        phaseTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
