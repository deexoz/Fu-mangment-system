package com.fu.pums.web.rest;

import com.fu.pums.domain.ResultData;
import com.fu.pums.service.ResultDataService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.fu.pums.domain.ResultData}.
 */
@RestController
@RequestMapping("/api")
public class ResultDataResource {

    private final Logger log = LoggerFactory.getLogger(ResultDataResource.class);

    private static final String ENTITY_NAME = "resultData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultDataService resultDataService;

    public ResultDataResource(ResultDataService resultDataService) {
        this.resultDataService = resultDataService;
    }

    /**
     * {@code POST  /result-data} : Create a new resultData.
     *
     * @param resultData the resultData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultData, or with status {@code 400 (Bad Request)} if the resultData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/result-data")
    public ResponseEntity<ResultData> createResultData(@RequestBody ResultData resultData) throws URISyntaxException {
        log.debug("REST request to save ResultData : {}", resultData);
        if (resultData.getId() != null) {
            throw new BadRequestAlertException("A new resultData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultData result = resultDataService.save(resultData);
        return ResponseEntity.created(new URI("/api/result-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /result-data} : Updates an existing resultData.
     *
     * @param resultData the resultData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultData,
     * or with status {@code 400 (Bad Request)} if the resultData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/result-data")
    public ResponseEntity<ResultData> updateResultData(@RequestBody ResultData resultData) throws URISyntaxException {
        log.debug("REST request to update ResultData : {}", resultData);
        if (resultData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultData result = resultDataService.save(resultData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /result-data} : get all the resultData.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultData in body.
     */
    @GetMapping("/result-data")
    public ResponseEntity<List<ResultData>> getAllResultData(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("phase-is-null".equals(filter)) {
            log.debug("REST request to get all ResultDatas where phase is null");
            return new ResponseEntity<>(resultDataService.findAllWherePhaseIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ResultData");
        Page<ResultData> page = resultDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /result-data/:id} : get the "id" resultData.
     *
     * @param id the id of the resultData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/result-data/{id}")
    public ResponseEntity<ResultData> getResultData(@PathVariable Long id) {
        log.debug("REST request to get ResultData : {}", id);
        Optional<ResultData> resultData = resultDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultData);
    }

    /**
     * {@code DELETE  /result-data/:id} : delete the "id" resultData.
     *
     * @param id the id of the resultData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/result-data/{id}")
    public ResponseEntity<Void> deleteResultData(@PathVariable Long id) {
        log.debug("REST request to delete ResultData : {}", id);
        resultDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
