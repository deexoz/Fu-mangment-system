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

import com.fu.pums.domain.Faculty;
import com.fu.pums.domain.*; // for static metamodels
import com.fu.pums.repository.FacultyRepository;
import com.fu.pums.service.dto.FacultyCriteria;

/**
 * Service for executing complex queries for {@link Faculty} entities in the database.
 * The main input is a {@link FacultyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Faculty} or a {@link Page} of {@link Faculty} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FacultyQueryService extends QueryService<Faculty> {

    private final Logger log = LoggerFactory.getLogger(FacultyQueryService.class);

    private final FacultyRepository facultyRepository;

    public FacultyQueryService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    /**
     * Return a {@link List} of {@link Faculty} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Faculty> findByCriteria(FacultyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Faculty> specification = createSpecification(criteria);
        return facultyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Faculty} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Faculty> findByCriteria(FacultyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Faculty> specification = createSpecification(criteria);
        return facultyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FacultyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Faculty> specification = createSpecification(criteria);
        return facultyRepository.count(specification);
    }

    /**
     * Function to convert {@link FacultyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Faculty> createSpecification(FacultyCriteria criteria) {
        Specification<Faculty> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Faculty_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Faculty_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Faculty_.code));
            }
            if (criteria.getStudentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getStudentsId(),
                    root -> root.join(Faculty_.students, JoinType.LEFT).get(Student_.id)));
            }
            if (criteria.getProjectsId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectsId(),
                    root -> root.join(Faculty_.projects, JoinType.LEFT).get(Project_.id)));
            }
            if (criteria.getAnnouncmentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnnouncmentsId(),
                    root -> root.join(Faculty_.announcments, JoinType.LEFT).get(Announcement_.id)));
            }
            if (criteria.getSupervisorsId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupervisorsId(),
                    root -> root.join(Faculty_.supervisors, JoinType.LEFT).get(Supervisor_.id)));
            }
        }
        return specification;
    }
}
