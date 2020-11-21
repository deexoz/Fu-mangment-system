package com.fu.pums.web.rest;

import com.fu.pums.domain.Supervisor;
import com.fu.pums.service.SupervisorService;
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
 * REST controller for managing {@link com.fu.pums.domain.Supervisor}.
 */
@RestController
@RequestMapping("/api")
public class SupervisorResource {

    private final Logger log = LoggerFactory.getLogger(SupervisorResource.class);

    private static final String ENTITY_NAME = "supervisor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupervisorService supervisorService;

    public SupervisorResource(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    /**
     * {@code POST  /supervisors} : Create a new supervisor.
     *
     * @param supervisor the supervisor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supervisor, or with status {@code 400 (Bad Request)} if the supervisor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supervisors")
    public ResponseEntity<Supervisor> createSupervisor(@RequestBody Supervisor supervisor) throws URISyntaxException {
        log.debug("REST request to save Supervisor : {}", supervisor);
        if (supervisor.getId() != null) {
            throw new BadRequestAlertException("A new supervisor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Supervisor result = supervisorService.save(supervisor);
        return ResponseEntity.created(new URI("/api/supervisors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supervisors} : Updates an existing supervisor.
     *
     * @param supervisor the supervisor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supervisor,
     * or with status {@code 400 (Bad Request)} if the supervisor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supervisor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supervisors")
    public ResponseEntity<Supervisor> updateSupervisor(@RequestBody Supervisor supervisor) throws URISyntaxException {
        log.debug("REST request to update Supervisor : {}", supervisor);
        if (supervisor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Supervisor result = supervisorService.save(supervisor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supervisor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supervisors} : get all the supervisors.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supervisors in body.
     */
    @GetMapping("/supervisors")
    public ResponseEntity<List<Supervisor>> getAllSupervisors(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Supervisors");
        Page<Supervisor> page;
        if (eagerload) {
            page = supervisorService.findAllWithEagerRelationships(pageable);
        } else {
            page = supervisorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supervisors/:id} : get the "id" supervisor.
     *
     * @param id the id of the supervisor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supervisor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supervisors/{id}")
    public ResponseEntity<Supervisor> getSupervisor(@PathVariable Long id) {
        log.debug("REST request to get Supervisor : {}", id);
        Optional<Supervisor> supervisor = supervisorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supervisor);
    }

    /**
     * {@code DELETE  /supervisors/:id} : delete the "id" supervisor.
     *
     * @param id the id of the supervisor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supervisors/{id}")
    public ResponseEntity<Void> deleteSupervisor(@PathVariable Long id) {
        log.debug("REST request to delete Supervisor : {}", id);
        supervisorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
