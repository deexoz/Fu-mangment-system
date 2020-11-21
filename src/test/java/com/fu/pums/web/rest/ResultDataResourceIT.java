package com.fu.pums.web.rest;

import com.fu.pums.PumsApp;
import com.fu.pums.domain.ResultData;
import com.fu.pums.repository.ResultDataRepository;
import com.fu.pums.service.ResultDataService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResultDataResource} REST controller.
 */
@SpringBootTest(classes = PumsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResultDataResourceIT {

    private static final String DEFAULT_OVSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OVSERVATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResultDataRepository resultDataRepository;

    @Autowired
    private ResultDataService resultDataService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultDataMockMvc;

    private ResultData resultData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultData createEntity(EntityManager em) {
        ResultData resultData = new ResultData()
            .ovservation(DEFAULT_OVSERVATION)
            .date(DEFAULT_DATE);
        return resultData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultData createUpdatedEntity(EntityManager em) {
        ResultData resultData = new ResultData()
            .ovservation(UPDATED_OVSERVATION)
            .date(UPDATED_DATE);
        return resultData;
    }

    @BeforeEach
    public void initTest() {
        resultData = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultData() throws Exception {
        int databaseSizeBeforeCreate = resultDataRepository.findAll().size();
        // Create the ResultData
        restResultDataMockMvc.perform(post("/api/result-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultData)))
            .andExpect(status().isCreated());

        // Validate the ResultData in the database
        List<ResultData> resultDataList = resultDataRepository.findAll();
        assertThat(resultDataList).hasSize(databaseSizeBeforeCreate + 1);
        ResultData testResultData = resultDataList.get(resultDataList.size() - 1);
        assertThat(testResultData.getOvservation()).isEqualTo(DEFAULT_OVSERVATION);
        assertThat(testResultData.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createResultDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultDataRepository.findAll().size();

        // Create the ResultData with an existing ID
        resultData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultDataMockMvc.perform(post("/api/result-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultData)))
            .andExpect(status().isBadRequest());

        // Validate the ResultData in the database
        List<ResultData> resultDataList = resultDataRepository.findAll();
        assertThat(resultDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResultData() throws Exception {
        // Initialize the database
        resultDataRepository.saveAndFlush(resultData);

        // Get all the resultDataList
        restResultDataMockMvc.perform(get("/api/result-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultData.getId().intValue())))
            .andExpect(jsonPath("$.[*].ovservation").value(hasItem(DEFAULT_OVSERVATION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getResultData() throws Exception {
        // Initialize the database
        resultDataRepository.saveAndFlush(resultData);

        // Get the resultData
        restResultDataMockMvc.perform(get("/api/result-data/{id}", resultData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultData.getId().intValue()))
            .andExpect(jsonPath("$.ovservation").value(DEFAULT_OVSERVATION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingResultData() throws Exception {
        // Get the resultData
        restResultDataMockMvc.perform(get("/api/result-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultData() throws Exception {
        // Initialize the database
        resultDataService.save(resultData);

        int databaseSizeBeforeUpdate = resultDataRepository.findAll().size();

        // Update the resultData
        ResultData updatedResultData = resultDataRepository.findById(resultData.getId()).get();
        // Disconnect from session so that the updates on updatedResultData are not directly saved in db
        em.detach(updatedResultData);
        updatedResultData
            .ovservation(UPDATED_OVSERVATION)
            .date(UPDATED_DATE);

        restResultDataMockMvc.perform(put("/api/result-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultData)))
            .andExpect(status().isOk());

        // Validate the ResultData in the database
        List<ResultData> resultDataList = resultDataRepository.findAll();
        assertThat(resultDataList).hasSize(databaseSizeBeforeUpdate);
        ResultData testResultData = resultDataList.get(resultDataList.size() - 1);
        assertThat(testResultData.getOvservation()).isEqualTo(UPDATED_OVSERVATION);
        assertThat(testResultData.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingResultData() throws Exception {
        int databaseSizeBeforeUpdate = resultDataRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultDataMockMvc.perform(put("/api/result-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultData)))
            .andExpect(status().isBadRequest());

        // Validate the ResultData in the database
        List<ResultData> resultDataList = resultDataRepository.findAll();
        assertThat(resultDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultData() throws Exception {
        // Initialize the database
        resultDataService.save(resultData);

        int databaseSizeBeforeDelete = resultDataRepository.findAll().size();

        // Delete the resultData
        restResultDataMockMvc.perform(delete("/api/result-data/{id}", resultData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultData> resultDataList = resultDataRepository.findAll();
        assertThat(resultDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
