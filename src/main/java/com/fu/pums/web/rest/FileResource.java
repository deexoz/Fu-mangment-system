package com.fu.pums.web.rest;

import com.fu.pums.domain.File;
import com.fu.pums.service.FileService;
import com.fu.pums.web.rest.errors.BadRequestAlertException;
import com.fu.pums.service.dto.FileCriteria;
import com.fu.pums.service.FileQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.fu.pums.domain.File}.
 */
@RestController
@RequestMapping("/api")
public class FileResource {

    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    private static final String ENTITY_NAME = "file";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileService fileService;

    private final FileQueryService fileQueryService;

    public FileResource(FileService fileService, FileQueryService fileQueryService) {
        this.fileService = fileService;
        this.fileQueryService = fileQueryService;
    }

    /**
     * {@code POST  /files} : Create a new file.
     *
     * @param file the file to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new file, or with status {@code 400 (Bad Request)} if the file has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/files")
    public ResponseEntity<File> createFile(@RequestBody File file) throws URISyntaxException {
        log.debug("REST request to save File : {}", file);
        if (file.getId() != null) {
            throw new BadRequestAlertException("A new file cannot already have an ID", ENTITY_NAME, "idexists");
        }
        File result = fileService.save(file);
        return ResponseEntity.created(new URI("/api/files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /files} : Updates an existing file.
     *
     * @param file the file to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated file,
     * or with status {@code 400 (Bad Request)} if the file is not valid,
     * or with status {@code 500 (Internal Server Error)} if the file couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/files")
    public ResponseEntity<File> updateFile(@RequestBody File file) throws URISyntaxException {
        log.debug("REST request to update File : {}", file);
        if (file.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        File result = fileService.save(file);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, file.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /files} : get all the files.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of files in body.
     */
    @GetMapping("/files")
    public ResponseEntity<List<File>> getAllFiles(FileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Files by criteria: {}", criteria);
        Page<File> page = fileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /files/count} : count all the files.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/files/count")
    public ResponseEntity<Long> countFiles(FileCriteria criteria) {
        log.debug("REST request to count Files by criteria: {}", criteria);
        return ResponseEntity.ok().body(fileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /files/:id} : get the "id" file.
     *
     * @param id the id of the file to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the file, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<File> getFile(@PathVariable Long id) {
        log.debug("REST request to get File : {}", id);
        Optional<File> file = fileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(file);
    }

    /**
     * {@code DELETE  /files/:id} : delete the "id" file.
     *
     * @param id the id of the file to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        log.debug("REST request to delete File : {}", id);
        fileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
