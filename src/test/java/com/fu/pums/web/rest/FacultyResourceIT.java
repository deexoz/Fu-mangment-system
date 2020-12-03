package com.fu.pums.web.rest;

import com.fu.pums.PumsApp;
import com.fu.pums.domain.Faculty;
<<<<<<< HEAD
import com.fu.pums.repository.FacultyRepository;
import com.fu.pums.service.FacultyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
=======
import com.fu.pums.domain.Student;
import com.fu.pums.domain.Project;
import com.fu.pums.domain.Announcement;
import com.fu.pums.domain.Supervisor;
import com.fu.pums.repository.FacultyRepository;
import com.fu.pums.service.FacultyService;
import com.fu.pums.service.dto.FacultyCriteria;
import com.fu.pums.service.FacultyQueryService;

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
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
<<<<<<< HEAD
=======
import static org.mockito.Mockito.*;
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FacultyResource} REST controller.
 */
@SpringBootTest(classes = PumsApp.class)
<<<<<<< HEAD
=======
@ExtendWith(MockitoExtension.class)
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
@AutoConfigureMockMvc
@WithMockUser
public class FacultyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

<<<<<<< HEAD
    @Autowired
    private FacultyRepository facultyRepository;

=======
    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private FacultyRepository facultyRepository;

    @Mock
    private FacultyRepository facultyRepositoryMock;

    @Mock
    private FacultyService facultyServiceMock;

>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
    @Autowired
    private FacultyService facultyService;

    @Autowired
