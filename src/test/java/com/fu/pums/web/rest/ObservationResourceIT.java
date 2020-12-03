package com.fu.pums.web.rest;

import com.fu.pums.PumsApp;
import com.fu.pums.domain.Observation;
import com.fu.pums.domain.File;
import com.fu.pums.domain.Project;
import com.fu.pums.repository.ObservationRepository;
import com.fu.pums.service.ObservationService;
import com.fu.pums.service.dto.ObservationCriteria;
import com.fu.pums.service.ObservationQueryService;

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
 * Integration tests for the {@link ObservationResource} REST controller.
 */
@SpringBootTest(classes = PumsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ObservationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private ObservationService observationService;

    @Autowired
    private ObservationQueryService observationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObservationMockMvc;

    private Observation observation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Observation createEntity(EntityManager em) {
        Observation observation = new Observation()
            .title(DEFAULT_TITLE)
            .detail(DEFAULT_DETAIL)
            .creationDate(DEFAULT_CREATION_DATE);
        return observation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Observation createUpdatedEntity(EntityManager em) {
        Observation observation = new Observation()
            .title(UPDATED_TITLE)
            .detail(UPDATED_DETAIL)
            .creationDate(UPDATED_CREATION_DATE);
        return observation;
    }

    @BeforeEach
    public void initTest() {
        observation = createEntity(em);
    }

    @Test
    @Transactional
    public void createObservation() throws Exception {
        int databaseSizeBeforeCreate = observationRepository.findAll().size();
        // Create the Observation
        restObservationMockMvc.perform(post("/api/observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observation)))
            .andExpect(status().isCreated());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate + 1);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testObservation.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testObservation.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createObservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = observationRepository.findAll().size();

        // Create the Observation with an existing ID
        observation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservationMockMvc.perform(post("/api/observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observation)))
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllObservations() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList
        restObservationMockMvc.perform(get("/api/observations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observation.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get the observation
        restObservationMockMvc.perform(get("/api/observations/{id}", observation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(observation.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }


    @Test
    @Transactional
    public void getObservationsByIdFiltering() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        Long id = observation.getId();

        defaultObservationShouldBeFound("id.equals=" + id);
        defaultObservationShouldNotBeFound("id.notEquals=" + id);

        defaultObservationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultObservationShouldNotBeFound("id.greaterThan=" + id);

        defaultObservationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultObservationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllObservationsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where title equals to DEFAULT_TITLE
        defaultObservationShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the observationList where title equals to UPDATED_TITLE
        defaultObservationShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllObservationsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where title not equals to DEFAULT_TITLE
        defaultObservationShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the observationList where title not equals to UPDATED_TITLE
        defaultObservationShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllObservationsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultObservationShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the observationList where title equals to UPDATED_TITLE
        defaultObservationShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllObservationsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where title is not null
        defaultObservationShouldBeFound("title.specified=true");

        // Get all the observationList where title is null
        defaultObservationShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllObservationsByTitleContainsSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where title contains DEFAULT_TITLE
        defaultObservationShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the observationList where title contains UPDATED_TITLE
        defaultObservationShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllObservationsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where title does not contain DEFAULT_TITLE
        defaultObservationShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the observationList where title does not contain UPDATED_TITLE
        defaultObservationShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllObservationsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creationDate equals to DEFAULT_CREATION_DATE
        defaultObservationShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the observationList where creationDate equals to UPDATED_CREATION_DATE
        defaultObservationShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllObservationsByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultObservationShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the observationList where creationDate not equals to UPDATED_CREATION_DATE
        defaultObservationShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllObservationsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultObservationShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the observationList where creationDate equals to UPDATED_CREATION_DATE
        defaultObservationShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllObservationsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creationDate is not null
        defaultObservationShouldBeFound("creationDate.specified=true");

        // Get all the observationList where creationDate is null
        defaultObservationShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllObservationsByFileIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);
        File file = FileResourceIT.createEntity(em);
        em.persist(file);
        em.flush();
        observation.setFile(file);
        observationRepository.saveAndFlush(observation);
        Long fileId = file.getId();

        // Get all the observationList where file equals to fileId
        defaultObservationShouldBeFound("fileId.equals=" + fileId);

        // Get all the observationList where file equals to fileId + 1
        defaultObservationShouldNotBeFound("fileId.equals=" + (fileId + 1));
    }


    @Test
    @Transactional
    public void getAllObservationsByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);
        Project project = ProjectResourceIT.createEntity(em);
        em.persist(project);
        em.flush();
        observation.setProject(project);
        observationRepository.saveAndFlush(observation);
        Long projectId = project.getId();

        // Get all the observationList where project equals to projectId
        defaultObservationShouldBeFound("projectId.equals=" + projectId);

        // Get all the observationList where project equals to projectId + 1
        defaultObservationShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultObservationShouldBeFound(String filter) throws Exception {
        restObservationMockMvc.perform(get("/api/observations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observation.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));

        // Check, that the count call also returns 1
        restObservationMockMvc.perform(get("/api/observations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultObservationShouldNotBeFound(String filter) throws Exception {
        restObservationMockMvc.perform(get("/api/observations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restObservationMockMvc.perform(get("/api/observations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingObservation() throws Exception {
        // Get the observation
        restObservationMockMvc.perform(get("/api/observations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObservation() throws Exception {
        // Initialize the database
        observationService.save(observation);

        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Update the observation
        Observation updatedObservation = observationRepository.findById(observation.getId()).get();
        // Disconnect from session so that the updates on updatedObservation are not directly saved in db
        em.detach(updatedObservation);
        updatedObservation
            .title(UPDATED_TITLE)
            .detail(UPDATED_DETAIL)
            .creationDate(UPDATED_CREATION_DATE);

        restObservationMockMvc.perform(put("/api/observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedObservation)))
            .andExpect(status().isOk());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testObservation.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testObservation.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservationMockMvc.perform(put("/api/observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observation)))
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObservation() throws Exception {
        // Initialize the database
        observationService.save(observation);

        int databaseSizeBeforeDelete = observationRepository.findAll().size();

        // Delete the observation
        restObservationMockMvc.perform(delete("/api/observations/{id}", observation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
