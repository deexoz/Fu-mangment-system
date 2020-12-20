package com.fu.pums.web.rest;

import com.fu.pums.domain.*;
import com.fu.pums.security.AuthoritiesConstants;
import com.fu.pums.service.ProjectService;
import com.fu.pums.service.StudentService;
import com.fu.pums.service.UserService;
import com.fu.pums.service.dto.StudentProjectDTO;
import com.fu.pums.service.dto.UserDTO;
import com.fu.pums.web.rest.errors.BadRequestAlertException;
import com.fu.pums.service.dto.ProjectCriteria;
import com.fu.pums.service.ProjectQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.fu.pums.domain.Project}.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    private static final String ENTITY_NAME = "project";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectService projectService;

    private final ProjectQueryService projectQueryService;

    private final StudentService studentService;

    private final UserService userService;

    public ProjectResource(ProjectService projectService, ProjectQueryService projectQueryService,
            StudentService studentService, UserService userService) {
        this.projectService = projectService;
        this.projectQueryService = projectQueryService;
        this.studentService = studentService;
        this.userService = userService;
    }

    /**
     * {@code POST  /projects} : Create a new project.
     *
     * @param project the project to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new project, or with status {@code 400 (Bad Request)} if the
     *         project has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to save Project : {}", project);
        if (project.getId() != null) {
            throw new BadRequestAlertException("A new project cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Project result = projectService.save(project);
        return ResponseEntity
                .created(new URI("/api/projects/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }
    @PreAuthorize(AuthoritiesConstants.ADMIN)
    @PostMapping("/studentsProject")
    public ResponseEntity<Project> createProject(@RequestBody StudentProjectDTO studentProjectDTO)
            throws URISyntaxException {
        if (studentProjectDTO.getStudents().size() > 2) {
            throw new BadRequestAlertException("Project cannot be more the 2 students", studentProjectDTO.getStudents().toString(),
                "tooMuchStudent");
        }

        Project project = new Project();
        project.setFaculty(studentProjectDTO.getFaculty());
        project.setSupervisor(studentProjectDTO.getSupervisor());
        project.setName(studentProjectDTO.getProjectName());

        studentProjectDTO.getStudents().forEach(student -> {
//            Optional<Student>  returnedStudent = this.studentService.findByIndex(student.getIndex());
            if (student.getUser().getId() != null){

                throw new BadRequestAlertException("student already have a project", studentProjectDTO.getStudents().toString(),
                    "tooMuchStudents");
            }
        });
        Project result = projectService.save(project);

        studentProjectDTO.getStudents().forEach(student -> {
            UserDTO user = new UserDTO();
            Set<String> authorities =  new HashSet<>();
            authorities.add(AuthoritiesConstants.USER);
            user.setAuthorities(authorities);
            user.setLogin(student.getIndex());
            user.setActivated(true);
            user.setEmail(student.getIndex() + "@fu.com");
            User returnedUser = userService.createUser(user);
            student.setUser(returnedUser);
            student.setFaculty(studentProjectDTO.getFaculty());
            student.setProject(project);
            studentService.save(student);
        });

        return ResponseEntity
                .created(new URI("/api/studentsProject/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /projects} : Updates an existing project.
     *
     * @param project the project to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated project, or with status {@code 400 (Bad Request)} if the
     *         project is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the project couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projects")
    public ResponseEntity<Project> updateProject(@RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to update Project : {}", project);
        if (project.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Project result = projectService.save(project);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, project.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /projects} : get all the projects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of projects in body.
     */
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects(ProjectCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Projects by criteria: {}", criteria);
        Page<Project> page = projectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /projects/count} : count all the projects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/projects/count")
    public ResponseEntity<Long> countProjects(ProjectCriteria criteria) {
        log.debug("REST request to count Projects by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectQueryService.countByCriteria(criteria));
    }


    @GetMapping("/student-project")
    public ResponseEntity<Optional<Project>>getStudentsProject(){
        Optional<Project> project = projectService.findByStudent();
        return ResponseEntity.ok().body(project);
    }

    /**
     * {@code GET  /projects/:id} : get the "id" project.
     *
     * @param id the id of the project to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the project, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Optional<Project> project = projectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(project);
    }
    @GetMapping("/faculty-projects")
    public ResponseEntity<List<Project>> getFacultyProjects(Faculty faculty){
        Optional<Page<Project>> page = projectService.findAllByFaculty(faculty);
          HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page.get());
        return ResponseEntity.ok().headers(headers).body(page.get().getContent());
    }

    /**
     * {@code DELETE  /projects/:id} : delete the "id" project.
     *
     * @param id the id of the project to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.debug("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
