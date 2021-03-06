package com.fu.pums.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.fu.pums.domain.Project;
import com.fu.pums.domain.*; // for static metamodels
import com.fu.pums.repository.ProjectRepository;
import com.fu.pums.service.dto.ProjectCriteria;

/**
 * Service for executing complex queries for {@link Project} entities in the database.
 * The main input is a {@link ProjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Project} or a {@link Page} of {@link Project} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectQueryService extends QueryService<Project> {

    private final Logger log = LoggerFactory.getLogger(ProjectQueryService.class);

    private final ProjectRepository projectRepository;

    public ProjectQueryService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Return a {@link List} of {@link Project} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Project> findByCriteria(ProjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Project> specification = createSpecification(criteria);
        return projectRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Project} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Project> findByCriteria(ProjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Project> specification = createSpecification(criteria);
        return projectRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Project> specification = createSpecification(criteria);
        return projectRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Project> createSpecification(ProjectCriteria criteria) {
        Specification<Project> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Project_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Project_.name));
            }
            if (criteria.getStudentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getStudentsId(),
                    root -> root.join(Project_.students, JoinType.LEFT).get(Student_.id)));
            }
            if (criteria.getFilesId() != null) {
                specification = specification.and(buildSpecification(criteria.getFilesId(),
                    root -> root.join(Project_.files, JoinType.LEFT).get(File_.id)));
            }
            if (criteria.getObservationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getObservationsId(),
                    root -> root.join(Project_.observations, JoinType.LEFT).get(Observation_.id)));
            }
            if (criteria.getFacultyId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacultyId(),
                    root -> root.join(Project_.faculty, JoinType.LEFT).get(Faculty_.id)));
            }
            if (criteria.getSupervisorId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupervisorId(),
                    root -> root.join(Project_.supervisor, JoinType.LEFT).get(Supervisor_.id)));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildSpecification(criteria.getBatchId(),
                    root -> root.join(Project_.batch, JoinType.LEFT).get(Batch_.id)));
            }
        }
        return specification;
    }
}
