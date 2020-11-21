package com.fu.pums.web.rest;

import com.fu.pums.domain.Faculty;
import com.fu.pums.service.FacultyService;
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
 * REST controller for managing {@link com.fu.pums.domain.Faculty}.
 */
@RestController
@RequestMapping("/api")
public class FacultyResource {

    private final Logger log = LoggerFactory.getLogger(FacultyResource.class);

    private static final String ENTITY_NAME = "faculty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacultyService facultyService;

    public FacultyResource(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    /**
     * {@code POST  /faculties} : Create a new faculty.
     *
     * @param faculty the faculty to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faculty, or with status {@code 400 (Bad Request)} if the faculty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/faculties")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) throws URISyntaxException {
        log.debug("REST request to save Faculty : {}", faculty);
        if (faculty.getId() != null) {
            throw new BadRequestAlertException("A new faculty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Faculty result = facultyService.save(faculty);
        return ResponseEntity.created(new URI("/api/faculties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /faculties} : Updates an existing faculty.
     *
     * @param faculty the faculty to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated faculty,
     * or with status {@code 400 (Bad Request)} if the faculty is not valid,
     * or with status {@code 500 (Internal Server Error)} if the faculty couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/faculties")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) throws URISyntaxException {
        log.debug("REST request to update Faculty : {}", faculty);
        if (faculty.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Faculty result = facultyService.save(faculty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, faculty.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /faculties} : get all the faculties.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faculties in body.
     */
    @GetMapping("/faculties")
    public ResponseEntity<List<Faculty>> getAllFaculties(Pageable pageable) {
        log.debug("REST request to get a page of Faculties");
        Page<Faculty> page = facultyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /faculties/:id} : get the "id" faculty.
     *
     * @param id the id of the faculty to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the faculty, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/faculties/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        log.debug("REST request to get Faculty : {}", id);
        Optional<Faculty> faculty = facultyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(faculty);
    }

    /**
     * {@code DELETE  /faculties/:id} : delete the "id" faculty.
     *
     * @param id the id of the faculty to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        log.debug("REST request to delete Faculty : {}", id);
        facultyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
