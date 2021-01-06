package com.fu.pums.web.rest;

import com.fu.pums.ProjecunitmangmentApp;
import com.fu.pums.domain.Student;
import com.fu.pums.domain.User;
import com.fu.pums.domain.Faculty;
import com.fu.pums.domain.Project;
import com.fu.pums.repository.StudentRepository;
import com.fu.pums.service.StudentService;
import com.fu.pums.service.dto.StudentCriteria;
import com.fu.pums.service.StudentQueryService;

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

/**
 * Integration tests for the {@link StudentResource} REST controller.
 */
@SpringBootTest(classes = ProjecunitmangmentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StudentResourceIT {

    private static final String DEFAULT_INDEX = "AAAAAAAAAA";
    private static final String UPDATED_INDEX = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentQueryService studentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentMockMvc;

    private Student student;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .index(DEFAULT_INDEX)
            .fullNameArabic(DEFAULT_FULL_NAME_ARABIC)
            .phone(DEFAULT_PHONE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        student.setUser(user);
        return student;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student()
            .index(UPDATED_INDEX)
            .fullNameArabic(UPDATED_FULL_NAME_ARABIC)
            .phone(UPDATED_PHONE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        student.setUser(user);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();
        // Create the Student
        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testStudent.getFullNameArabic()).isEqualTo(DEFAULT_FULL_NAME_ARABIC);
        assertThat(testStudent.getPhone()).isEqualTo(DEFAULT_PHONE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testStudent.getId()).isEqualTo(testStudent.getUser().getId());
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateStudentMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        studentService.save(student);
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);

        // Update the User with new association value
        updatedStudent.setUser(user);

        // Update the entity
        restStudentMockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
        Student testStudent = studentList.get(studentList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testStudent.getId()).isEqualTo(testStudent.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].fullNameArabic").value(hasItem(DEFAULT_FULL_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.fullNameArabic").value(DEFAULT_FULL_NAME_ARABIC))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }


    @Test
    @Transactional
    public void getStudentsByIdFiltering() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        Long id = student.getId();

        defaultStudentShouldBeFound("id.equals=" + id);
        defaultStudentShouldNotBeFound("id.notEquals=" + id);

        defaultStudentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStudentsByIndexIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where index equals to DEFAULT_INDEX
        defaultStudentShouldBeFound("index.equals=" + DEFAULT_INDEX);

        // Get all the studentList where index equals to UPDATED_INDEX
        defaultStudentShouldNotBeFound("index.equals=" + UPDATED_INDEX);
    }

    @Test
    @Transactional
    public void getAllStudentsByIndexIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where index not equals to DEFAULT_INDEX
        defaultStudentShouldNotBeFound("index.notEquals=" + DEFAULT_INDEX);

        // Get all the studentList where index not equals to UPDATED_INDEX
        defaultStudentShouldBeFound("index.notEquals=" + UPDATED_INDEX);
    }

    @Test
    @Transactional
    public void getAllStudentsByIndexIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where index in DEFAULT_INDEX or UPDATED_INDEX
        defaultStudentShouldBeFound("index.in=" + DEFAULT_INDEX + "," + UPDATED_INDEX);

        // Get all the studentList where index equals to UPDATED_INDEX
        defaultStudentShouldNotBeFound("index.in=" + UPDATED_INDEX);
    }

    @Test
    @Transactional
    public void getAllStudentsByIndexIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where index is not null
        defaultStudentShouldBeFound("index.specified=true");

        // Get all the studentList where index is null
        defaultStudentShouldNotBeFound("index.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByIndexContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where index contains DEFAULT_INDEX
        defaultStudentShouldBeFound("index.contains=" + DEFAULT_INDEX);

        // Get all the studentList where index contains UPDATED_INDEX
        defaultStudentShouldNotBeFound("index.contains=" + UPDATED_INDEX);
    }

    @Test
    @Transactional
    public void getAllStudentsByIndexNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where index does not contain DEFAULT_INDEX
        defaultStudentShouldNotBeFound("index.doesNotContain=" + DEFAULT_INDEX);

        // Get all the studentList where index does not contain UPDATED_INDEX
        defaultStudentShouldBeFound("index.doesNotContain=" + UPDATED_INDEX);
    }


    @Test
    @Transactional
    public void getAllStudentsByFullNameArabicIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where fullNameArabic equals to DEFAULT_FULL_NAME_ARABIC
        defaultStudentShouldBeFound("fullNameArabic.equals=" + DEFAULT_FULL_NAME_ARABIC);

        // Get all the studentList where fullNameArabic equals to UPDATED_FULL_NAME_ARABIC
        defaultStudentShouldNotBeFound("fullNameArabic.equals=" + UPDATED_FULL_NAME_ARABIC);
    }

    @Test
    @Transactional
    public void getAllStudentsByFullNameArabicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where fullNameArabic not equals to DEFAULT_FULL_NAME_ARABIC
        defaultStudentShouldNotBeFound("fullNameArabic.notEquals=" + DEFAULT_FULL_NAME_ARABIC);

        // Get all the studentList where fullNameArabic not equals to UPDATED_FULL_NAME_ARABIC
        defaultStudentShouldBeFound("fullNameArabic.notEquals=" + UPDATED_FULL_NAME_ARABIC);
    }

    @Test
    @Transactional
    public void getAllStudentsByFullNameArabicIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where fullNameArabic in DEFAULT_FULL_NAME_ARABIC or UPDATED_FULL_NAME_ARABIC
        defaultStudentShouldBeFound("fullNameArabic.in=" + DEFAULT_FULL_NAME_ARABIC + "," + UPDATED_FULL_NAME_ARABIC);

        // Get all the studentList where fullNameArabic equals to UPDATED_FULL_NAME_ARABIC
        defaultStudentShouldNotBeFound("fullNameArabic.in=" + UPDATED_FULL_NAME_ARABIC);
    }

    @Test
    @Transactional
    public void getAllStudentsByFullNameArabicIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where fullNameArabic is not null
        defaultStudentShouldBeFound("fullNameArabic.specified=true");

        // Get all the studentList where fullNameArabic is null
        defaultStudentShouldNotBeFound("fullNameArabic.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByFullNameArabicContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where fullNameArabic contains DEFAULT_FULL_NAME_ARABIC
        defaultStudentShouldBeFound("fullNameArabic.contains=" + DEFAULT_FULL_NAME_ARABIC);

        // Get all the studentList where fullNameArabic contains UPDATED_FULL_NAME_ARABIC
        defaultStudentShouldNotBeFound("fullNameArabic.contains=" + UPDATED_FULL_NAME_ARABIC);
    }

    @Test
    @Transactional
    public void getAllStudentsByFullNameArabicNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where fullNameArabic does not contain DEFAULT_FULL_NAME_ARABIC
        defaultStudentShouldNotBeFound("fullNameArabic.doesNotContain=" + DEFAULT_FULL_NAME_ARABIC);

        // Get all the studentList where fullNameArabic does not contain UPDATED_FULL_NAME_ARABIC
        defaultStudentShouldBeFound("fullNameArabic.doesNotContain=" + UPDATED_FULL_NAME_ARABIC);
    }


    @Test
    @Transactional
    public void getAllStudentsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where phone equals to DEFAULT_PHONE
        defaultStudentShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the studentList where phone equals to UPDATED_PHONE
        defaultStudentShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllStudentsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where phone not equals to DEFAULT_PHONE
        defaultStudentShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the studentList where phone not equals to UPDATED_PHONE
        defaultStudentShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllStudentsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultStudentShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the studentList where phone equals to UPDATED_PHONE
        defaultStudentShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllStudentsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where phone is not null
        defaultStudentShouldBeFound("phone.specified=true");

        // Get all the studentList where phone is null
        defaultStudentShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where phone contains DEFAULT_PHONE
        defaultStudentShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the studentList where phone contains UPDATED_PHONE
        defaultStudentShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllStudentsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where phone does not contain DEFAULT_PHONE
        defaultStudentShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the studentList where phone does not contain UPDATED_PHONE
        defaultStudentShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllStudentsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = student.getUser();
        studentRepository.saveAndFlush(student);
        Long userId = user.getId();

        // Get all the studentList where user equals to userId
        defaultStudentShouldBeFound("userId.equals=" + userId);

        // Get all the studentList where user equals to userId + 1
        defaultStudentShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllStudentsByFacultyIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);
        Faculty faculty = FacultyResourceIT.createEntity(em);
        em.persist(faculty);
        em.flush();
        student.setFaculty(faculty);
        studentRepository.saveAndFlush(student);
        Long facultyId = faculty.getId();

        // Get all the studentList where faculty equals to facultyId
        defaultStudentShouldBeFound("facultyId.equals=" + facultyId);

        // Get all the studentList where faculty equals to facultyId + 1
        defaultStudentShouldNotBeFound("facultyId.equals=" + (facultyId + 1));
    }


    @Test
    @Transactional
    public void getAllStudentsByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);
        Project project = ProjectResourceIT.createEntity(em);
        em.persist(project);
        em.flush();
        student.setProject(project);
        studentRepository.saveAndFlush(student);
        Long projectId = project.getId();

        // Get all the studentList where project equals to projectId
        defaultStudentShouldBeFound("projectId.equals=" + projectId);

        // Get all the studentList where project equals to projectId + 1
        defaultStudentShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentShouldBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].fullNameArabic").value(hasItem(DEFAULT_FULL_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));

        // Check, that the count call also returns 1
        restStudentMockMvc.perform(get("/api/students/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentShouldNotBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentMockMvc.perform(get("/api/students/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .index(UPDATED_INDEX)
            .fullNameArabic(UPDATED_FULL_NAME_ARABIC)
            .phone(UPDATED_PHONE);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testStudent.getFullNameArabic()).isEqualTo(UPDATED_FULL_NAME_ARABIC);
        assertThat(testStudent.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
