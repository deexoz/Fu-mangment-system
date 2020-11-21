package com.fu.pums.web.rest;

import com.fu.pums.PumsApp;
import com.fu.pums.domain.Phase;
import com.fu.pums.repository.PhaseRepository;
import com.fu.pums.service.PhaseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PhaseResource} REST controller.
 */
@SpringBootTest(classes = PumsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PhaseResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DISCUTION_TIME = "AAAAAAAAAA";
    private static final String UPDATED_DISCUTION_TIME = "BBBBBBBBBB";

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private PhaseService phaseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhaseMockMvc;

    private Phase phase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phase createEntity(EntityManager em) {
        Phase phase = new Phase()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .discutionTime(DEFAULT_DISCUTION_TIME);
        return phase;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phase createUpdatedEntity(EntityManager em) {
        Phase phase = new Phase()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .discutionTime(UPDATED_DISCUTION_TIME);
        return phase;
    }

    @BeforeEach
    public void initTest() {
        phase = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhase() throws Exception {
        int databaseSizeBeforeCreate = phaseRepository.findAll().size();
        // Create the Phase
        restPhaseMockMvc.perform(post("/api/phases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phase)))
            .andExpect(status().isCreated());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeCreate + 1);
        Phase testPhase = phaseList.get(phaseList.size() - 1);
        assertThat(testPhase.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPhase.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPhase.getDiscutionTime()).isEqualTo(DEFAULT_DISCUTION_TIME);
    }

    @Test
    @Transactional
    public void createPhaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phaseRepository.findAll().size();

        // Create the Phase with an existing ID
        phase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhaseMockMvc.perform(post("/api/phases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phase)))
            .andExpect(status().isBadRequest());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPhases() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        // Get all the phaseList
        restPhaseMockMvc.perform(get("/api/phases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phase.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].discutionTime").value(hasItem(DEFAULT_DISCUTION_TIME)));
    }
    
    @Test
    @Transactional
    public void getPhase() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        // Get the phase
        restPhaseMockMvc.perform(get("/api/phases/{id}", phase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phase.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.discutionTime").value(DEFAULT_DISCUTION_TIME));
    }
    @Test
    @Transactional
    public void getNonExistingPhase() throws Exception {
        // Get the phase
        restPhaseMockMvc.perform(get("/api/phases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhase() throws Exception {
        // Initialize the database
        phaseService.save(phase);

        int databaseSizeBeforeUpdate = phaseRepository.findAll().size();

        // Update the phase
        Phase updatedPhase = phaseRepository.findById(phase.getId()).get();
        // Disconnect from session so that the updates on updatedPhase are not directly saved in db
        em.detach(updatedPhase);
        updatedPhase
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .discutionTime(UPDATED_DISCUTION_TIME);

        restPhaseMockMvc.perform(put("/api/phases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPhase)))
            .andExpect(status().isOk());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeUpdate);
        Phase testPhase = phaseList.get(phaseList.size() - 1);
        assertThat(testPhase.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPhase.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPhase.getDiscutionTime()).isEqualTo(UPDATED_DISCUTION_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPhase() throws Exception {
        int databaseSizeBeforeUpdate = phaseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhaseMockMvc.perform(put("/api/phases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phase)))
            .andExpect(status().isBadRequest());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhase() throws Exception {
        // Initialize the database
        phaseService.save(phase);

        int databaseSizeBeforeDelete = phaseRepository.findAll().size();

        // Delete the phase
        restPhaseMockMvc.perform(delete("/api/phases/{id}", phase.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