<<<<<<< HEAD
=======
    private FacultyQueryService facultyQueryService;

    @Autowired
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
    private EntityManager em;

    @Autowired
    private MockMvc restFacultyMockMvc;

    private Faculty faculty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Faculty createEntity(EntityManager em) {
        Faculty faculty = new Faculty()
<<<<<<< HEAD
            .name(DEFAULT_NAME);
=======
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
        return faculty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Faculty createUpdatedEntity(EntityManager em) {
        Faculty faculty = new Faculty()
<<<<<<< HEAD
            .name(UPDATED_NAME);
=======
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
        return faculty;
    }

    @BeforeEach
    public void initTest() {
        faculty = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaculty() throws Exception {
        int databaseSizeBeforeCreate = facultyRepository.findAll().size();
        // Create the Faculty
        restFacultyMockMvc.perform(post("/api/faculties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(faculty)))
            .andExpect(status().isCreated());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeCreate + 1);
        Faculty testFaculty = facultyList.get(facultyList.size() - 1);
        assertThat(testFaculty.getName()).isEqualTo(DEFAULT_NAME);
<<<<<<< HEAD
=======
        assertThat(testFaculty.getCode()).isEqualTo(DEFAULT_CODE);
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
    }

    @Test
    @Transactional
    public void createFacultyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facultyRepository.findAll().size();

        // Create the Faculty with an existing ID
        faculty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacultyMockMvc.perform(post("/api/faculties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(faculty)))
            .andExpect(status().isBadRequest());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFaculties() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList
        restFacultyMockMvc.perform(get("/api/faculties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faculty.getId().intValue())))
<<<<<<< HEAD
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
=======
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFacultiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(facultyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFacultyMockMvc.perform(get("/api/faculties?eagerload=true"))
            .andExpect(status().isOk());

        verify(facultyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFacultiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(facultyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFacultyMockMvc.perform(get("/api/faculties?eagerload=true"))
            .andExpect(status().isOk());

        verify(facultyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
    @Test
    @Transactional
    public void getFaculty() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get the faculty
        restFacultyMockMvc.perform(get("/api/faculties/{id}", faculty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(faculty.getId().intValue()))
<<<<<<< HEAD
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
=======
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }


    @Test
    @Transactional
    public void getFacultiesByIdFiltering() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        Long id = faculty.getId();

        defaultFacultyShouldBeFound("id.equals=" + id);
        defaultFacultyShouldNotBeFound("id.notEquals=" + id);

        defaultFacultyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFacultyShouldNotBeFound("id.greaterThan=" + id);

        defaultFacultyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFacultyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFacultiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where name equals to DEFAULT_NAME
        defaultFacultyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the facultyList where name equals to UPDATED_NAME
        defaultFacultyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFacultiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where name not equals to DEFAULT_NAME
        defaultFacultyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the facultyList where name not equals to UPDATED_NAME
        defaultFacultyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFacultiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFacultyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the facultyList where name equals to UPDATED_NAME
        defaultFacultyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFacultiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where name is not null
        defaultFacultyShouldBeFound("name.specified=true");

        // Get all the facultyList where name is null
        defaultFacultyShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllFacultiesByNameContainsSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where name contains DEFAULT_NAME
        defaultFacultyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the facultyList where name contains UPDATED_NAME
        defaultFacultyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFacultiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where name does not contain DEFAULT_NAME
        defaultFacultyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the facultyList where name does not contain UPDATED_NAME
        defaultFacultyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllFacultiesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where code equals to DEFAULT_CODE
        defaultFacultyShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the facultyList where code equals to UPDATED_CODE
        defaultFacultyShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFacultiesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where code not equals to DEFAULT_CODE
        defaultFacultyShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the facultyList where code not equals to UPDATED_CODE
        defaultFacultyShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFacultiesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where code in DEFAULT_CODE or UPDATED_CODE
        defaultFacultyShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the facultyList where code equals to UPDATED_CODE
        defaultFacultyShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFacultiesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where code is not null
        defaultFacultyShouldBeFound("code.specified=true");

        // Get all the facultyList where code is null
        defaultFacultyShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllFacultiesByCodeContainsSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where code contains DEFAULT_CODE
        defaultFacultyShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the facultyList where code contains UPDATED_CODE
        defaultFacultyShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFacultiesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList where code does not contain DEFAULT_CODE
        defaultFacultyShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the facultyList where code does not contain UPDATED_CODE
        defaultFacultyShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllFacultiesByStudentsIsEqualToSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);
        Student students = StudentResourceIT.createEntity(em);
        em.persist(students);
        em.flush();
        faculty.addStudents(students);
        facultyRepository.saveAndFlush(faculty);
        Long studentsId = students.getId();

        // Get all the facultyList where students equals to studentsId
        defaultFacultyShouldBeFound("studentsId.equals=" + studentsId);

        // Get all the facultyList where students equals to studentsId + 1
        defaultFacultyShouldNotBeFound("studentsId.equals=" + (studentsId + 1));
    }


    @Test
    @Transactional
    public void getAllFacultiesByProjectsIsEqualToSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);
        Project projects = ProjectResourceIT.createEntity(em);
        em.persist(projects);
        em.flush();
        faculty.addProjects(projects);
        facultyRepository.saveAndFlush(faculty);
        Long projectsId = projects.getId();

        // Get all the facultyList where projects equals to projectsId
        defaultFacultyShouldBeFound("projectsId.equals=" + projectsId);

        // Get all the facultyList where projects equals to projectsId + 1
        defaultFacultyShouldNotBeFound("projectsId.equals=" + (projectsId + 1));
    }


    @Test
    @Transactional
    public void getAllFacultiesByAnnouncmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);
        Announcement announcments = AnnouncementResourceIT.createEntity(em);
        em.persist(announcments);
        em.flush();
        faculty.addAnnouncments(announcments);
        facultyRepository.saveAndFlush(faculty);
        Long announcmentsId = announcments.getId();

        // Get all the facultyList where announcments equals to announcmentsId
        defaultFacultyShouldBeFound("announcmentsId.equals=" + announcmentsId);

        // Get all the facultyList where announcments equals to announcmentsId + 1
        defaultFacultyShouldNotBeFound("announcmentsId.equals=" + (announcmentsId + 1));
    }


    @Test
    @Transactional
    public void getAllFacultiesBySupervisorsIsEqualToSomething() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);
        Supervisor supervisors = SupervisorResourceIT.createEntity(em);
        em.persist(supervisors);
        em.flush();
        faculty.addSupervisors(supervisors);
        facultyRepository.saveAndFlush(faculty);
        Long supervisorsId = supervisors.getId();

        // Get all the facultyList where supervisors equals to supervisorsId
        defaultFacultyShouldBeFound("supervisorsId.equals=" + supervisorsId);

        // Get all the facultyList where supervisors equals to supervisorsId + 1
        defaultFacultyShouldNotBeFound("supervisorsId.equals=" + (supervisorsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFacultyShouldBeFound(String filter) throws Exception {
        restFacultyMockMvc.perform(get("/api/faculties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faculty.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));

        // Check, that the count call also returns 1
        restFacultyMockMvc.perform(get("/api/faculties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFacultyShouldNotBeFound(String filter) throws Exception {
        restFacultyMockMvc.perform(get("/api/faculties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFacultyMockMvc.perform(get("/api/faculties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
    @Test
    @Transactional
    public void getNonExistingFaculty() throws Exception {
        // Get the faculty
        restFacultyMockMvc.perform(get("/api/faculties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaculty() throws Exception {
        // Initialize the database
        facultyService.save(faculty);

        int databaseSizeBeforeUpdate = facultyRepository.findAll().size();

        // Update the faculty
        Faculty updatedFaculty = facultyRepository.findById(faculty.getId()).get();
        // Disconnect from session so that the updates on updatedFaculty are not directly saved in db
        em.detach(updatedFaculty);
        updatedFaculty
<<<<<<< HEAD
            .name(UPDATED_NAME);
=======
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity

        restFacultyMockMvc.perform(put("/api/faculties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaculty)))
            .andExpect(status().isOk());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeUpdate);
        Faculty testFaculty = facultyList.get(facultyList.size() - 1);
        assertThat(testFaculty.getName()).isEqualTo(UPDATED_NAME);
<<<<<<< HEAD
=======
        assertThat(testFaculty.getCode()).isEqualTo(UPDATED_CODE);
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
    }

    @Test
    @Transactional
    public void updateNonExistingFaculty() throws Exception {
        int databaseSizeBeforeUpdate = facultyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacultyMockMvc.perform(put("/api/faculties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(faculty)))
            .andExpect(status().isBadRequest());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFaculty() throws Exception {
        // Initialize the database
        facultyService.save(faculty);

        int databaseSizeBeforeDelete = facultyRepository.findAll().size();

        // Delete the faculty
        restFacultyMockMvc.perform(delete("/api/faculties/{id}", faculty.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
