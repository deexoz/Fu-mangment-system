package com.fu.pums.web.rest;

import com.fu.pums.ProjecunitmangmentApp;
import com.fu.pums.domain.Announcement;
import com.fu.pums.domain.Faculty;
import com.fu.pums.repository.AnnouncementRepository;
import com.fu.pums.service.AnnouncementService;
import com.fu.pums.service.dto.AnnouncementCriteria;
import com.fu.pums.service.AnnouncementQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fu.pums.domain.enumeration.AnnouncementType;
/**
 * Integration tests for the {@link AnnouncementResource} REST controller.
 */
@SpringBootTest(classes = ProjecunitmangmentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AnnouncementResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final AnnouncementType DEFAULT_ANNOUNCEMENT_TYPE = AnnouncementType.SUBMISSION;
    private static final AnnouncementType UPDATED_ANNOUNCEMENT_TYPE = AnnouncementType.DISCUSSIONS;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_OPEN = false;
    private static final Boolean UPDATED_OPEN = true;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnnouncementQueryService announcementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnnouncementMockMvc;

    private Announcement announcement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .announcementType(DEFAULT_ANNOUNCEMENT_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .open(DEFAULT_OPEN);
        return announcement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createUpdatedEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .announcementType(UPDATED_ANNOUNCEMENT_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .open(UPDATED_OPEN);
        return announcement;
    }

    @BeforeEach
    public void initTest() {
        announcement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnouncement() throws Exception {
        int databaseSizeBeforeCreate = announcementRepository.findAll().size();
        // Create the Announcement
        restAnnouncementMockMvc.perform(post("/api/announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isCreated());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeCreate + 1);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAnnouncement.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAnnouncement.getAnnouncementType()).isEqualTo(DEFAULT_ANNOUNCEMENT_TYPE);
        assertThat(testAnnouncement.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAnnouncement.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testAnnouncement.isOpen()).isEqualTo(DEFAULT_OPEN);
    }

    @Test
    @Transactional
    public void createAnnouncementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = announcementRepository.findAll().size();

        // Create the Announcement with an existing ID
        announcement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnouncementMockMvc.perform(post("/api/announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnnouncements() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList
        restAnnouncementMockMvc.perform(get("/api/announcements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(announcement.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].announcementType").value(hasItem(DEFAULT_ANNOUNCEMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].open").value(hasItem(DEFAULT_OPEN.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get the announcement
        restAnnouncementMockMvc.perform(get("/api/announcements/{id}", announcement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(announcement.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.announcementType").value(DEFAULT_ANNOUNCEMENT_TYPE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.open").value(DEFAULT_OPEN.booleanValue()));
    }


    @Test
    @Transactional
    public void getAnnouncementsByIdFiltering() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        Long id = announcement.getId();

        defaultAnnouncementShouldBeFound("id.equals=" + id);
        defaultAnnouncementShouldNotBeFound("id.notEquals=" + id);

        defaultAnnouncementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnnouncementShouldNotBeFound("id.greaterThan=" + id);

        defaultAnnouncementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnnouncementShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnnouncementsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where title equals to DEFAULT_TITLE
        defaultAnnouncementShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the announcementList where title equals to UPDATED_TITLE
        defaultAnnouncementShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where title not equals to DEFAULT_TITLE
        defaultAnnouncementShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the announcementList where title not equals to UPDATED_TITLE
        defaultAnnouncementShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultAnnouncementShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the announcementList where title equals to UPDATED_TITLE
        defaultAnnouncementShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where title is not null
        defaultAnnouncementShouldBeFound("title.specified=true");

        // Get all the announcementList where title is null
        defaultAnnouncementShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnnouncementsByTitleContainsSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where title contains DEFAULT_TITLE
        defaultAnnouncementShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the announcementList where title contains UPDATED_TITLE
        defaultAnnouncementShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where title does not contain DEFAULT_TITLE
        defaultAnnouncementShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the announcementList where title does not contain UPDATED_TITLE
        defaultAnnouncementShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllAnnouncementsByAnnouncementTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where announcementType equals to DEFAULT_ANNOUNCEMENT_TYPE
        defaultAnnouncementShouldBeFound("announcementType.equals=" + DEFAULT_ANNOUNCEMENT_TYPE);

        // Get all the announcementList where announcementType equals to UPDATED_ANNOUNCEMENT_TYPE
        defaultAnnouncementShouldNotBeFound("announcementType.equals=" + UPDATED_ANNOUNCEMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByAnnouncementTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where announcementType not equals to DEFAULT_ANNOUNCEMENT_TYPE
        defaultAnnouncementShouldNotBeFound("announcementType.notEquals=" + DEFAULT_ANNOUNCEMENT_TYPE);

        // Get all the announcementList where announcementType not equals to UPDATED_ANNOUNCEMENT_TYPE
        defaultAnnouncementShouldBeFound("announcementType.notEquals=" + UPDATED_ANNOUNCEMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByAnnouncementTypeIsInShouldWork() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where announcementType in DEFAULT_ANNOUNCEMENT_TYPE or UPDATED_ANNOUNCEMENT_TYPE
        defaultAnnouncementShouldBeFound("announcementType.in=" + DEFAULT_ANNOUNCEMENT_TYPE + "," + UPDATED_ANNOUNCEMENT_TYPE);

        // Get all the announcementList where announcementType equals to UPDATED_ANNOUNCEMENT_TYPE
        defaultAnnouncementShouldNotBeFound("announcementType.in=" + UPDATED_ANNOUNCEMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByAnnouncementTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where announcementType is not null
        defaultAnnouncementShouldBeFound("announcementType.specified=true");

        // Get all the announcementList where announcementType is null
        defaultAnnouncementShouldNotBeFound("announcementType.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where startDate equals to DEFAULT_START_DATE
        defaultAnnouncementShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the announcementList where startDate equals to UPDATED_START_DATE
        defaultAnnouncementShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where startDate not equals to DEFAULT_START_DATE
        defaultAnnouncementShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the announcementList where startDate not equals to UPDATED_START_DATE
        defaultAnnouncementShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultAnnouncementShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the announcementList where startDate equals to UPDATED_START_DATE
        defaultAnnouncementShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where startDate is not null
        defaultAnnouncementShouldBeFound("startDate.specified=true");

        // Get all the announcementList where startDate is null
        defaultAnnouncementShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultAnnouncementShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the announcementList where startDate is greater than or equal to UPDATED_START_DATE
        defaultAnnouncementShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where startDate is less than or equal to DEFAULT_START_DATE
        defaultAnnouncementShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the announcementList where startDate is less than or equal to SMALLER_START_DATE
        defaultAnnouncementShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where startDate is less than DEFAULT_START_DATE
        defaultAnnouncementShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the announcementList where startDate is less than UPDATED_START_DATE
        defaultAnnouncementShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where startDate is greater than DEFAULT_START_DATE
        defaultAnnouncementShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the announcementList where startDate is greater than SMALLER_START_DATE
        defaultAnnouncementShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllAnnouncementsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where endDate equals to DEFAULT_END_DATE
        defaultAnnouncementShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the announcementList where endDate equals to UPDATED_END_DATE
        defaultAnnouncementShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where endDate not equals to DEFAULT_END_DATE
        defaultAnnouncementShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the announcementList where endDate not equals to UPDATED_END_DATE
        defaultAnnouncementShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultAnnouncementShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the announcementList where endDate equals to UPDATED_END_DATE
        defaultAnnouncementShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where endDate is not null
        defaultAnnouncementShouldBeFound("endDate.specified=true");

        // Get all the announcementList where endDate is null
        defaultAnnouncementShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultAnnouncementShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the announcementList where endDate is greater than or equal to UPDATED_END_DATE
        defaultAnnouncementShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where endDate is less than or equal to DEFAULT_END_DATE
        defaultAnnouncementShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the announcementList where endDate is less than or equal to SMALLER_END_DATE
        defaultAnnouncementShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where endDate is less than DEFAULT_END_DATE
        defaultAnnouncementShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the announcementList where endDate is less than UPDATED_END_DATE
        defaultAnnouncementShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where endDate is greater than DEFAULT_END_DATE
        defaultAnnouncementShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the announcementList where endDate is greater than SMALLER_END_DATE
        defaultAnnouncementShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllAnnouncementsByOpenIsEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where open equals to DEFAULT_OPEN
        defaultAnnouncementShouldBeFound("open.equals=" + DEFAULT_OPEN);

        // Get all the announcementList where open equals to UPDATED_OPEN
        defaultAnnouncementShouldNotBeFound("open.equals=" + UPDATED_OPEN);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByOpenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where open not equals to DEFAULT_OPEN
        defaultAnnouncementShouldNotBeFound("open.notEquals=" + DEFAULT_OPEN);

        // Get all the announcementList where open not equals to UPDATED_OPEN
        defaultAnnouncementShouldBeFound("open.notEquals=" + UPDATED_OPEN);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByOpenIsInShouldWork() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where open in DEFAULT_OPEN or UPDATED_OPEN
        defaultAnnouncementShouldBeFound("open.in=" + DEFAULT_OPEN + "," + UPDATED_OPEN);

        // Get all the announcementList where open equals to UPDATED_OPEN
        defaultAnnouncementShouldNotBeFound("open.in=" + UPDATED_OPEN);
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByOpenIsNullOrNotNull() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList where open is not null
        defaultAnnouncementShouldBeFound("open.specified=true");

        // Get all the announcementList where open is null
        defaultAnnouncementShouldNotBeFound("open.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnnouncementsByFacultyIsEqualToSomething() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);
        Faculty faculty = FacultyResourceIT.createEntity(em);
        em.persist(faculty);
        em.flush();
        announcement.setFaculty(faculty);
        announcementRepository.saveAndFlush(announcement);
        Long facultyId = faculty.getId();

        // Get all the announcementList where faculty equals to facultyId
        defaultAnnouncementShouldBeFound("facultyId.equals=" + facultyId);

        // Get all the announcementList where faculty equals to facultyId + 1
        defaultAnnouncementShouldNotBeFound("facultyId.equals=" + (facultyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnnouncementShouldBeFound(String filter) throws Exception {
        restAnnouncementMockMvc.perform(get("/api/announcements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(announcement.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].announcementType").value(hasItem(DEFAULT_ANNOUNCEMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].open").value(hasItem(DEFAULT_OPEN.booleanValue())));

        // Check, that the count call also returns 1
        restAnnouncementMockMvc.perform(get("/api/announcements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnnouncementShouldNotBeFound(String filter) throws Exception {
        restAnnouncementMockMvc.perform(get("/api/announcements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnnouncementMockMvc.perform(get("/api/announcements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAnnouncement() throws Exception {
        // Get the announcement
        restAnnouncementMockMvc.perform(get("/api/announcements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnouncement() throws Exception {
        // Initialize the database
        announcementService.save(announcement);

        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Update the announcement
        Announcement updatedAnnouncement = announcementRepository.findById(announcement.getId()).get();
        // Disconnect from session so that the updates on updatedAnnouncement are not directly saved in db
        em.detach(updatedAnnouncement);
        updatedAnnouncement
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .announcementType(UPDATED_ANNOUNCEMENT_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .open(UPDATED_OPEN);

        restAnnouncementMockMvc.perform(put("/api/announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnnouncement)))
            .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAnnouncement.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAnnouncement.getAnnouncementType()).isEqualTo(UPDATED_ANNOUNCEMENT_TYPE);
        assertThat(testAnnouncement.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAnnouncement.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testAnnouncement.isOpen()).isEqualTo(UPDATED_OPEN);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc.perform(put("/api/announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnnouncement() throws Exception {
        // Initialize the database
        announcementService.save(announcement);

        int databaseSizeBeforeDelete = announcementRepository.findAll().size();

        // Delete the announcement
        restAnnouncementMockMvc.perform(delete("/api/announcements/{id}", announcement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
