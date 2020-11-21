package com.fu.pums.web.rest;

import com.fu.pums.PumsApp;
import com.fu.pums.domain.Supervisor;
import com.fu.pums.repository.SupervisorRepository;
import com.fu.pums.service.SupervisorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SupervisorResource} REST controller.
 */
@SpringBootTest(classes = PumsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SupervisorResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Mock
    private SupervisorRepository supervisorRepositoryMock;

    @Mock
    private SupervisorService supervisorServiceMock;

    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupervisorMockMvc;

    private Supervisor supervisor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supervisor createEntity(EntityManager em) {
        Supervisor supervisor = new Supervisor()
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return supervisor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supervisor createUpdatedEntity(EntityManager em) {
        Supervisor supervisor = new Supervisor()
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return supervisor;
    }

    @BeforeEach
    public void initTest() {
        supervisor = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupervisor() throws Exception {
        int databaseSizeBeforeCreate = supervisorRepository.findAll().size();
        // Create the Supervisor
        restSupervisorMockMvc.perform(post("/api/supervisors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supervisor)))
            .andExpect(status().isCreated());

        // Validate the Supervisor in the database
        List<Supervisor> supervisorList = supervisorRepository.findAll();
        assertThat(supervisorList).hasSize(databaseSizeBeforeCreate + 1);
        Supervisor testSupervisor = supervisorList.get(supervisorList.size() - 1);
        assertThat(testSupervisor.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createSupervisorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supervisorRepository.findAll().size();

        // Create the Supervisor with an existing ID
        supervisor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupervisorMockMvc.perform(post("/api/supervisors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supervisor)))
            .andExpect(status().isBadRequest());

        // Validate the Supervisor in the database
        List<Supervisor> supervisorList = supervisorRepository.findAll();
        assertThat(supervisorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSupervisors() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList
        restSupervisorMockMvc.perform(get("/api/supervisors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supervisor.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSupervisorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(supervisorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupervisorMockMvc.perform(get("/api/supervisors?eagerload=true"))
            .andExpect(status().isOk());

        verify(supervisorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSupervisorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(supervisorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupervisorMockMvc.perform(get("/api/supervisors?eagerload=true"))
            .andExpect(status().isOk());

        verify(supervisorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSupervisor() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get the supervisor
        restSupervisorMockMvc.perform(get("/api/supervisors/{id}", supervisor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supervisor.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingSupervisor() throws Exception {
        // Get the supervisor
        restSupervisorMockMvc.perform(get("/api/supervisors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupervisor() throws Exception {
        // Initialize the database
        supervisorService.save(supervisor);

        int databaseSizeBeforeUpdate = supervisorRepository.findAll().size();

        // Update the supervisor
        Supervisor updatedSupervisor = supervisorRepository.findById(supervisor.getId()).get();
        // Disconnect from session so that the updates on updatedSupervisor are not directly saved in db
        em.detach(updatedSupervisor);
        updatedSupervisor
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restSupervisorMockMvc.perform(put("/api/supervisors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSupervisor)))
            .andExpect(status().isOk());

        // Validate the Supervisor in the database
        List<Supervisor> supervisorList = supervisorRepository.findAll();
        assertThat(supervisorList).hasSize(databaseSizeBeforeUpdate);
        Supervisor testSupervisor = supervisorList.get(supervisorList.size() - 1);
        assertThat(testSupervisor.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingSupervisor() throws Exception {
        int databaseSizeBeforeUpdate = supervisorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupervisorMockMvc.perform(put("/api/supervisors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supervisor)))
            .andExpect(status().isBadRequest());

        // Validate the Supervisor in the database
        List<Supervisor> supervisorList = supervisorRepository.findAll();
        assertThat(supervisorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupervisor() throws Exception {
        // Initialize the database
        supervisorService.save(supervisor);

        int databaseSizeBeforeDelete = supervisorRepository.findAll().size();

        // Delete the supervisor
        restSupervisorMockMvc.perform(delete("/api/supervisors/{id}", supervisor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supervisor> supervisorList = supervisorRepository.findAll();
        assertThat(supervisorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
