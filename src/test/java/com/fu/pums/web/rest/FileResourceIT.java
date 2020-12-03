package com.fu.pums.web.rest;

import com.fu.pums.ProjecunitmangmentApp;
import com.fu.pums.domain.File;
import com.fu.pums.domain.Observation;
import com.fu.pums.domain.Project;
import com.fu.pums.repository.FileRepository;
import com.fu.pums.service.FileService;
import com.fu.pums.service.dto.FileCriteria;
import com.fu.pums.service.FileQueryService;

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
 * Integration tests for the {@link FileResource} REST controller.
 */
@SpringBootTest(classes = ProjecunitmangmentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FileResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_UPLOAD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileQueryService fileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileMockMvc;

    private File file;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static File createEntity(EntityManager em) {
        File file = new File()
            .name(DEFAULT_NAME)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .uploadDate(DEFAULT_UPLOAD_DATE)
            .type(DEFAULT_TYPE);
        return file;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static File createUpdatedEntity(EntityManager em) {
        File file = new File()
            .name(UPDATED_NAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .type(UPDATED_TYPE);
        return file;
    }

    @BeforeEach
    public void initTest() {
        file = createEntity(em);
    }

    @Test
    @Transactional
    public void createFile() throws Exception {
        int databaseSizeBeforeCreate = fileRepository.findAll().size();
        // Create the File
        restFileMockMvc.perform(post("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(file)))
            .andExpect(status().isCreated());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate + 1);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFile.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testFile.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testFile.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
        assertThat(testFile.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileRepository.findAll().size();

        // Create the File with an existing ID
        file.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileMockMvc.perform(post("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(file)))
            .andExpect(status().isBadRequest());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFiles() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList
        restFileMockMvc.perform(get("/api/files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(file.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(DEFAULT_UPLOAD_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getFile() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get the file
        restFileMockMvc.perform(get("/api/files/{id}", file.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(file.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.uploadDate").value(DEFAULT_UPLOAD_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }


    @Test
    @Transactional
    public void getFilesByIdFiltering() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        Long id = file.getId();

        defaultFileShouldBeFound("id.equals=" + id);
        defaultFileShouldNotBeFound("id.notEquals=" + id);

        defaultFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFileShouldNotBeFound("id.greaterThan=" + id);

        defaultFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFilesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where name equals to DEFAULT_NAME
        defaultFileShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the fileList where name equals to UPDATED_NAME
        defaultFileShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFilesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where name not equals to DEFAULT_NAME
        defaultFileShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the fileList where name not equals to UPDATED_NAME
        defaultFileShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFilesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFileShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the fileList where name equals to UPDATED_NAME
        defaultFileShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFilesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where name is not null
        defaultFileShouldBeFound("name.specified=true");

        // Get all the fileList where name is null
        defaultFileShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllFilesByNameContainsSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where name contains DEFAULT_NAME
        defaultFileShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the fileList where name contains UPDATED_NAME
        defaultFileShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFilesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where name does not contain DEFAULT_NAME
        defaultFileShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the fileList where name does not contain UPDATED_NAME
        defaultFileShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllFilesByUploadDateIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where uploadDate equals to DEFAULT_UPLOAD_DATE
        defaultFileShouldBeFound("uploadDate.equals=" + DEFAULT_UPLOAD_DATE);

        // Get all the fileList where uploadDate equals to UPDATED_UPLOAD_DATE
        defaultFileShouldNotBeFound("uploadDate.equals=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    public void getAllFilesByUploadDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where uploadDate not equals to DEFAULT_UPLOAD_DATE
        defaultFileShouldNotBeFound("uploadDate.notEquals=" + DEFAULT_UPLOAD_DATE);

        // Get all the fileList where uploadDate not equals to UPDATED_UPLOAD_DATE
        defaultFileShouldBeFound("uploadDate.notEquals=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    public void getAllFilesByUploadDateIsInShouldWork() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where uploadDate in DEFAULT_UPLOAD_DATE or UPDATED_UPLOAD_DATE
        defaultFileShouldBeFound("uploadDate.in=" + DEFAULT_UPLOAD_DATE + "," + UPDATED_UPLOAD_DATE);

        // Get all the fileList where uploadDate equals to UPDATED_UPLOAD_DATE
        defaultFileShouldNotBeFound("uploadDate.in=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    public void getAllFilesByUploadDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where uploadDate is not null
        defaultFileShouldBeFound("uploadDate.specified=true");

        // Get all the fileList where uploadDate is null
        defaultFileShouldNotBeFound("uploadDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where type equals to DEFAULT_TYPE
        defaultFileShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the fileList where type equals to UPDATED_TYPE
        defaultFileShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllFilesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where type not equals to DEFAULT_TYPE
        defaultFileShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the fileList where type not equals to UPDATED_TYPE
        defaultFileShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllFilesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultFileShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the fileList where type equals to UPDATED_TYPE
        defaultFileShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllFilesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where type is not null
        defaultFileShouldBeFound("type.specified=true");

        // Get all the fileList where type is null
        defaultFileShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllFilesByTypeContainsSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where type contains DEFAULT_TYPE
        defaultFileShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the fileList where type contains UPDATED_TYPE
        defaultFileShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllFilesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where type does not contain DEFAULT_TYPE
        defaultFileShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the fileList where type does not contain UPDATED_TYPE
        defaultFileShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllFilesByObservationIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);
        Observation observation = ObservationResourceIT.createEntity(em);
        em.persist(observation);
        em.flush();
        file.setObservation(observation);
        observation.setFile(file);
        fileRepository.saveAndFlush(file);
        Long observationId = observation.getId();

        // Get all the fileList where observation equals to observationId
        defaultFileShouldBeFound("observationId.equals=" + observationId);

        // Get all the fileList where observation equals to observationId + 1
        defaultFileShouldNotBeFound("observationId.equals=" + (observationId + 1));
    }


    @Test
    @Transactional
    public void getAllFilesByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);
        Project project = ProjectResourceIT.createEntity(em);
        em.persist(project);
        em.flush();
        file.setProject(project);
        fileRepository.saveAndFlush(file);
        Long projectId = project.getId();

        // Get all the fileList where project equals to projectId
        defaultFileShouldBeFound("projectId.equals=" + projectId);

        // Get all the fileList where project equals to projectId + 1
        defaultFileShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFileShouldBeFound(String filter) throws Exception {
        restFileMockMvc.perform(get("/api/files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(file.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(DEFAULT_UPLOAD_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restFileMockMvc.perform(get("/api/files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFileShouldNotBeFound(String filter) throws Exception {
        restFileMockMvc.perform(get("/api/files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFileMockMvc.perform(get("/api/files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFile() throws Exception {
        // Get the file
        restFileMockMvc.perform(get("/api/files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFile() throws Exception {
        // Initialize the database
        fileService.save(file);

        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // Update the file
        File updatedFile = fileRepository.findById(file.getId()).get();
        // Disconnect from session so that the updates on updatedFile are not directly saved in db
        em.detach(updatedFile);
        updatedFile
            .name(UPDATED_NAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .type(UPDATED_TYPE);

        restFileMockMvc.perform(put("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFile)))
            .andExpect(status().isOk());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFile.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testFile.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testFile.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testFile.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFile() throws Exception {
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileMockMvc.perform(put("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(file)))
            .andExpect(status().isBadRequest());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFile() throws Exception {
        // Initialize the database
        fileService.save(file);

        int databaseSizeBeforeDelete = fileRepository.findAll().size();

        // Delete the file
        restFileMockMvc.perform(delete("/api/files/{id}", file.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
