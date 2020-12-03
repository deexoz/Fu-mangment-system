package com.fu.pums.web.rest;

import com.fu.pums.ProjecunitmangmentApp;
import com.fu.pums.domain.Supervisor;
import com.fu.pums.domain.Project;
import com.fu.pums.domain.Faculty;
import com.fu.pums.repository.SupervisorRepository;
import com.fu.pums.service.SupervisorService;
import com.fu.pums.service.dto.SupervisorCriteria;
import com.fu.pums.service.SupervisorQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fu.pums.domain.enumeration.Gender;
/**
 * Integration tests for the {@link SupervisorResource} REST controller.
 */
@SpringBootTest(classes = ProjecunitmangmentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SupervisorResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    private SupervisorQueryService supervisorQueryService;

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
            .fullName(DEFAULT_FULL_NAME)
            .role(DEFAULT_ROLE)
            .gender(DEFAULT_GENDER)
            .email(DEFAULT_EMAIL)
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
            .fullName(UPDATED_FULL_NAME)
            .role(UPDATED_ROLE)
            .gender(UPDATED_GENDER)
            .email(UPDATED_EMAIL)
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
        assertThat(testSupervisor.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testSupervisor.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testSupervisor.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testSupervisor.getEmail()).isEqualTo(DEFAULT_EMAIL);
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
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
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
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }


    @Test
    @Transactional
    public void getSupervisorsByIdFiltering() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        Long id = supervisor.getId();

        defaultSupervisorShouldBeFound("id.equals=" + id);
        defaultSupervisorShouldNotBeFound("id.notEquals=" + id);

        defaultSupervisorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupervisorShouldNotBeFound("id.greaterThan=" + id);

        defaultSupervisorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupervisorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSupervisorsByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where fullName equals to DEFAULT_FULL_NAME
        defaultSupervisorShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the supervisorList where fullName equals to UPDATED_FULL_NAME
        defaultSupervisorShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where fullName not equals to DEFAULT_FULL_NAME
        defaultSupervisorShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the supervisorList where fullName not equals to UPDATED_FULL_NAME
        defaultSupervisorShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultSupervisorShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the supervisorList where fullName equals to UPDATED_FULL_NAME
        defaultSupervisorShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where fullName is not null
        defaultSupervisorShouldBeFound("fullName.specified=true");

        // Get all the supervisorList where fullName is null
        defaultSupervisorShouldNotBeFound("fullName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupervisorsByFullNameContainsSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where fullName contains DEFAULT_FULL_NAME
        defaultSupervisorShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the supervisorList where fullName contains UPDATED_FULL_NAME
        defaultSupervisorShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where fullName does not contain DEFAULT_FULL_NAME
        defaultSupervisorShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the supervisorList where fullName does not contain UPDATED_FULL_NAME
        defaultSupervisorShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }


    @Test
    @Transactional
    public void getAllSupervisorsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where role equals to DEFAULT_ROLE
        defaultSupervisorShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the supervisorList where role equals to UPDATED_ROLE
        defaultSupervisorShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where role not equals to DEFAULT_ROLE
        defaultSupervisorShouldNotBeFound("role.notEquals=" + DEFAULT_ROLE);

        // Get all the supervisorList where role not equals to UPDATED_ROLE
        defaultSupervisorShouldBeFound("role.notEquals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultSupervisorShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the supervisorList where role equals to UPDATED_ROLE
        defaultSupervisorShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where role is not null
        defaultSupervisorShouldBeFound("role.specified=true");

        // Get all the supervisorList where role is null
        defaultSupervisorShouldNotBeFound("role.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupervisorsByRoleContainsSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where role contains DEFAULT_ROLE
        defaultSupervisorShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the supervisorList where role contains UPDATED_ROLE
        defaultSupervisorShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where role does not contain DEFAULT_ROLE
        defaultSupervisorShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the supervisorList where role does not contain UPDATED_ROLE
        defaultSupervisorShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }


    @Test
    @Transactional
    public void getAllSupervisorsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where gender equals to DEFAULT_GENDER
        defaultSupervisorShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the supervisorList where gender equals to UPDATED_GENDER
        defaultSupervisorShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where gender not equals to DEFAULT_GENDER
        defaultSupervisorShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the supervisorList where gender not equals to UPDATED_GENDER
        defaultSupervisorShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultSupervisorShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the supervisorList where gender equals to UPDATED_GENDER
        defaultSupervisorShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where gender is not null
        defaultSupervisorShouldBeFound("gender.specified=true");

        // Get all the supervisorList where gender is null
        defaultSupervisorShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupervisorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where email equals to DEFAULT_EMAIL
        defaultSupervisorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the supervisorList where email equals to UPDATED_EMAIL
        defaultSupervisorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where email not equals to DEFAULT_EMAIL
        defaultSupervisorShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the supervisorList where email not equals to UPDATED_EMAIL
        defaultSupervisorShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultSupervisorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the supervisorList where email equals to UPDATED_EMAIL
        defaultSupervisorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where email is not null
        defaultSupervisorShouldBeFound("email.specified=true");

        // Get all the supervisorList where email is null
        defaultSupervisorShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupervisorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where email contains DEFAULT_EMAIL
        defaultSupervisorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the supervisorList where email contains UPDATED_EMAIL
        defaultSupervisorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where email does not contain DEFAULT_EMAIL
        defaultSupervisorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the supervisorList where email does not contain UPDATED_EMAIL
        defaultSupervisorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllSupervisorsByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultSupervisorShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the supervisorList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSupervisorShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultSupervisorShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the supervisorList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultSupervisorShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultSupervisorShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the supervisorList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSupervisorShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where phoneNumber is not null
        defaultSupervisorShouldBeFound("phoneNumber.specified=true");

        // Get all the supervisorList where phoneNumber is null
        defaultSupervisorShouldNotBeFound("phoneNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupervisorsByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultSupervisorShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the supervisorList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultSupervisorShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSupervisorsByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);

        // Get all the supervisorList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultSupervisorShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the supervisorList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultSupervisorShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSupervisorsByProjectsIsEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);
        Project projects = ProjectResourceIT.createEntity(em);
        em.persist(projects);
        em.flush();
        supervisor.addProjects(projects);
        supervisorRepository.saveAndFlush(supervisor);
        Long projectsId = projects.getId();

        // Get all the supervisorList where projects equals to projectsId
        defaultSupervisorShouldBeFound("projectsId.equals=" + projectsId);

        // Get all the supervisorList where projects equals to projectsId + 1
        defaultSupervisorShouldNotBeFound("projectsId.equals=" + (projectsId + 1));
    }


    @Test
    @Transactional
    public void getAllSupervisorsByFacultiesIsEqualToSomething() throws Exception {
        // Initialize the database
        supervisorRepository.saveAndFlush(supervisor);
        Faculty faculties = FacultyResourceIT.createEntity(em);
        em.persist(faculties);
        em.flush();
        supervisor.addFaculties(faculties);
        supervisorRepository.saveAndFlush(supervisor);
        Long facultiesId = faculties.getId();

        // Get all the supervisorList where faculties equals to facultiesId
        defaultSupervisorShouldBeFound("facultiesId.equals=" + facultiesId);

        // Get all the supervisorList where faculties equals to facultiesId + 1
        defaultSupervisorShouldNotBeFound("facultiesId.equals=" + (facultiesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupervisorShouldBeFound(String filter) throws Exception {
        restSupervisorMockMvc.perform(get("/api/supervisors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supervisor.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));

        // Check, that the count call also returns 1
        restSupervisorMockMvc.perform(get("/api/supervisors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupervisorShouldNotBeFound(String filter) throws Exception {
        restSupervisorMockMvc.perform(get("/api/supervisors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupervisorMockMvc.perform(get("/api/supervisors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .fullName(UPDATED_FULL_NAME)
            .role(UPDATED_ROLE)
            .gender(UPDATED_GENDER)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restSupervisorMockMvc.perform(put("/api/supervisors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSupervisor)))
            .andExpect(status().isOk());

        // Validate the Supervisor in the database
        List<Supervisor> supervisorList = supervisorRepository.findAll();
        assertThat(supervisorList).hasSize(databaseSizeBeforeUpdate);
        Supervisor testSupervisor = supervisorList.get(supervisorList.size() - 1);
        assertThat(testSupervisor.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testSupervisor.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testSupervisor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSupervisor.getEmail()).isEqualTo(UPDATED_EMAIL);
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
