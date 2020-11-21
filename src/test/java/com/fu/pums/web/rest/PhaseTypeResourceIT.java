package com.fu.pums.web.rest;

import com.fu.pums.PumsApp;
import com.fu.pums.domain.PhaseType;
import com.fu.pums.repository.PhaseTypeRepository;
import com.fu.pums.service.PhaseTypeService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fu.pums.domain.enumeration.AnnouncementType;
/**
 * Integration tests for the {@link PhaseTypeResource} REST controller.
 */
@SpringBootTest(classes = PumsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PhaseTypeResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final AnnouncementType DEFAULT_PHASE_TYPE = AnnouncementType.SUBMISSION;
    private static final AnnouncementType UPDATED_PHASE_TYPE = AnnouncementType.DISCUSSIONS;

    @Autowired
    private PhaseTypeRepository phaseTypeRepository;

    @Autowired
    private PhaseTypeService phaseTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhaseTypeMockMvc;

    private PhaseType phaseType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhaseType createEntity(EntityManager em) {
        PhaseType phaseType = new PhaseType()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .phaseType(DEFAULT_PHASE_TYPE);
        return phaseType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhaseType createUpdatedEntity(EntityManager em) {
        PhaseType phaseType = new PhaseType()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .phaseType(UPDATED_PHASE_TYPE);
        return phaseType;
    }

    @BeforeEach
    public void initTest() {
        phaseType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhaseType() throws Exception {
        int databaseSizeBeforeCreate = phaseTypeRepository.findAll().size();
        // Create the PhaseType
        restPhaseTypeMockMvc.perform(post("/api/phase-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phaseType)))
            .andExpect(status().isCreated());

        // Validate the PhaseType in the database
        List<PhaseType> phaseTypeList = phaseTypeRepository.findAll();
        assertThat(phaseTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PhaseType testPhaseType = phaseTypeList.get(phaseTypeList.size() - 1);
        assertThat(testPhaseType.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPhaseType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhaseType.getPhaseType()).isEqualTo(DEFAULT_PHASE_TYPE);
    }

    @Test
    @Transactional
    public void createPhaseTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phaseTypeRepository.findAll().size();

        // Create the PhaseType with an existing ID
        phaseType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhaseTypeMockMvc.perform(post("/api/phase-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phaseType)))
            .andExpect(status().isBadRequest());

        // Validate the PhaseType in the database
        List<PhaseType> phaseTypeList = phaseTypeRepository.findAll();
        assertThat(phaseTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPhaseTypes() throws Exception {
        // Initialize the database
        phaseTypeRepository.saveAndFlush(phaseType);

        // Get all the phaseTypeList
        restPhaseTypeMockMvc.perform(get("/api/phase-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phaseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].phaseType").value(hasItem(DEFAULT_PHASE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getPhaseType() throws Exception {
        // Initialize the database
        phaseTypeRepository.saveAndFlush(phaseType);

        // Get the phaseType
        restPhaseTypeMockMvc.perform(get("/api/phase-types/{id}", phaseType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phaseType.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.phaseType").value(DEFAULT_PHASE_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPhaseType() throws Exception {
        // Get the phaseType
        restPhaseTypeMockMvc.perform(get("/api/phase-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhaseType() throws Exception {
        // Initialize the database
        phaseTypeService.save(phaseType);

        int databaseSizeBeforeUpdate = phaseTypeRepository.findAll().size();

        // Update the phaseType
        PhaseType updatedPhaseType = phaseTypeRepository.findById(phaseType.getId()).get();
        // Disconnect from session so that the updates on updatedPhaseType are not directly saved in db
        em.detach(updatedPhaseType);
        updatedPhaseType
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .phaseType(UPDATED_PHASE_TYPE);

        restPhaseTypeMockMvc.perform(put("/api/phase-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPhaseType)))
            .andExpect(status().isOk());

        // Validate the PhaseType in the database
        List<PhaseType> phaseTypeList = phaseTypeRepository.findAll();
        assertThat(phaseTypeList).hasSize(databaseSizeBeforeUpdate);
        PhaseType testPhaseType = phaseTypeList.get(phaseTypeList.size() - 1);
        assertThat(testPhaseType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPhaseType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhaseType.getPhaseType()).isEqualTo(UPDATED_PHASE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPhaseType() throws Exception {
        int databaseSizeBeforeUpdate = phaseTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhaseTypeMockMvc.perform(put("/api/phase-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phaseType)))
            .andExpect(status().isBadRequest());

        // Validate the PhaseType in the database
        List<PhaseType> phaseTypeList = phaseTypeRepository.findAll();
        assertThat(phaseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhaseType() throws Exception {
        // Initialize the database
        phaseTypeService.save(phaseType);

        int databaseSizeBeforeDelete = phaseTypeRepository.findAll().size();

        // Delete the phaseType
        restPhaseTypeMockMvc.perform(delete("/api/phase-types/{id}", phaseType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhaseType> phaseTypeList = phaseTypeRepository.findAll();
        assertThat(phaseTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
